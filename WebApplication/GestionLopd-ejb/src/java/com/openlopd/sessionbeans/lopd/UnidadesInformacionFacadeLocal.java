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

import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.entities.lopd.UnidadesInformacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Local
public interface UnidadesInformacionFacadeLocal {

    void create(UnidadesInformacion unidadesInformacion);

    void edit(UnidadesInformacion unidadesInformacion);

    void remove(UnidadesInformacion unidadesInformacion);

    UnidadesInformacion find(Object id);

    List<UnidadesInformacion> findAll();

    List<UnidadesInformacion> findRange(int[] range);

    int count();

    public List<UnidadesInformacion> getActives();
    
}
