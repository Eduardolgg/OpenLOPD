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
import com.openlopd.agpd.nota.tablascomunes.TipoSolicitud;
import com.openlopd.business.lopd.FileRegisterBot;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ficheros en la AEPD.
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "ficheros", schema = "lopd")
@NamedQueries ({
    @NamedQuery(name = "Fichero.findActives", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.empresa = :empresa AND f.borrado IS NULL "
        + "  AND f.active = true "),
    @NamedQuery(name = "Fichero.findNoRegistrados", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE ( f.error IS NULL OR f.error <> :errorCodeOK ) "
        + "  AND f.active = true AND f.borrado IS NULL "),
    @NamedQuery(name = "Fichero.findPendienteFirma", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.empresa = :empresa AND f.borrado IS NULL"
        + "  AND f.active = false"),
    @NamedQuery(name = "Fichero.findByNivel", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.nivel = :nivel "
        + "  AND f.borrado IS NULL "
        + "  AND f.active = true "),
    @NamedQuery(name = "Fichero.findFicherosDisponiblesPersona", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.empresa = :empresa AND f.borrado IS NULL "
        + "  AND f.active = TRUE AND f.nombre NOT IN ("
        + "             SELECT pf.id.nombreFichero FROM PersonaFichero pf "
        + "             WHERE pf.id.idPersona = :idPersona"
        + ")"),
    @NamedQuery(name = "Fichero.findFicherosHabilitadosPersona", query = ""
        + "SELECT f FROM Fichero f, PersonaFichero pf "
        + "WHERE f.nombre = pf.id.nombreFichero AND pf.id.idPersona = :idPersona "
        + "  AND f.empresa = :empresa AND f.borrado IS NULL "
        + "  AND f.active = TRUE "),
    @NamedQuery(name = "Fichero.findPerfiles", query = ""
        + "SELECT DISTINCT(p.perfil) "
        + "FROM Fichero f, Persona p, PersonaFichero pf "
        + "WHERE f.nombre = pf.id.nombreFichero "
        + "  AND p.id = pf.id.idPersona "
        + "  AND f.empresa = :empresa AND p.empresa = :empresa "
        + "  AND f.nombre = :nombre "
        + "ORDER BY p.perfil ASC"),
    @NamedQuery(name = "Fichero.findByNombre", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.nombre = :nombre "
        + "  AND f.empresa = :empresa "
        + "  AND f.active = true "
        + "  AND f.borrado IS NULL"),
    @NamedQuery(name = "Fichero.findWithoutIncrpCod", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.codInscripcion IS NULL "
        + "  AND f.empresa = :empresa "
        + "  AND f.active = true "
        + "  AND f.borrado IS NULL"),
    @NamedQuery(name = "Fichero.findWithErrors", query = ""
        + "SELECT f FROM Fichero f "
        + "WHERE f.error IS NOT NULL "
        + "  AND f.error <> :errorCode "
        + "  AND f.active = true "
        + "  AND f.borrado IS NULL")
})
public class Fichero extends DateTimeEntity implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(Fichero.class);
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @Column(name ="usuario", nullable = false, length = 255)
    private String usuario;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "nombre_interno", nullable = false, length = 37)
    private String nombreInterno;
    @Column(name = "descripcion", nullable = false, length = 350)
    private String descripcion;
    @Column(name = "numero_de_Registro", nullable = true, length = 50)
    private String numeroDeRegistro;
    @Column(name = "fecha_hora_registro", nullable = true, length = 50)
    private String fechaHoraRegistro;
    @Column(name = "cod_inscripcion", nullable = true, length = 50)
    private String codInscripcion;
    @Enumerated(EnumType.STRING)
    @Column (name = "nivel", nullable = false, length = 10)
    private TipoNivelSeguridad nivel;
    @Enumerated(EnumType.STRING)
    @Column (name = "tipo", nullable = false, length = 12)
    private TipoDeFichero tipo;
    @Column (name = "cesiones", nullable = true, length = 1024)
    private String cesiones;
    @Column (name = "tipo_datos", nullable = true, length = 1024)
    private String tipoDatos;
    @Column (name = "origen_datos", nullable = true, length = 1024)
    private String origenDatos;
    @Column (name = "transferencias_internacionales", nullable = true, length = 1024)
    private String transferenciasInternacionales;
    @Enumerated(EnumType.STRING)
    @Column (name = "accion", nullable = false, length = 12)
    private TipoSolicitud accion;
    @OneToOne
    private FileDataBase solicitud;
    @OneToOne
    private FileDataBase respuesta;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInterna;
    @Column(name = "error", nullable = true, length = 2)
    private String error;
    @Column(name = "fecha_error", nullable = true)
    private Long fechaError;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;

    public Fichero() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Fichero(String id, Empresa empresa, String nombre, 
            String nombreInterno) {
        this.id = id;
        this.empresa = empresa;
        this.nombre = nombre;
        this.nombreInterno = nombreInterno;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreInterno() {
        return nombreInterno;
    }

    public void setNombreInterno(String nombreInterno) {
        this.nombreInterno = nombreInterno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    public void setNumeroDeRegistro(String numeroDeRegistro) {
        this.numeroDeRegistro = numeroDeRegistro;
    }

    public String getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(String fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public String getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(String codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public FileDataBase getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(FileDataBase solicitud) {
        this.solicitud = solicitud;
    }

    public FileDataBase getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(FileDataBase respuesta) {
        this.respuesta = respuesta;
    }

    public Long getFechaAltaInterna() {
        return fechaAltaInterna;
    }
    
    public void setFechaAltaInterna(Long fechaAltaInterna) {
        this.fechaAltaInterna = fechaAltaInterna;
    }

    public TipoNivelSeguridad getNivel() {
        return nivel;
    }

    public void setNivel(TipoNivelSeguridad nivel) {
        this.nivel = nivel;
    }

    public TipoDeFichero getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeFichero tipo) {
        this.tipo = tipo;
    }

    public String getCesiones() {
        return cesiones;
    }

    public void setCesiones(String cesiones) {
        this.cesiones = cesiones;
    }

    public String getTipoDatos() {
        return tipoDatos;
    }

    public void setTipoDatos(String tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public String getOrigenDatos() {
        return origenDatos;
    }

    public void setOrigenDatos(String origenDatos) {
        this.origenDatos = origenDatos;
    }

    public String getTransferenciasInternacionales() {
        return transferenciasInternacionales;
    }

    public void setTransferenciasInternacionales(String transferenciasInternacionales) {
        this.transferenciasInternacionales = transferenciasInternacionales;
    }

    public TipoSolicitud getAccion() {
        return accion;
    }

    public void setAccion(TipoSolicitud accion) {
        this.accion = accion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getFechaError() {
        return fechaError;
    }

    public void setFechaError(Long fechaError) {
        this.fechaError = fechaError;
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
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fichero other = (Fichero) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fichero{" + "id=" + id + ", empresa=" + empresa + ", nombre=" + nombre + ", numeroDeRegistro=" + numeroDeRegistro + ", fechaHoraRegistro=" + fechaHoraRegistro + ", solicitud=" + solicitud + ", respuesta=" + respuesta + '}';
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
//            js.put("DT_RowClass", (fechaBaja != null ? "gradeA" : "gradeB"));
            js.put("0", nombre);
            js.put("1", nivel);
            js.put("2", numeroDeRegistro != null ? numeroDeRegistro : "");
            js.put("3", ManejadorFechas.getFechaHora(fechaAltaInterna, config.getTimeZone()));
            js.put("4", fechaHoraRegistro != null ? fechaHoraRegistro : "");
            js.put("5", codInscripcion != null ? codInscripcion : "");
            js.put("6", (error != null && error.equals(FileRegisterBot.ENVIO_OK) ? "<span class=\"ui-icon ui-icon-check\"></span>" 
                    : "<span class=\"ui-icon ui-icon-refresh\"></span>"));
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
        //TODO: automatizar titularidad.
        try {
            js.put("7", (error != null && error.equals(FileRegisterBot.ENVIO_OK) && codInscripcion != null ? 
                    "<a class=\"ui-icon "
                    + "ui-icon-pencil\" href=\"./nota.jsp?id="
                    + this.id + "&accion=" + TipoSolicitud.MODIFICACION.getValue()
                    + "&titularidad=2\">Modificación</a>" 
                    : "<span title=\"No se puede modificar un fichero que está en proceso "
                    + "de registro o del que se carece del código de inscripción\" "
                    + "class=\"ui-icon ui-icon-pencil ui-state-disabled\"></span>"));
            js.put("8", (error != null && error.equals(FileRegisterBot.ENVIO_OK) && codInscripcion != null ? 
                    "<a class=\""
                    + "ui-icon ui-icon-trash\" href=\"./nota.jsp?id="
                    + this.id + "&accion=" + TipoSolicitud.SUPRESION.getValue()
                    + "&titularidad=2\">Supresión</a>" 
                    : "<span title=\"No se puede suprimir un fichero que está en proceso "
                    + "de registro o del que se carece del código de inscripción\" "
                    + "class=\"ui-icon ui-icon-trash ui-state-disabled\"></span>"));
//            js.put("9", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
//                    + this.id + "\">Detalles</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
