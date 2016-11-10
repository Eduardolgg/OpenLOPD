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

import com.openlopd.entities.lopd.ProcedimientoHabilitado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Local
public interface ProcedimientoHabilitadoFacadeLocal {

    void create(ProcedimientoHabilitado procedimientoHabilitado);

    void edit(ProcedimientoHabilitado procedimientoHabilitado);

    void remove(ProcedimientoHabilitado procedimientoHabilitado);

    ProcedimientoHabilitado find(Object id);

    List<ProcedimientoHabilitado> findAll();

    List<ProcedimientoHabilitado> findRange(int[] range);

    int count();

    public java.util.List<com.openlopd.entities.lopd.ProcedimientoHabilitado> getProcedimientosHabilitados(String idTipoProc, com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.lopd.ProcedimientoHabilitado> getProcedimientosHabilitados(String idTipoProc, java.lang.String idEmpresa);

    public java.util.List<com.openlopd.entities.lopd.ProcedimientoHabilitado> getProcedimientosHabilitados(com.openlopd.entities.lopd.TipoProcedimiento tipoProcedimiento, com.openlopd.entities.empresas.Empresa empresa);

    public void create(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.lopd.ProcedimientoHabilitado p);

    public void edit(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.lopd.ProcedimientoHabilitado p);

    public void remove(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.lopd.ProcedimientoHabilitado p);
    
}
