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

package com.jkingii.mail.utils;

import com.jkingii.mail.entities.MailAccount;
import java.util.Properties;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class MailProperties {
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_USER = "mail.smtp.user";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    /**
     * Defaulth constructor.
     */
    public MailProperties() {
    }

    /**
     * MailAccount to Properties.
     * 
     * Generates an instance of Properties required for javamail
     * @param mc mail info.
     * @return Properties instance with properties of mc param.
     */
    public static Properties PropertiesFactory(MailAccount mc) {
        Properties props = new Properties();
        props.setProperty(MAIL_SMTP_HOST, mc.getMailServer());
        props.setProperty(MAIL_SMTP_STARTTLS_ENABLE, mc.isTsl().toString());
        props.setProperty(MAIL_SMTP_PORT, mc.getPort());
        props.setProperty(MAIL_SMTP_USER, mc.getMailUser());
        props.setProperty(MAIL_SMTP_AUTH, mc.isAuthRequired().toString());
        return props;
    }
}
