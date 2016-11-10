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

package com.openlopd.entities.documentos;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión de plantillas
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Entity
@Table (name = "plantillas", schema = "public")
@NamedQueries ({
    @NamedQuery(name = "Plantilla.findByName", 
        query = "SELECT p FROM Plantilla p "
        + "WHERE p.active = :active AND p.nombre = :nombre "
        + "  AND p.empresa = :empresa AND p.borrado is NULL"),
    @NamedQuery(name = "Plantilla.findById",
        query = "SELECT p FROM Plantilla p "
        + "WHERE p.id = :id AND p.empresa = :empresa "
        + "AND p.borrado is NULL"),
    @NamedQuery(name = "Plantilla.findAllByIdEmpresa", query = ""
        + "SELECT p FROM Plantilla p "
        + "WHERE p.active = :active "
        + "  AND p.empresa.idEmpresa = :idEmpresa AND p.borrado is NULL")
})
public class Plantilla extends DateTimeEntity implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(Plantilla.class);
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column(length = 37)
    private String id;
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Column(name = "descripcion", nullable = true, length = 2000)
    private String descripcion;
    @OneToOne(optional = false)
    @JoinColumn(name = "idDocumento")
    private FileDataBase documento;
    @Column(name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column(name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column(name = "version", nullable = false, length = 7)
    private String version;
//    @Column(name = "fields", nullable = false, length = 2000)
//    private String fields;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "cod_operacion", nullable = true)
    private TipoOperacion codOperacion;
    @Column(name = "form", nullable = true, length = 2048)
    private String form;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = false)
    private Boolean active;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructor por defecto.
     */
    public Plantilla() {
        active = true;
        borrado = null;
    }

    /**
     * Constructor que inicializa el id.
     * @param id Identificador único de la empresa.
     */
    public Plantilla(String id) {
        this.id = id;
        active = true;
        borrado = null;
    }

    /**
     * Inicializa todas las propiedades de la entidad.
     * @param id Identificador único de la empresa.
     * @param nombre Nombre de la plantilla.
     * @param descripcion Descripción detalada.
     * @param plantilla Plantilla en el gestor de documentos.
     * @param fechaAlta Fecha de alta de la plantilla.
     * @param fechaBaja Fecha de baja de la plantilla.
     * @param version Versión de la plantilla. (v1.0)
     * @param empresa Empresa a la que pertenece la plantilla.
     */
    public Plantilla(String id, String nombre, String descripcion,
            FileDataBase documento, Long fechaAlta, Long fechaBaja,
            String version, Empresa empresa) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.documento = documento;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.version = version;
        this.empresa = empresa;
        active = true;
        borrado = null;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
    /**
     * Obtiene el Identificador único de la plantilla.
     * @return Identificador único de la plantilla.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Establece el Identificador único de la plantilla.
     * @param id Identificador único de la plantilla.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Obtiene la Descripcion.
     * @return Descripción detallada de la plantilla.
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Establece la Descripcion.
     * @param descripcion Descripción detallada de la plantilla.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene la empresa de la plantilla.
     * @return Empresa a la que pertenece la plantilla.
     */
    public Empresa getEmpresa() {
        return empresa;
    }
    
    /**
     * Establece la empresa de la plantilla.
     * @param empresa Empresa a la que pertenece la plantilla.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    /**
     * Obtiene el nombre de la plantilla.
     * @return Nombre que permite identificar la plantilla.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre de la plantilla.
     * @param nombre Nombre que permite identificar la plantilla.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene la fecha de alta.
     *
     * Una plantilla puede entrar en el sistema por necesitarse
     * en un nuevo desarrollo o por sustituir a una plantilla existene.
     *
     * @return Fecha de entrada de la plantilla en el sistema.
     */
    public Long getFechaAlta() {
        return fechaAlta;
    }
    
    /**
     * Establece la fecha de alta.
     *
     * Una plantilla puede entrar en el sistema por necesitarse
     * en un nuevo desarrollo o por sustituir a una plantilla existene.
     *
     * @param fechaAlta Fecha de entrada de la plantilla en el sistema.
     */
    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    /**
     * Obtiene la fecha de baja.
     *
     * La plantilla puede darse de baja por ejemplo por dejarse
     * de utilizar o por ser sustituida por otra.
     *
     * @return Fecha de baja de la plantilla en el sistema.
     */
    public Long getFechaBaja() {
        return fechaBaja;
    }
    
    /**
     * Establece la fecha de baja.
     *
     * La plantilla puede darse de baja por ejemplo por dejarse
     * de utilizar o por ser sustituida por otra.
     *
     * @param fechaBaja Fecha de baja de la plantilla en el sistema.
     */
    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    /**
     * Obtiene el número de versión de la plantilla.
     * @return Número de versión de la plantilla.
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * Establece el número de versión de la plantilla.
     * @param version Número de versión de la plantilla.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Obtiene los campos a cumplimentar en la plantilla.
     * @return Campos a cumplimentar en la plantilla.
     */
//    public String getFields() {
//        return fields;
//    }

    /**
     * Establece los campos a cumplimentar en la plantilla.
     * @param fields Campos a cumplimentar en la plantilla.
     */
//    public void setFields(String fields) {
//        this.fields = fields;
//    }
    
    /**
     * Obtiene la plantilla.
     *
     * Obtiene la plantilla del gestor documental.
     *
     * @return devuelve un <code>FileDataBase</code> con la
     * plantilla del sistema.
     */
    public FileDataBase getDocumento() {
        return documento;
    }
    
    /**
     * Establece la plantilla
     *
     * Obtiene la plantilla del gestor documental.
     *
     * @param documento recibe un <code>FileDataBase</code> con la
     * plantilla del sistema.
     */
    public void setDocumento(FileDataBase documento) {
        this.documento = documento;
    }

    /**
     * Obtiene la operación LOPD a la que pertenece la plantilla.
     * 
     * El código de operación se almacena en la base de datos como un
     * ordenal, por lo que no es recomendable realizar actualizaciónes
     * sin consultar la documentación.
     * 
     * @return operación lopd a la que pertenece la plantilla.
     */
    public TipoOperacion getCodOperacion() {
        return codOperacion;
    }

    /**
     * Establece la operación LOPD a la que pertenece la plantilla.
     * 
     * El código de operación se almacena en la base de datos como un
     * ordenal, por lo que no es recomendable realizar actualizaciónes
     * sin consultar la documentación.
     * 
     * @param codOperacion operación lopd a la que pertenece la plantilla.
     */
    public void setCodOperacion(TipoOperacion codOperacion) {
        this.codOperacion = codOperacion;
    }

    /**
     * Obtiene los campos de un formulario de datos para la plantilla.
     * @return código html del contenido del formulario.
     */
    public String getForm() {
        return form;
    }

    /**
     * Establece los campos de un formulario de datos para la plantilla.
     * @param form código html del contenido del formulario.
     */
    public void setForm(String form) {
        this.form = form;
    }
    
    /**
     * Indica si la entidad ha sido borrada.
     * @return <code>true</code> si está borrada, <code>false</code> en
     * caso contrario.
     */
    public Long getBorrado() {
        return borrado;
    }

    /**
     * Indica si la entidad ha sido borrada.
     * @param borrado <code>true</code> si está borrada, <code>false</code> en
     * caso contrario.
     */
    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }   

    /**
     * Usuario que borra la entidad.
     * @return Id del usuario que borra la entidad.
     */
    public String getBorradoPor() {
        return borradoPor;
    }

    /**
     * Usuario que borra la entidad.
     * @param borradoPor Id del usuario que borra la entidad.
     */
    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }
    
    /**
     * Obtiene si la plantilla está activa (borrada)
     * 
     * Cuando se elimina una plantilla del sistema este bit se desactiva
     * de esta forma puede mantenerse un historial.
     * 
     * @return <code>true</code> si la plantilla está activa, 
     * <code>false</code> en caso contrario.
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Establece si la plantilla está activa (borrada)
     * 
     * Cuando se elimina una plantilla del sistema este bit se desactiva
     * de esta forma puede mantenerse un historial.
     * 
     * @param active <code>true</code> si la plantilla está activa, 
     * <code>false</code> en caso contrario.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plantilla)) {
            return false;
        }
        Plantilla other = (Plantilla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.openlopd.entities.documentos.Plantilla[ id=" + id + " ]";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="JSon">
    @Override
    public JSONObject toJson(ResponseConfig config) {
        logger.debug("Método sin implementar.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        
        try {
            js.put("DT_RowId", id);
            js.put("DT_RowClass", (fechaBaja != null ? "gradeA" : "gradeB"));
            js.put("0", nombre);
            js.put("1", descripcion);
            js.put("2", version);
            js.put("3", "<a href=\"" + rb.getString("root")  
                    + "/download?file=" + documento.getId() + "\"><img src=\""
                    + rb.getString("root") + "/images/odf.png\" /></a>");
            js.put("4", ManejadorFechas.getFechaHora(fechaAlta, 
                    config.getTimeZone()));
            js.put("5", (fechaBaja != null ? ManejadorFechas.getFechaHora(fechaBaja, 
                    config.getTimeZone()) : ""));
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
            js.put("6", "<a id=\"" + this.id + "\" class=\"table-action-EditData "
                    + "ui-icon ui-icon-pencil\">Edit</a>");
            js.put("7", "<a class=\"ui-icon ui-icon-plus\""
                    + " href=\"./plantillasDetail.jsp?id="+ id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
    //</editor-fold>
    
}
