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

import com.jkingii.mail.entities.OutBox;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plan de envío de mails.
 * @author Eduardo L. García Glez.
 */
@Stateless
@LocalBean
public class OutBoxBot {
    private static Logger logger = LoggerFactory.getLogger(OutBoxBot.class);
    @EJB
    private OutBoxFacadeLocal outBoxFacade;
    private static boolean active = false;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        OutBoxBot.active = active;
    }   
    
    @Schedule(minute = "*/5", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    private void sheduleOutBox() {
        if(!active) {
            logger.info("Sistema de envío de e-mails desactivado, consulte "
                    + "con el administrador del sistema.");
            return;
        }
        
        logger.info("Sending Mails: " + new Date());

        try {
            List<OutBox> mesgList = outBoxFacade.getPendingMessages();
            for (OutBox mesg : mesgList) {
                mesg.setLastAttempt(new Date().getTime());
                outBoxFacade.edit(mesg);
                outBoxFacade.sendMail(mesg.getMailAccount().getMailAccountPK(), mesg);
                mesg.setSendDate(mesg.getLastAttempt());
                outBoxFacade.edit(mesg);
            }
        } catch (Exception e) {
            logger.error("Error enviado: " + e.getMessage());
        }
    }
    
}
