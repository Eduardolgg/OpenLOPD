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
import com.openlopd.entities.lopd.RegistroEntradaSoporte;
import com.openlopd.entities.lopd.RegistroEntradaSoporte_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author Eduardo L. García GLez.
 */
@Stateless
public class RegistroEntradaSoporteFacade extends AbstractFacadeDataTable<RegistroEntradaSoporte> implements RegistroEntradaSoporteFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroEntradaSoporteFacade() {
        super(RegistroEntradaSoporte.class);
    }
    
    

    @Override
    public void create(AccessInfo accessInfo, 
    RegistroEntradaSoporte registroEntradaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        create(accessInfo, ColumnasPermisos.REGISTRO_ENTRADA, 
                null, registroEntradaSoporte);
    }

    @Override
    public void edit(AccessInfo accessInfo, 
    RegistroEntradaSoporte registroEntradaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        edit(accessInfo, ColumnasPermisos.REGISTRO_ENTRADA, 
                null, registroEntradaSoporte);
    }

    @Override
    public void remove(AccessInfo accessInfo, 
    RegistroEntradaSoporte registroEntradaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        remove(accessInfo, ColumnasPermisos.REGISTRO_ENTRADA, 
                null, registroEntradaSoporte);
    }
    
    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return RegistroEntradaSoporte_.tipoSoporte;
        }
        if (iShortCol == 1) {
            return RegistroEntradaSoporte_.Observaciones;
        }
        if (iShortCol == 2) {
            return RegistroEntradaSoporte_.FechaEntrada;
        }
        if (iShortCol == 3) {
            return RegistroEntradaSoporte_.cantidad;
        }
        if (iShortCol == 4) {
            return RegistroEntradaSoporte_.tipoInfo;
        }
        if (iShortCol == 5) {
            return RegistroEntradaSoporte_.personaAutorizada;
        }
        return RegistroEntradaSoporte_.tipoSoporte;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return RegistroEntradaSoporte_.empresa;
    }    

    @Override
    public SingularAttribute getBorradoAttribute() {
        return RegistroEntradaSoporte_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return RegistroEntradaSoporte_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<RegistroEntradaSoporte> root, String filterText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
