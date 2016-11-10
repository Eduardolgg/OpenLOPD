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

package com.jkingii.mail.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Message OutBox
 *
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "outbox")
@NamedQueries({
    @NamedQuery(name = "outBox.MessagesToSend", query =
    "SELECT o FROM OutBox o "
    + "WHERE o.sendDate is null"),
    @NamedQuery(name = "outBox.MessagesError", query = ""
        + "SELECT o FROM OutBox o "
        + "WHERE o.lastAttempt IS NOT NULL "
        + "  AND o.sendDate IS NULL")
})
public class OutBox implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Private Properties">
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne
    private MailAccount mailAccount;
    @ManyToOne
    private Message message;
    @Column(nullable = false, length = 255)
    private String recipient;
    @Column(nullable = false)
    private Long createDate;
    @Column(nullable = true)
    private Long sendDate;
    @Column(nullable = true)
    private Long lastAttempt;
    @Column(nullable = false, length = 255)
    private String subject;
    @Column(nullable = false, length = 6000)
    private String messageText;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default constructor
     */
    public OutBox() {
    }

    /**
     * Init all required params
     *
     * @param id entity unique id
     * @param mailAccount message sender
     * @param message message to send
     * @param recipient message recipient.
     * @param createDate message create date.
     */
    public OutBox(String id, MailAccount mailAccount, Message message,
            String subject, String messageText, String recipient, Long createDate) {
        this.id = id;
        this.mailAccount = mailAccount;
        this.message = message;
        this.recipient = recipient;
        this.createDate = createDate;
        this.subject = subject;
        this.messageText = messageText;
    }
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Gets entity unique id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set entity unique id
     *
     * @param id id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets sender account info.
     *
     * @return Sender account info.
     */
    public MailAccount getMailAccount() {
        return mailAccount;
    }

    /**
     * Sets sender account info.
     *
     * @param mailAccount account info.
     */
    public void setMailAccount(MailAccount mailAccount) {
        this.mailAccount = mailAccount;
    }

    /**
     * Gets message to send.
     *
     * @return message to send.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Sets message to send.
     *
     * @param message messate to send.
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * Gets message recipient.
     *
     * @return recipient. TODO: ¿Y si es un listado de destinatarios?
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Set message recipient.
     *
     * @param recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Get message create date.
     *
     * @return message create date.
     */
    public Long getCreateDate() {
        return createDate;
    }

    /**
     * Set message create date.
     *
     * @param createDate message create date.
     */
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    /**
     * Get message last attempt
     *
     * If sending the message fails, the date is stored here
     *
     * @return message date last attemp.
     */
    public Long getLastAttempt() {
        return lastAttempt;
    }

    /**
     * Sets message last attempt
     *
     * If sending the message fails, the date is stored here
     *
     * @param lastAttempt message date last attemp.
     */
    public void setLastAttempt(Long lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    /**
     * Sets message send date.
     *
     * @return message send date.
     */
    public Long getSendDate() {
        return sendDate;
    }

    /**
     * Sets message send date.
     *
     * @param sendDate message send date.
     */
    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OutBox)) {
            return false;
        }
        OutBox other = (OutBox) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jkingii.mail.entities.OutBox[ id=" + id + " ]";
    }
    //</editor-fold>
}
