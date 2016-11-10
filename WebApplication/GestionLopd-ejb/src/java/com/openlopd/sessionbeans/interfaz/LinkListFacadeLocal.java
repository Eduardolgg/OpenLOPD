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

package com.openlopd.sessionbeans.interfaz;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.interfaz.LinkList;
import com.openlopd.entities.lopd.TipoNivelSeguridad;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestión del listado de enlaces.
 * @author Eduardo L. García Glez.
 */
@Local
public interface LinkListFacadeLocal {

    void create(LinkList linkList);

    void edit(LinkList linkList);

    void remove(LinkList linkList);

    LinkList find(Object id);

    List<LinkList> findAll();

    List<LinkList> findRange(int[] range);

    int count();

    public java.util.List<com.openlopd.entities.interfaz.LinkList> findActives(java.lang.String locale);

    public java.util.List<com.openlopd.entities.interfaz.LinkList> findOperations();

    public com.openlopd.entities.interfaz.LinkList findByLink(java.lang.String link);

    public List<LinkList> findOperations(TipoNivelSeguridad nivel);

    public List<LinkList> findTareasHabituales(AccessInfo accessInfo);
    
}
