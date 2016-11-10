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

package com.openlopd.common.localizacion.sessionbeans;

import com.openlopd.common.localizacion.entities.Localidad;
import java.util.List;
import javax.ejb.Local;

/**
 * Bean de Session encargado de los métodos básicos para la gestión de
 * Localidades.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 09 de marzo de 2011
 */
@Local
public interface LocalidadFacadeLocal {

    void create(Localidad localidad);

    void edit(Localidad localidad);

    void remove(Localidad localidad);

    Localidad find(Object id);

    List<Localidad> findAll();

    List<Localidad> findRange(int[] range);

    int count();

    public java.util.List<com.openlopd.common.localizacion.entities.Localidad> getLocalidades(com.openlopd.common.localizacion.entities.Provincia provincia);

    public java.util.List<com.openlopd.common.localizacion.entities.Localidad> getLocalidades(java.lang.String idProvincia);

    public java.util.List<String> findAll(java.lang.String term);

}
