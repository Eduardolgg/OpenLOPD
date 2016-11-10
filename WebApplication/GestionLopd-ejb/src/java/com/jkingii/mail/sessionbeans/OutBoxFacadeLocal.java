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

import com.jkingii.mail.entities.MailAccountPK;
import com.jkingii.mail.entities.OutBox;
import java.util.List;
import javax.ejb.Local;

/**
 * OutBox bussines methods.
 * @author Eduardo L. García Glez.
 */
@Local
public interface OutBoxFacadeLocal {

    void create(OutBox outBox);

    void edit(OutBox outBox);

    void remove(OutBox outBox);

    OutBox find(Object id);

    List<OutBox> findAll();

    List<OutBox> findRange(int[] range);

    int count();

    public void sendMail(MailAccountPK accountId, OutBox mailInfo) throws javax.mail.MessagingException;

    public List<OutBox> getPendingMessages();

    public void addMessage(MailAccountPK accountId, java.lang.String myMessage, String[] params, java.lang.String recipient);

    public List<OutBox> getErrorMessages();

    public void setActive(boolean active);

    public boolean isActive();
    
}
