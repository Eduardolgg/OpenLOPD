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

package com.openlopd.sessionbeans.empresas;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Acceso a datos para la entidad Persona.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Local
public interface PersonaFacadeLocal {

    void create(Persona persona);

    void create(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void edit(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    Persona find(Object id);

    List<Persona> findAll();

    List<Persona> findRange(int[] range);

    int count();

    public Object recoveryTest(java.lang.String dni, java.lang.String email);
    public List<Persona> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);

    public java.util.List<com.openlopd.entities.empresas.Persona> getAutorizadosSalida(com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.empresas.Persona> getAutorizadosEntrada(com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.empresas.Persona> findAll(com.openlopd.business.seguridad.AccessInfo accessInfo, String term);

    public com.openlopd.entities.empresas.Persona getResponsableSeguridad(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public java.util.List<com.openlopd.entities.empresas.Persona> getAutorizadosCopiaReproduccion(com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.empresas.Persona> findAll(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public void edit(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.empresas.Persona persona, java.lang.String ficheros) throws com.openlopd.entities.seguridad.exception.UnknownColumnException, com.openlopd.exceptions.SeguridadWriteException, com.openlopd.exceptions.SeguridadWriteLimitException;

}
