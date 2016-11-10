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

package com.openlopd.common.localizacion.business;

import com.openlopd.common.localizacion.entities.Localidad;
import com.openlopd.common.localizacion.entities.Pais;
import com.openlopd.common.localizacion.entities.Provincia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 10 de marzo de 2011
 */
@Local
public interface LocalizacionLocal {

    public List<Provincia> getProvincias();

    public java.util.List<com.openlopd.common.localizacion.entities.Localidad> getLocalidades(com.openlopd.common.localizacion.entities.Provincia provincia);

    public java.util.List<com.openlopd.common.localizacion.entities.Localidad> getLocalidades(java.lang.String idProvincia);

    Localidad getLocalidad(Long id);

    Provincia getProvincia(String id);

    public java.util.List<com.openlopd.common.localizacion.entities.Pais> getPaises();

    public List<Pais> getPaisesAgpdResponsable();

    public List<Pais> getPaisesAgpdTransferInternacional();

    public List<Pais> getPaisesAgpdDeclarante();

    public List<Pais> getPaisesAgpdEncargado();

    public List<Pais> getPaisesAgpdAcceso();
    
}
