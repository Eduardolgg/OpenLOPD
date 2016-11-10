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

package com.openlopd.business.Empresas;

import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Entity
public class EmpresaInfo implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(EmpresaInfo.class);
    
    @Id
    private String idEmpresa;
    private String cif;
    private String razonSocial;
    private String nombre;
    private String actividad;
    private String perContacto;
    private String mailContacto;
    private String telefono1;
    private String movil;
    private String fax;
    private Integer rank;
    private Boolean gestor;
    
    public EmpresaInfo() {
        this.rank = 0;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMailContacto() {
        return mailContacto;
    }

    public void setMailContacto(String mailContacto) {
        this.mailContacto = mailContacto;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerContacto() {
        return perContacto;
    }

    public void setPerContacto(String perContacto) {
        this.perContacto = perContacto;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public Boolean getGestor() {
        return gestor;
    }

    public void setGestor(Boolean gestor) {
        this.gestor = gestor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpresaInfo other = (EmpresaInfo) obj;
        if ((this.idEmpresa == null) ? (other.idEmpresa != null) : !this.idEmpresa.equals(other.idEmpresa)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.idEmpresa != null ? this.idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.Empresa[id=" + idEmpresa + "]";
    }
    // </editor-fold>

    @Override
    public JSONObject toJson(ResponseConfig config) {
        logger.debug("No se puede crear el objeto, el método no está definido.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private String getGrade() {
        return gestor ? "gradeG" : rankToGrade();
    }

    private String rankToGrade(){
        if(rank >= 0 && rank < 10)
            return "gradeX";
        else if (rank >= 10 && rank < 20)
            return "gradeU";
        else if (rank >= 20 && rank < 30)
            return "gradeC";    
        else
            return "gradeA";   
    }
    
    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        try {
            js.put("DT_RowId", idEmpresa);
            js.put("DT_RowClass", getGrade());
            js.put("0", cif);
            js.put("1", razonSocial);
            js.put("2", (actividad != null ? actividad : ""));
            js.put("3", perContacto);
            js.put("4", (mailContacto != null ? mailContacto : ""));
            js.put("5", (telefono1 != null ? telefono1 : ""));
            js.put("6", (movil != null ? movil : ""));
            js.put("7", (fax != null ? fax : ""));
            js.put("8", rank);
            return js;
        } catch (JSONException e) {
            logger.error("Generando el json de [{}] Exception: {} ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        try {            
//            js.put("9", "<a id=\"" + this.idEmpresa + "\" class=\"table-action-EditData ui-icon ui-icon-pencil\">Edit</a>");
            js.put("9", "<a class=\"ui-icon ui-icon-home\" href=\"./setHome.jsp?id="
                    + this.idEmpresa + "\">Home</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Generando el json de [{}] Exception: {} ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
