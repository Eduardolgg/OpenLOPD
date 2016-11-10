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

package com.openlopd.entities.lopd;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Persona;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registro de acceso a datos de nivel alto.
 * @author Eduardo L. García Glez.
 */
@Entity
@Table (name = "registros_accesos", schema = "lopd")
public class RegistroAcceso implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(RegistroAcceso.class);
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @Column(name = "usuario", nullable = false, length = 255)
    private String usuario;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInt;
       
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_que_accede_id")
    private Persona  usuarioQueAccede;
    @Column (name = "fecha_acceso", nullable = false)
    private Long fechaAcceso;
    @Column (name = "fichero", nullable = false, length = 50)
    private String fichero;
    @Column (name = "tipo_acceso", nullable = false)
    private TipoAccesoDocumento tipoAcceso;
    @Column (name = "autorizado", nullable = false)
    private Boolean autorizado;
    
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;

    public RegistroAcceso() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public RegistroAcceso(Persona usuarioQueAccede, Long fechaAcceso, 
            String fichero, TipoAccesoDocumento tipoAcceso, Boolean autorizado) {
        this.usuarioQueAccede = usuarioQueAccede;
        this.fechaAcceso = fechaAcceso;
        this.fichero = fichero;
        this.tipoAcceso = tipoAcceso;
        this.autorizado = autorizado;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getFechaAltaInt() {
        return fechaAltaInt;
    }

    public void setFechaAltaInt(Long fechaAltaInt) {
        this.fechaAltaInt = fechaAltaInt;
    }

    public Persona getUsuarioQueAccede() {
        return usuarioQueAccede;
    }

    public void setUsuarioQueAccede(Persona usuarioQueAccede) {
        this.usuarioQueAccede = usuarioQueAccede;
    }

    public Long getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Long fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public TipoAccesoDocumento getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(TipoAccesoDocumento tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroAcceso)) {
            return false;
        }
        RegistroAcceso other = (RegistroAcceso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.RegistroAcceso[ id=" + id + " ]";
    }
    //<editor-fold defaultstate="collapsed" desc="JSon">
    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        
        try {
            js.put("DT_RowId", id);
            //js.put("DT_RowClass", (showObsInDocs ? "gradeA" : "gradeB"));
            js.put("0", usuarioQueAccede.getNombreCompleto());
            js.put("1", ManejadorFechas.getFechaHora(fechaAcceso, config.getTimeZone()));
            //js.put("2", (showObsInDocs ? "si" : "no"));
            js.put("2", fichero);
            js.put("3", tipoAcceso);
            js.put("4", autorizado ? "Si" : "No");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla de [{}] Exception: {}", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
    
    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        return js;
    }
    //</editor-fold>
}
