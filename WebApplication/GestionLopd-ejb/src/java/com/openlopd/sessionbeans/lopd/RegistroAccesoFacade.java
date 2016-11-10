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
import com.openlopd.entities.lopd.RegistroAcceso;
import com.openlopd.entities.lopd.RegistroAcceso_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Negocio Registro Accesos LOPD
 * @author Eduardo L. García Glez.
 */
@Stateless
public class RegistroAccesoFacade extends AbstractFacadeDataTable<RegistroAcceso> implements RegistroAccesoFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroAccesoFacade() {
        super(RegistroAcceso.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo, 
    RegistroAcceso registroAcceso) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        registroAcceso.setId(GenKey.newKey());
        registroAcceso.setEmpresa(accessInfo.getSubEmpresa());
        registroAcceso.setUsuario(accessInfo.getUserInfo().getUsuario());
        registroAcceso.setFechaAltaInt(new Date().getTime());
        create(accessInfo, ColumnasPermisos.REGISTRO_ACCESOS, 
                null, registroAcceso);
    }

    @Override
    public void edit(AccessInfo accessInfo, 
    RegistroAcceso registroAcceso) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        edit(accessInfo, ColumnasPermisos.REGISTRO_ACCESOS, 
                null, registroAcceso);
    }

    @Override
    public void remove(AccessInfo accessInfo, 
    RegistroAcceso registroAcceso) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        remove(accessInfo, ColumnasPermisos.REGISTRO_ACCESOS, 
                null, registroAcceso);
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return RegistroAcceso_.usuarioQueAccede;
        }
        if (iShortCol == 1) {
            return RegistroAcceso_.fechaAcceso;
        }
        if (iShortCol == 2) {
            return RegistroAcceso_.fichero;
        }
        if (iShortCol == 3) {
            return RegistroAcceso_.tipoAcceso;
        }
        if (iShortCol == 4) {
            return RegistroAcceso_.autorizado;
        }        
        return RegistroAcceso_.usuarioQueAccede;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return RegistroAcceso_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return RegistroAcceso_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return RegistroAcceso_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<RegistroAcceso> root, String filterText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
