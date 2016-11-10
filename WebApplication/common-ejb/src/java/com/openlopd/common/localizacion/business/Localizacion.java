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
import com.openlopd.common.localizacion.sessionbeans.LocalidadFacadeLocal;
import com.openlopd.common.localizacion.sessionbeans.PaisFacade;
import com.openlopd.common.localizacion.sessionbeans.PaisFacadeLocal;
import com.openlopd.common.localizacion.sessionbeans.ProvinciaFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Métodos que permiten obtener datos de localización.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.1 10 de marzo de 2011
 * Modificaciones:
 *     Añadidos los métodos getLocalidad y getProvincia.
 */
@Stateless
public class Localizacion implements LocalizacionLocal {
    @EJB
    private PaisFacadeLocal paisFacade;
    @EJB
    private ProvinciaFacadeLocal provinciaFacade;
    @EJB
    private LocalidadFacadeLocal localidadFacade;

    /**
     * Obtiene el listado de todas las provincias españolas.
     * @return Lista con todas las provincias españolas.
     */
    @Override
    public List<Provincia> getProvincias() {
        return provinciaFacade.findAll();
    }

    /**
     * Obtiene el listado de localidades de una provincia determidada.
     * @param provincia Objeto provincia sobre la que buscar las localidades.
     * @return Lista de las localidades pertenecientes a la provincia.
     */
    @Override
    public List<Localidad> getLocalidades(Provincia provincia) {
        return localidadFacade.getLocalidades(provincia);
    }

    /**
     * Obtiene el listado de localidades de una provincia determidada.
     * @param idProvincia Idntificador de la provincia sobre la que buscar las
     * localidades.
     * @return Lista de las localidades pertenecientes a la provincia.
     */
    @Override
    public List<Localidad> getLocalidades(String idProvincia) {
        return localidadFacade.getLocalidades(idProvincia);
    }

    /**
     * Obtiene una localidad a través de su ID.
     * @param id Identificador único de la localidad a buscar.
     * @return Objeto <code>Localidad</code> con la localidad buscada.
     */
    @Override
    public Localidad getLocalidad(Long id) {
        return localidadFacade.find(id);
    }

    /**
     * Obtiene una provincia a través de su ID.
     * @param id Identificador único de la provincia a buscar.
     * @return Obtiene <code>Provincia</code> con la provincia buscada.
     */
    @Override
    public Provincia getProvincia(String id) {
        return provinciaFacade.find(id);
    }
    
    /**
     * Obtiene un listado de los paises.
     * @return listado de paises.
     */
    @Override
    public List<Pais> getPaises(){
        return paisFacade.findAll();
    }
    
    @Override
    public List<Pais> getPaisesAgpdResponsable() {
        return paisFacade.getPaisesAgpdType(PaisFacade.TypePais.RESPONSABLE);
    }
    
    @Override
    public List<Pais> getPaisesAgpdTransferInternacional() {
        return paisFacade.getPaisesAgpdType(PaisFacade.TypePais.TRANSFERENCIA_INTERNACIONAL);
    }
    
    @Override
    public List<Pais> getPaisesAgpdDeclarante() {
        return paisFacade.getPaisesAgpdType(PaisFacade.TypePais.DECLARANTE);
    }
    
    @Override
    public List<Pais> getPaisesAgpdEncargado() {
        return paisFacade.getPaisesAgpdType(PaisFacade.TypePais.ENCARGADO);
    }
    
    @Override
    public List<Pais> getPaisesAgpdAcceso() {
        return paisFacade.getPaisesAgpdType(PaisFacade.TypePais.ACCESO);
    }
}
