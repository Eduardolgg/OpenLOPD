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
import java.util.List;
import javax.persistence.*;

/**
 * Manage application mail accounts
 *
 * @author Eduardo L. García Glez.
 */
@Entity
public class MailAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Private Properties">
    @EmbeddedId
    private MailAccountPK MailAccountPK;
    @Column(nullable = false, length = 255)
    private String mailServer;
    @Column(nullable = false, length = 255)
    private String mailAccount;
    @Column(nullable = false, length = 255)
    private String mailUser;
    @Column(nullable = false, length = 25)
    private String password;
    @Column(nullable = false, length = 25)
    private String protocol;
    @Column(nullable = false, length = 25)
    private String port;
    @Column(nullable = false)
    private Boolean tsl;
    @Column(nullable = false)
    private Boolean authRequired;
    @OneToMany(mappedBy = "mailAccount", cascade = CascadeType.REMOVE)
    private List<OutBox> outBox;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Defaulth constructor
     */
    public MailAccount() {
    }

    /**
     * Instance entity by primary key
     *
     * @param MailAccountPK entity key.
     */
    public MailAccount(com.jkingii.mail.entities.MailAccountPK MailAccountPK) {
        this.MailAccountPK = MailAccountPK;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Get entity primary key.
     *
     * @return entity primary key
     * @see MailAccountPK
     */
    public com.jkingii.mail.entities.MailAccountPK getMailAccountPK() {
        return MailAccountPK;
    }

    /**
     * Set entity primary key.
     *
     * @param MailAccountPK
     * @see MailAccountPK
     */
    public void setMailAccountPK(com.jkingii.mail.entities.MailAccountPK MailAccountPK) {
        this.MailAccountPK = MailAccountPK;
    }

    /**
     * Gets the mail server name.
     *
     * ej: imap.google.com
     *
     * @return mail server name
     */
    public String getMailServer() {
        return mailServer;
    }

    /**
     * Set mail server name.
     *
     * ej: imap.google.com
     *
     * @param mailServer mail server name.
     */
    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    /**
     * Get a mail account
     *
     * ej: myaccount@mailserver.es
     *
     * @return mail account
     */
    public String getMailAccount() {
        return mailAccount;
    }

    /**
     * Set a mail account
     *
     * ej: myaccount@mailserver.es
     *
     * @param mailAccount mail account.
     */
    public void setMailAccount(String mailAccount) {
        this.mailAccount = mailAccount;
    }

    /**
     * Get a mail account password
     *
     * @return mail account password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set a mail account password.
     *
     * @param password mail account password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get a mail server port
     *
     * POP3 - port 110
     * IMAP - port 143
     * SMTP - port 25
     * HTTP - port 80
     *
     * Secure SMTP (SSMTP) - port 465
     * Secure IMAP (IMAP4-SSL) - port 585
     * IMAP4 over SSL (IMAPS) - port 993
     * Secure POP3 (SSL-POP) - port 995
     *
     * @return mail server port
     */
    public String getPort() {
        return port;
    }

    /**
     * Set a mail server port
     *
     * POP3 - port 110
     * IMAP - port 143
     * SMTP - port 25
     * HTTP - port 80
     *
     * Secure SMTP (SSMTP) - port 465
     * Secure IMAP (IMAP4-SSL) - port 585
     * IMAP4 over SSL (IMAPS) - port 993
     * Secure POP3 (SSL-POP) - port 995
     * 
     * @param port mail server port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Get mail protocol
     * 
     * ej: smtp, pop3...
     * 
     * @return mail protocol.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Set mail protocol
     * 
     * ej: smtp, pop3...
     * 
     * @param protocol mail protocol.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Get mail user name
     * @return mail user name
     */
    public String getMailUser() {
        return mailUser;
    }

    /**
     * Set mail user name
     * @param mailUser mail user name.
     */
    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    /**
     * Indicates if TSL is required
     * @return <code>true</code> if TSL is required, <code>false</code>
     * otherwise.
     */
    public Boolean isTsl() {
        return tsl;
    }

    /**
     * Indicates if TSL is required
     * @param tsl <code>true</code> if TSL is required, <code>false</code>
     * otherwise.
     */
    public void setTsl(Boolean tsl) {
        this.tsl = tsl;
    }

    /**
     * Indicates if authentication is required.
     * @return <code>true</code> if authentication is required, <code>false</code>
     * otherwise.
     */
    public Boolean isAuthRequired() {
        return authRequired;
    }

    /**
     * Indicates if authentication is required.
     * @param authRequired <code>true</code> if authentication is required, <code>false</code>
     * otherwise.
     */
    public void setAuthRequired(Boolean authRequired) {
        this.authRequired = authRequired;
    }

    /**
     * Messages in outbox asociated with this mail account.
     * @return List of messages.
     */
    public List<OutBox> getOutBox() {
        return outBox;
    }

    /**
     * Messages in outbox asociated with this mail account.
     * @param outBox List of messages.
     */
    public void setOutBox(List<OutBox> outBox) {
        this.outBox = outBox;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailAccount other = (MailAccount) obj;
        if (this.MailAccountPK != other.MailAccountPK && (this.MailAccountPK == null || !this.MailAccountPK.equals(other.MailAccountPK))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.MailAccountPK != null ? this.MailAccountPK.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MailAccount{" + "MailAccountPK=" + MailAccountPK + ", mailServer=" + mailServer + ", mailAccount=" + mailAccount + ", mailUser=" + mailUser + ", password=" + password + ", protocol=" + protocol + ", port=" + port + ", tsl=" + tsl + ", authRequired=" + authRequired + '}';
    }
    //</editor-fold>
}
