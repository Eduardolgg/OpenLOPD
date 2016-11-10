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
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
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
@Table(name = "incidencias", schema = "lopd")
public class Incidencia extends DateTimeEntity implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(Incidencia.class);
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToOne(optional = false)
    private Empresa empresa;
    @Column (name = "codigo", nullable = false, length = 12)
    private String codigo;
    @Column(name = "tipo_incidencia", nullable = false, length = 1000)
    private String tipoIncidencia;
    // Propiedades para medidas de nivel básico.
    @Column(name = "fecha_incidencia", nullable = false)
    private Long fechaIncidencia;
    @Column(name = "deteccion", nullable = true)
    private Boolean deteccion;
    @Column(name = "notificado_por", nullable = false, length = 50)
    private String notificadoPor;
    @Column(name = "notificado_a", nullable = true, length = 50)
    private String notificadoA;
    @Column(name = "efectos_derivados", nullable = true, length = 20000)
    private String efectosDerivados;
    @Column(name = "medidas_correctoras", nullable = true, length = 20000)
    private String medidasCorrectoras;
    @Column(name = "sistema_afectado", nullable = true, length = 1000)
    private String sistemaAfectado;
    // Propiedades para Medidas de nivel Medio
    @Column(name = "persona_ejecutora", nullable = true, length = 1000)
    private String personaEjecutora;
    @Column(name = "datos_restaurados", nullable = true, length = 20000)
    private String datosRestaurados;
    @Column(name = "datos_restaurados_manualmente", nullable = true, length = 20000)
    private String datosRestauradosManualmente;
    @Column(name = "protocolo_utilizado", nullable = true, length = 20000)
    private String protocoloUtilizado;
    @ManyToOne
    @JoinColumn(name = "id_documento")
    private FileDataBase autorizacion;
    // Propiedades Internas
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

    public Incidencia() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Incidencia(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Incidencia(String id, Empresa empresa, String tipoIncidencia,
            Long fechaIncidencia, Boolean deteccion, String notificadoPor,
            String notificadoA, String sistemaAfectado, Long fechaAltaInt) {
        this.id = id;
        this.empresa = empresa;
        this.tipoIncidencia = tipoIncidencia;
        this.fechaIncidencia = fechaIncidencia;
        this.deteccion = deteccion;
        this.notificadoPor = notificadoPor;
        this.notificadoA = notificadoA;
        this.sistemaAfectado = sistemaAfectado;
        this.fechaAltaInt = fechaAltaInt;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Obtiene la clave primaria de la entidad.
     *
     * @return Clave primaria.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece la clave primaria de la entidad.
     *
     * @param id Clave primaria.
     */
    public void setId(String id) {
        this.id = id;
    }
//
//    /**
//     * Obtiene una escripción sobre la entidad.
//     * 
//     * Descripción del usuario sobre la entidad.
//     * También se podría proveer de descripciones estándar.
//     * 
//     * @return Descripción sobre la entidad.
//     */
//    public String getDescripcion() {
//        return Descripcion;
//    }
//
//    /**
//     * Establece una descripción sobre la entidad.
//     * 
//     * Descripción del usuario sobre la entidad.
//     * También se podría proveer de descripciones estándar.
//     * 
//     * @param Descripcion Descripción sobre la entidad.
//     */
//    public void setDescripcion(String Descripcion) {
//        this.Descripcion = Descripcion;
//    }
//
//    /**
//     * Obtiene la fecha de alta de la entidad en el sistema de información
//     * de la empresa.
//     * 
//     * @return fecha de alta en SI.
//     */
//    public Long getFechaAlta() {
//        return fechaAlta;
//    }
//
//    /**
//     * Establece la fecha de alta de la entidad en el sistema de información
//     * de la empresa.
//     * 
//     * @param fechaAlta Fecha de alta en SI.
//     */
//    public void setFechaAlta(Long fechaAlta) {
//        this.fechaAlta = fechaAlta;
//    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de alta de la entidad en la base de datos.
     *
     * @return Fecha de alta en DB.
     */
    public Long getFechaAltaInt() {
        return fechaAltaInt;
    }

    /**
     * Establece la fecha de alta de la entidad en la base de datos.
     *
     * @param fechaAltaInt Fecha de alta en DB.
     */
    public void setFechaAltaInt(Long fechaAltaInt) {
        this.fechaAltaInt = fechaAltaInt;
    }

//    /**
//     * Establece la fecha de baja de la entidad en el sistema de 
//     * información de la empresa.
//     * 
//     * @return Fecha de baja en el SI.
//     */
//    public Long getFechaBaja() {
//        return fechaBaja;
//    }
//
//    /**
//     * Establece la fecha de baja de la entidad en el sistema de 
//     * información de la empresa.
//     * 
//     * @param fechaBaja 
//     */
//    public void setFechaBaja(Long fechaBaja) {
//        this.fechaBaja = fechaBaja;
//    }
    /**
     * Obtiene la fecha de baja de la entidad en la base de datos.
     *
     * @return Fecha de baja en BD.
     */
    public Long getFechaBajaInt() {
        return fechaBajaInt;
    }

    /**
     * Obtiene la fecha de baja de la entidad en la base de datos.
     *
     * @param fechaBajaInt Fecha de Baja en BD.
     */
    public void setFechaBajaInt(Long fechaBajaInt) {
        this.fechaBajaInt = fechaBajaInt;
    }

//    /**
//     * Obtiene las observaciones sobre la entidad realizadas por el usuario.
//     * 
//     * @return Texto de las observaciones.
//     */
//    public String getObservaciones() {
//        return Observaciones;
//    }
//
//    /**
//     * Establece las observaciones sobre la entidad realizadas por el usuario.
//     * 
//     * @param Observaciones Texto de las observaciones.
//     */
//    public void setObservaciones(String Observaciones) {
//        this.Observaciones = Observaciones;
//    }
    /**
     * Obtiene la empresa a la que pertenece la entidad.
     *
     * @return información de la empresa a la que pertenece la entidad.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Establece la empresa a la que pertenece la entidad.
     *
     * @param empresa información de la empresa a la que pertenece la entidad.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public Long getFechaIncidencia() {
        return fechaIncidencia;
    }

    public void setFechaIncidencia(Long fechaIncidencia) {
        this.fechaIncidencia = fechaIncidencia;
    }

    public Boolean getDeteccion() {
        return deteccion;
    }

    public void setDeteccion(Boolean deteccion) {
        this.deteccion = deteccion;
    }

    public String getNotificadoPor() {
        return notificadoPor;
    }

    public void setNotificadoPor(String notificadoPor) {
        this.notificadoPor = notificadoPor;
    }

    public String getNotificadoA() {
        return notificadoA;
    }

    public void setNotificadoA(String notificadoA) {
        this.notificadoA = notificadoA;
    }

    public String getEfectosDerivados() {
        return efectosDerivados;
    }

    public void setEfectosDerivados(String efectosDerivados) {
        this.efectosDerivados = efectosDerivados;
    }

    public String getMedidasCorrectoras() {
        return medidasCorrectoras;
    }

    public void setMedidasCorrectoras(String medidasCorrectoras) {
        this.medidasCorrectoras = medidasCorrectoras;
    }

    public String getSistemaAfectado() {
        return sistemaAfectado;
    }

    public void setSistemaAfectado(String sistemaAfectado) {
        this.sistemaAfectado = sistemaAfectado;
    }

    public String getPersonaEjecutora() {
        return personaEjecutora;
    }

    public void setPersonaEjecutora(String personaEjecutora) {
        this.personaEjecutora = personaEjecutora;
    }

    public String getDatosRestaurados() {
        return datosRestaurados;
    }

    public void setDatosRestaurados(String datosRestaurados) {
        this.datosRestaurados = datosRestaurados;
    }

    public String getDatosRestauradosManualmente() {
        return datosRestauradosManualmente;
    }

    public void setDatosRestauradosManualmente(String datosRestauradosManualmente) {
        this.datosRestauradosManualmente = datosRestauradosManualmente;
    }

    public String getProtocoloUtilizado() {
        return protocoloUtilizado;
    }

    public void setProtocoloUtilizado(String protocoloUtilizado) {
        this.protocoloUtilizado = protocoloUtilizado;
    }

    public FileDataBase getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(FileDataBase autorizacion) {
        this.autorizacion = autorizacion;
    }

//    /**
//     * Obtiene si las observaciones deben mostrarse en los documentos.
//     * 
//     * Este bit indica si las observaciones realizadas sobre la entidad
//     * son aptas para ser incluidas en los documentos generados, este
//     * bit se establece a petición del usuario.
//     * 
//     * @return <code>true</code> si las observaciones deben se incluidas,
//     * <code>false</code> en caso contrario.
//     */
//    public Boolean getShowObsInDocs() {
//        return showObsInDocs;
//    }
//
//    /**
//     * Establece si las observaciones deben mostrarse en los documentos.
//     * 
//     * Este bit indica si las observaciones realizadas sobre la entidad
//     * son aptas para ser incluidas en los documentos generados, este
//     * bit se establece a petición del usuario.
//     * 
//     * @param showObsInDocs <code>true</code> si las observaciones deben se incluidas,
//     * <code>false</code> en caso contrario.
//     */
//    public void setShowObsInDocs(Boolean showObsInDocs) {
//        this.showObsInDocs = showObsInDocs;
//    }
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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Incidencia other = (Incidencia) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Incidencia{" + "id=" + id + ", empresa=" + empresa
                + ", tipoIncidencia=" + tipoIncidencia
                + ", fechaIncidencia=" + fechaIncidencia + ", deteccion=" + deteccion
                + ", notificadoPor=" + notificadoPor + ", notificadoA=" + notificadoA
                + ", efectosDerivados=" + efectosDerivados
                + ", medidasCorrectoras=" + medidasCorrectoras
                + ", sistemaAfectado=" + sistemaAfectado
                + ", personaEjecutora=" + personaEjecutora
                + ", datosRestaurados=" + datosRestaurados
                + ", datosRestauradosManualmente=" + datosRestauradosManualmente
                + ", protocoloUtilizado=" + protocoloUtilizado
                + ", autorizacion=" + autorizacion + ", fechaAltaInt=" + fechaAltaInt
                + ", fechaBajaInt=" + fechaBajaInt + ", borrado=" + borrado
                + ", borradoPor=" + borradoPor + ", active=" + active + '}';
    }

    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getSubString(String text) {
        
        if (text == null) {
            return "";
        }

        if (text.length() >= 255) {
            return text.substring(0, 255) + "...";
        } else {
            return text;
        }
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();

        try {
            js.put("DT_RowId", id);
//            js.put("DT_RowClass", (showObsInDocs ? "gradeA" : "gradeB"));
            js.put("0", codigo);
            js.put("1", ManejadorFechas.getFechaHora(fechaIncidencia, config.getTimeZone()));
            js.put("2", getSubString(notificadoA));
            js.put("3", getSubString(sistemaAfectado));
            js.put("4", getSubString(medidasCorrectoras));
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
        try {
            js.put("5", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ",
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
