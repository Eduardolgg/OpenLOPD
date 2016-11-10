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

package com.openlopd.sessionbeans.lopd;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.lopd.FicheroPredefinido;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestión de las entidades de ficheros predefinidos.
 *
 * @author Eduardo L. García Glez.
 */
@Local
public interface FicheroPredefinidoFacadeLocal {

    void create(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException;

    void edit(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException;

    FicheroPredefinido find(Object id);

    List<FicheroPredefinido> findAll();

    List<FicheroPredefinido> findRange(int[] range);

    int count();

    public com.openlopd.entities.lopd.FicheroPredefinido create(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.agpd.nota.xml.DatosFichero datosFichero) throws java.lang.Exception;

    public java.util.List<com.openlopd.entities.lopd.FicheroPredefinido> findActives(com.openlopd.business.seguridad.AccessInfo accessInfo) throws com.openlopd.entities.seguridad.exception.UnknownColumnException, com.openlopd.exceptions.SeguridadReadException;

    public void load(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.agpd.nota.xml.DatosFichero datosFichero, java.lang.String id) throws javax.xml.bind.JAXBException;
}
