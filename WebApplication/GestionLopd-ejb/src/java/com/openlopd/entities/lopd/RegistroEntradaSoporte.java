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
import java.util.Date;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "registro_entrada_soportes", schema = "lopd")
public class RegistroEntradaSoporte implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(RegistroEntradaSoporte.class);
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "tipo_soporte_id")
    private TipoSoporte tipoSoporte;
    @Column(name = "observaciones", nullable = true, length = 20000)
    private String Observaciones;
    @Column(name = "show_obs_in_docs", nullable = true)
    private Boolean showObsInDocs;
    @Column(name = "fecha_entrada", nullable = false)
    private Long FechaEntrada;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Column(name = "tipo_info", nullable = false)
    private String tipoInfo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "persona_autorizada_id")
    private Persona personaAutorizada;
    @Column (name = "persona_que_entrega", nullable = false, length = 50)
    private String personaQueEntrega;
    @Column (name = "modo_envio", nullable = false, length = 255)
    private String modoEnvio;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInt;
    @Column(name = "fecha_baja_int", nullable = true)
    private Long fechaBajaInt;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;

    public RegistroEntradaSoporte() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public RegistroEntradaSoporte(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public RegistroEntradaSoporte(String id, Empresa empresa, TipoSoporte tipoSoporte,
            String Observaciones, Boolean showObsInDocs, Long FechaEntrada,
            Integer cantidad, String tipoInfo, Persona personaAutorizada,
            String personaQueEntrega, String modoEnvio,
            Long fechaAltaInt, Long fechaBajaInt) {
        this.id = id;
        this.empresa = empresa;
        this.tipoSoporte = tipoSoporte;
        this.Observaciones = Observaciones;
        this.showObsInDocs = showObsInDocs;
        this.FechaEntrada = FechaEntrada;
        this.cantidad = cantidad;
        this.tipoInfo = tipoInfo;
        this.personaAutorizada = personaAutorizada;
        this.personaQueEntrega = personaQueEntrega;
        this.modoEnvio = modoEnvio;
        this.fechaAltaInt = fechaAltaInt;
        this.fechaBajaInt = fechaBajaInt;
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

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public Long getFechaEntrada() {
        return FechaEntrada;
    }

    public void setFechaEntrada(Long FechaEntrada) {
        this.FechaEntrada = FechaEntrada;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Persona getPersonaAutorizada() {
        return personaAutorizada;
    }

    public void setPersonaAutorizada(Persona personaAutorizada) {
        this.personaAutorizada = personaAutorizada;
    }

    public String getPersonaQueEntrega() {
        return personaQueEntrega;
    }

    public void setPersonaQueEntrega(String personaQueEntrega) {
        this.personaQueEntrega = personaQueEntrega;
    }

    public String getModoEnvio() {
        return modoEnvio;
    }

    public void setModoEnvio(String modoEnvio) {
        this.modoEnvio = modoEnvio;
    }

    public String getTipoInfo() {
        return tipoInfo;
    }

    public void setTipoInfo(String tipoInfo) {
        this.tipoInfo = tipoInfo;
    }

    public TipoSoporte getTipoSoporte() {
        return tipoSoporte;
    }

    public void setTipoSoporte(TipoSoporte tipoSoporte) {
        this.tipoSoporte = tipoSoporte;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegistroEntradaSoporte other = (RegistroEntradaSoporte) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Indica si la entidad ha sido borrada.
     *
     * @return <code>true</code> si está borrada, <code>false</code> en caso
     * contrario.
     */
    public Long getBorrado() {
        return borrado;
    }

    /**
     * Indica si la entidad ha sido borrada.
     *
     * @param borrado <code>true</code> si está borrada, <code>false</code> en
     * caso contrario.
     */
    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }

    /**
     * Usuario que borra la entidad.
     *
     * @return Id del usuario que borra la entidad.
     */
    public String getBorradoPor() {
        return borradoPor;
    }

    /**
     * Usuario que borra la entidad.
     *
     * @param borradoPor Id del usuario que borra la entidad.
     */
    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }

    /**
     * Obtiene si la plantilla está activa (borrada)
     *
     * Cuando se elimina una plantilla del sistema este bit se desactiva de esta
     * forma puede mantenerse un historial.
     *
     * @return <code>true</code> si la plantilla está activa, <code>false</code>
     * en caso contrario.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece si la plantilla está activa (borrada)
     *
     * Cuando se elimina una plantilla del sistema este bit se desactiva de esta
     * forma puede mantenerse un historial.
     *
     * @param active <code>true</code> si la plantilla está activa,
     * <code>false</code> en caso contrario.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "RegistroEntradaSoporte{" + "id=" + id + ", empresa=" + empresa + ", tipoSoporte=" + tipoSoporte + ", Observaciones=" + Observaciones + ", showObsInDocs=" + showObsInDocs + ", FechaEntrada=" + FechaEntrada + ", cantidad=" + cantidad + ", tipoInfo=" + tipoInfo + ", personaAutorizada=" + personaAutorizada + ", fechaAltaInt=" + fechaAltaInt + ", fechaBajaInt=" + fechaBajaInt + '}';
    }

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
            js.put("0", tipoSoporte.getDescripcion());
            js.put("1", (Observaciones != null ? Observaciones : ""));
            //js.put("2", (showObsInDocs ? "si" : "no"));
            js.put("2", ManejadorFechas.getFechaHora(FechaEntrada, config.getTimeZone()));
            js.put("3", cantidad);
            js.put("4", tipoInfo);
            js.put("5", personaAutorizada.getNombre());
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla de [{}] Exception: ",
                    this.toString(), e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        return js;
    }
}
