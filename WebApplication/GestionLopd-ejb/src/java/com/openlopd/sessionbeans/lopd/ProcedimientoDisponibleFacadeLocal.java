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
import com.openlopd.entities.lopd.ProcedimientoDisponible;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Local
public interface ProcedimientoDisponibleFacadeLocal {

    void create(ProcedimientoDisponible procedimientoDisponible);

    void edit(ProcedimientoDisponible procedimientoDisponible);

    void remove(ProcedimientoDisponible procedimientoDisponible);

    ProcedimientoDisponible find(Object id);

    List<ProcedimientoDisponible> findAll();

    List<ProcedimientoDisponible> findRange(int[] range);

    int count();

    public java.util.List<com.openlopd.entities.lopd.ProcedimientoDisponible> getUnusedProcedures(String idTipoProc, AccessInfo accessInfo);

    public void create(AccessInfo accessInfo, ProcedimientoDisponible entity);
    
}
