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

package com.openlopd.web.controllers.privatearea.plantillas;

import com.openlopd.entities.documentos.Plantilla;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Controla la visualización de los detalles de las plantillas.
 *
 * @author Eduardo L. García Glez.
 */
public class CPlantillasDetail implements Serializable {

    PlantillaFacadeLocal plantillaFacade = lookupPlantillaFacadeLocal();
    private String id;
    private String nombre;
    private Empresa empresa;
    private Plantilla plantilla;

    public CPlantillasDetail() {
    }

    /**
     * Busca la plantilla en el sistema de ficheros.
     *
     * @return
     */
    public Plantilla findPlantilla() {
        if (id != null && !id.isEmpty()) {
            return plantillaFacade.find(id, empresa);
        } else {
            return plantillaFacade.getActiveByName(empresa,nombre);
        }
    }

    public Plantilla getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Plantilla plantilla) {
        this.plantilla = plantilla;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private PlantillaFacadeLocal lookupPlantillaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlantillaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PlantillaFacade!com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
