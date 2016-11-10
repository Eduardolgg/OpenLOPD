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

package com.openlopd.common.ajax;

import com.openlopd.common.localizacion.business.LocalizacionLocal;
import com.openlopd.common.localizacion.entities.Localidad;
import com.openlopd.common.localizacion.entities.Provincia;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Acceso a los listados de localidades y provincias.
 * @author Eduardo L. García Glez.
 */
 //TODO parece estar duplicado con AuxTables, solucionar.
public class localidades {
    private static Logger logger = LoggerFactory.getLogger(localidades.class);
    LocalizacionLocal localizacion = lookupLocalizacionLocal();
    private String idProvincia;

    /**
     * Obtiene el id de la provincia actual.
     * @return Id de la provincia.
     */
    public String getIdProvincia() {
        return idProvincia;
    }

    /**
     * Establece el id de la provincia a utilizar.
     * @param idProvincia Id de la provincia.
     */
    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    /**
     * Obtiene las localidades de la provincia actual.
     * @return Listado de localidades.
     */
    public List<Localidad> getLocalidades(){
        return localizacion.getLocalidades(idProvincia);
    }
    
    /**
     * Obtiene un listado de las provincias de la BBDD
     * @return Listado de provincias.
     */
    public List<Provincia> getProvincias(){
        return localizacion.getProvincias();
    }

    //<editor-fold defaultstate="collapsed" desc="Lookup">
    private LocalizacionLocal lookupLocalizacionLocal() {
        try {
            Context c = new InitialContext();
            return (LocalizacionLocal) c.lookup("java:global/GestionLopd/common-ejb/Localizacion!com.openlopd.common.localizacion.business.LocalizacionLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean localización mediante lookup.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
