/*
 * OpenLOPD
 * Copyright (C) 2011  Eduardo L. García Glez <eduardo.l.g.g@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jkingii.mail.sessionbeans;

import com.elgg.utils.lang.StringParam;
import com.jkingii.mail.entities.MailAccount;
import com.jkingii.mail.entities.MailAccountPK;
import com.jkingii.mail.entities.OutBox;
import com.jkingii.mail.utils.GenKey;
import com.jkingii.mail.utils.JKAutenticator;
import com.jkingii.mail.utils.MailProperties;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OutBox bussines methods.
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class OutBoxFacade extends AbstractFacade<OutBox> implements OutBoxFacadeLocal {
    @EJB
    private OutBoxBot outBoxBot;    
    @EJB
    private MessageFacadeLocal messageFacade;
    @EJB
    private MailAccountFacadeLocal mailAccountFacade;
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;
    
    private static Logger logger = LoggerFactory.getLogger(OutBoxFacade.class);
    public static final String ACCOUNT_INFO = "info";
    public static final String ACCOUNT_ERROR = "error";
    public static final String ACCOUNT_PUBLI = "publi";
    public static final String MSG_FORGOT_PASS = "forgotPass";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutBoxFacade() {
        super(OutBox.class);
    }

    /**
     * Add a message to out box to be Send.
     *
     * The message will be sent according to the planning
     *
     * @param accountId cuenta de envío o tipo de mensaje.
     * @param myMessage id mensaje predeterminado.
     * @param recipient destinatario del mensaje
     */
    @Override
    public void addMessage(MailAccountPK accountId, String myMessage,
            String[] params, String recipient) {
        try {
            com.jkingii.mail.entities.Message me = messageFacade.find(myMessage);
            OutBox o = new OutBox(GenKey.newKey(), new MailAccount(accountId),
                    me, new StringParam(me.getSubject(), params).replace(),
                    new StringParam(me.getMessage(), params).replace(),
                    recipient, new Date().getTime());
            this.create(o);
        } catch (Exception e) {
            Object[] errInfo = {accountId, myMessage, params.toString(), 
                recipient, e.getMessage()};
            logger.error("Error añadiendo el mensaje [accountId: [{}] myMessage: [{}]"
                    + "params: [{}], recipient: [{}]] a la cola, Exception: {}",
                    errInfo);
        }
    }

    /**
     * Send a predefined mail.
     *
     * @param accountId cuenta de envío o tipo de mensaje.
     * @param myMessage id mensaje predeterminado.
     * @param recipient destinatario del mensaje
     */
    @Override
    public void sendMail(MailAccountPK accountId, OutBox mailInfo) throws MessagingException {
        MailAccount ma = mailAccountFacade.find(accountId);
        Session session = Session.getInstance(MailProperties.PropertiesFactory(ma),
                new JKAutenticator(ma));

        if (logger.isDebugEnabled())
            session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(ma.getMailAccount()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getRecipient()));
        message.setSubject(mailInfo.getSubject());
        message.setText(mailInfo.getMessageText(),
                "UTF-8", "html"); 
        Transport t = session.getTransport(ma.getProtocol());
        t.connect(ma.getMailAccount(), ma.getPassword());
        // El segundo parámetro de la siguiente instrucción
        //obtiene todos los destinatarios.
        t.sendMessage(message, message.getAllRecipients());
        t.close();

        //TODO: almacenar los mails que no se envíen en una tabla para
    }

    @Override
    public List<OutBox> getPendingMessages() {
        Query q = em.createNamedQuery("outBox.MessagesToSend");

        try {
            return q.getResultList();
        } catch (Exception e) {
            logger.error("Error recuperando listado de mensajes. Exception: {}", e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<OutBox> getErrorMessages() {
        Query q = em.createNamedQuery("outBox.MessagesError");
        return q.getResultList();
    }
    
    @Override
    public boolean isActive() {
        return outBoxBot.isActive();
    }

    @Override
    public void setActive(boolean active) {
        outBoxBot.setActive(active);
    } 
}
