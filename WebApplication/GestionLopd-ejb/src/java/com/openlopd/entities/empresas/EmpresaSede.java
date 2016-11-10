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

package com.openlopd.entities.empresas;

import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestiona las distintas sedes de la empresa.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 03 de mayo de 2012
 */
@Entity
@Table (name = "empresas_sedes")
@NamedQueries ({
    @NamedQuery(name = "EmpresaSede.findMainSede", query = ""
        + "SELECT es FROM EmpresaSede es WHERE es.empresa = :empresa "
        + "AND es.gestionaContratLOPD = true "
        + "AND es.borrado IS NULL "
        + "AND es.active = :active"),
    @NamedQuery(name = "EmpresaSede.resetGestionaContratLOPD", query = ""
        + "UPDATE EmpresaSede e SET e.gestionaContratLOPD = :varFalse "
        + "WHERE e.empresa = :empresa "
        + "AND e.borrado IS NULL "
        + "AND e.active = true "
        + "AND e.id <> :idSede"),
    @NamedQuery(name = "EmpresaSede.findActives", query = ""
        + "SELECT es FROM EmpresaSede es WHERE es.empresa = :empresa "
        + "AND es.borrado IS NULL "
        + "AND es.active = true")
})
public class EmpresaSede implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(EmpresaSede.class);
    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column(name = "id", nullable = false, length = 37)
    private String id;
    @ManyToOne(optional = false)
    private Empresa empresa;
    private boolean gestionaContratLOPD;
    @Column(nullable=false, length=100)
    private String nombreSede;
    @Column(nullable=true, length=9)
    private String telefono;
    @Column(nullable=true, length=9)
    private String movil;
    @Column(nullable=true, length=9)
    private String fax;
    @Column(name = "direccion", length=100, nullable=true)
    private String direccion;
    @Column(nullable=true, length=9)
    private String cp;
  
    @Column(nullable=true, length=2)
    private String provincia;
    @Column(nullable=true)
    private Long localidad;
    @Column(nullable=true, length=2048)
    private String nota;
    @Column(nullable = true, length = 37)
    private String perContacto;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    //</editor-fold>
    
    @Transient
    private boolean changeData = false;

    public EmpresaSede() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public EmpresaSede(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public EmpresaSede(Empresa empresa, boolean gestionaContratLOPD, String nombreSede, 
            String telefono, String movil, String fax, String direccion, 
            String cp, String provincia, Long localidad) {
        this.empresa = empresa;
        this.gestionaContratLOPD = gestionaContratLOPD;
        this.nombreSede = nombreSede;
        this.telefono = telefono;
        this.movil = movil;
        this.fax = fax;
        this.direccion = direccion;
        this.cp = cp;
        this.provincia = provincia;
        this.localidad = localidad;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }   

    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCp() {
        return cp;
    }
    
    public void setCp(String cp) {
        if (this.cp == null || !this.cp.equals(cp)) {
            this.cp = cp;
            this.changeData = true;
        }        
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        if (this.direccion == null || !this.direccion.equals(direccion))  {
            this.direccion = direccion;
            this.changeData = true;
        }
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    /**
     * Obtiene el número de fax de la empresa.
     * @return Número de fax de la empresa.
     */
    public String getFax() {
        return fax;
    }

    /**
     * Devuelve el número de fax de la empresa.
     * @param fax Número de fax de la empresa.
     */
    public void setFax(String fax) {
        if (this.fax == null || !this.fax.equals(fax)) {
            this.fax = fax;
            this.changeData = true;
        }
    }
    
    public boolean isGestionaContratLOPD() {
        return gestionaContratLOPD;
    }
    
    public void setGestionaContratLOPD(boolean gestionaContratLOPD) {
        this.gestionaContratLOPD = gestionaContratLOPD;
    }
    
    public Long getLocalidad() {
        return localidad;
    }
    
    public void setLocalidad(Long localidad) {
        if (this.localidad != localidad) {
            this.localidad = localidad;
            this.changeData = true;
        }
    }
    
    /**
     * Obtiene el número de movil de la empresa.
     * @return Número de movil de la empresa.
     */
    public String getMovil() {
        return movil;
    }

    /**
     * Establece el número de movil de la empresa.
     * @param movil Número de movil de la empresa.
     */
    public void setMovil(String movil) {
        if (this.movil == null || !this.movil.equals(movil)) {
            this.movil = movil;
            this.changeData = true;
        }
    }
    
    public String getNombreSede() {
        return nombreSede;
    }
    
    public void setNombreSede(String nombreSede) {
        if (this.nombreSede == null || 
                this.nombreSede.equals(nombreSede)) {
            this.nombreSede = nombreSede;
            this.changeData = true;
        }
    }
    
    public String getProvincia() {
        return provincia;
    }
    
    public void setProvincia(String provincia) {
        if (this.provincia == null || !this.provincia.equals(provincia)) {
            this.provincia = provincia;
            this.changeData = true;
        }
    }   

    /**
     * Obtiene el número de teléfono de la empresa.
     * @return número de teléfono de la empresa.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono de la empresa.
     * @param telefono Número de telefono a asignar a la empresa.
     */
    public void setTelefono(String telefono) {
        if (this.telefono == null || !this.telefono.equals(telefono)) {
            this.telefono = telefono;
            this.changeData = true;
        }
    }
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getPerContacto() {
        return perContacto;
    }

    public void setPerContacto(String perContacto) {
        this.perContacto = perContacto;
    }
    
    public Long getBorrado() {
        return borrado;
    }

    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }

    public String getBorradoPor() {
        return borradoPor;
    }

    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //</editor-fold>
    
    /**
     * Indica en caso de gestionar lopd si los datos de la empresa han cambiado.
     * @return true si los datos han cambiado, false en caso contrario.
     */
    public boolean isChangeData() {
        return gestionaContratLOPD && changeData;
    }


    //<editor-fold defaultstate="collapsed" desc="Defaulth Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaSede)) {
            return false;
        }
        EmpresaSede other = (EmpresaSede) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmpresaSede{" + "id=" + id + ", empresa=" + empresa + ", gestionaContratLOPD=" 
                + gestionaContratLOPD + ", nombreSede=" + nombreSede + ", telefono=" 
                + telefono + ", movil=" + movil + ", fax=" + fax + ", direccion=" 
                + direccion + ", cp=" + cp + ", provincia=" + provincia + ", localidad=" 
                + localidad + ", nota=" + nota + ", perContacto=" + perContacto + ", borrado=" 
                + borrado + ", borradoPor=" + borradoPor + ", active=" + active 
                + ", changeData=" + changeData + '}';
    }
    
    //</editor-fold>

    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();

        try {
            js.put("DT_RowId", id);
            js.put("DT_RowClass", (gestionaContratLOPD ? "gradeA" : null));
            js.put("0", nombreSede);
            js.put("1", direccion != null ? direccion : "");
            js.put("2", telefono != null ? telefono : "");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        try {
//            js.put("3", "<a id=\"" + this.id + "\" class=\"table-action-EditData "
//                    + "ui-icon ui-icon-pencil\">Edit</a>");
            js.put("3", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }
    
}
