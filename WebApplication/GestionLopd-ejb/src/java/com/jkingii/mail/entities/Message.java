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
 * Mensajes por defecto.
 * @author Eduardo L. García Glez.
 */
@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Private Properties">
    @Id
    @Column(nullable = false, length = 37)
    private String id;
    @Column(nullable = false, length = 255)
    private String subject;
    @Column(nullable = false, length = 2000)
    private String message;
    @Column(nullable = false)
    private Long createDate;
    @Column(nullable = true)
    private Long closeDate;
    @OneToMany (mappedBy = "message", cascade = CascadeType.PERSIST)
    private List<OutBox> outBox;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Defaulth constructor
     */
    public Message() {
    }
    
    /**
     * Instance a entity with id
     * @param id 
     */
    public Message(String id) {
        this.id = id;
    }
    //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Message ID
     * @return message id.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Message ID
     * @param id Message id.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Get close date.
     * 
     * A message is closed if it is not to be reused
     * 
     * @return 
     */
    public Long getCloseDate() {
        return closeDate;
    }
    
    /**
     * Set a close date.
     * 
     * A message is closed if it is not to be reused
     * 
     * @param closeDate close date.
     */
    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }
    
    /**
     * Get a create date.
     * @return create date.
     */
    public Long getCreateDate() {
        return createDate;
    }
    
    /**
     * Set a create date
     * @param createDate create date.
     */
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
    
    /**
     * Get message body
     * @return 
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Set a message body
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Get message subject
     * 
     * @return message subject text.
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Set message subject
     * 
     * @param Subject message subject text.
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
    
    //<editor-fold defaultstate="collapsed" desc="Defaulth Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.jkingii.mail.entities.Message[ id=" + id + " ]";
    }
    //</editor-fold>
    
}
