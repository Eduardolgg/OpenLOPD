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
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadReadException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestión de los ficheros (AEPD) de la empresa.
 * @author Eduardo L. García Glez.
 */
@Local
public interface FicheroFacadeLocal {

    void create(AccessInfo accessInfo, Fichero fichero) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    /**
     * Solo para uso interno.
     * @param fichero 
     */
    void edit(Fichero fichero);

    void edit(AccessInfo accessInfo, Fichero fichero) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, Fichero fichero) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    Fichero find(Object id);

    List<Fichero> findAll();

    List<Fichero> findRange(int[] range);

    int count();
    
    public List<Fichero> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);

    public java.util.List<com.openlopd.entities.lopd.Fichero> findActives(com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.lopd.Fichero> ficherosSinRegistrar();

    public com.openlopd.entities.lopd.Fichero realizarSolicitud(AccessInfo accessInfo, com.openlopd.agpd.nota.xml.DatosFichero datosFichero, boolean registrar, boolean firmar) throws java.lang.Exception;

    public com.openlopd.entities.lopd.TipoNivelSeguridad getNivel(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public java.lang.Boolean isNivelMedioSystem(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public java.util.List<com.openlopd.entities.lopd.Fichero> findFicherosDisponiblesPersona(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idPersona);

    public java.util.List<com.openlopd.entities.lopd.Fichero> findFicherosHabilitadosPersona(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idPersona);

    public java.lang.String findPerfilesAsString(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.lopd.Fichero f);

    public void load(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.agpd.nota.xml.DatosFichero datosFichero, java.lang.String id) throws javax.xml.bind.JAXBException;

    public Fichero updateCodInscripcion(AccessInfo accessInfo, String nombreFichero, String codInscripcion) throws SeguridadWriteException, UnknownColumnException, SeguridadReadException, SeguridadWriteLimitException;

    public boolean hasAllRegistrationCodes(AccessInfo accessInfo);

    public List<Fichero> pendientesFirmar(AccessInfo accessInfo);

    public List<Fichero> findFicherosConError(AccessInfo accessInfo);

    public boolean isAutoRegistroActivo();

    public void setAutoRegistroActivo(boolean autoRegistroActivo);
    
}
