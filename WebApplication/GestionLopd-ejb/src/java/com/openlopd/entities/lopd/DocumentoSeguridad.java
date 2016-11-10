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
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * Información sobre el documento de seguridad.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 de 15 de septiembre de 2012
 */
@Entity
@Table(name = "documentos_seguridad", schema = "lopd")
@NamedQueries({
    @NamedQuery(name = "DocumentoSeguridad.findActive", query = ""
    + "SELECT d FROM DocumentoSeguridad d "
    + "WHERE d.empresa = :empresa AND d.fechaBaja IS NULL ")
})
public class DocumentoSeguridad implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(DocumentoSeguridad.class);
    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
    @Column(name = "falta", nullable = false)
    private Long fechaAlta;
    @Column(name = "fbaja", nullable = true)
    private Long fechaBaja;
    @OneToOne(optional = false)
    @JoinColumn(name = "iddocumento")
    private FileDataBase documento;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public DocumentoSeguridad() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public DocumentoSeguridad(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public DocumentoSeguridad(String id, Empresa empresa, Long fechaAlta, FileDataBase documento) {
        this.id = id;
        this.empresa = empresa;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = null;
        this.documento = documento;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
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

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public FileDataBase getDocumento() {
        return documento;
    }

    public void setDocumento(FileDataBase documento) {
        this.documento = documento;
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
        if (!(object instanceof DocumentoSeguridad)) {
            return false;
        }
        DocumentoSeguridad other = (DocumentoSeguridad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.DocumentoSeguridad[ id=" + id + " ]";
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
            js.put("0", ManejadorFechas.getFechaHora(fechaAlta, config.getTimeZone()));
            js.put("1", (fechaBaja != null ? ManejadorFechas.
                    getFechaHora(fechaBaja, config.getTimeZone()) : ""));
            js.put("2", "<a href=\"/OpenLopd/download?file=" + documento.getId() + "\"><img src=\"/OpenLopd/images/pdf.png\" /></a>");
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
            js.put("3", "<a class=\"table-action-EditData\">Edit</a>");
            js.put("4", "<a href=\"/Details/17\">Details</a>");//TODO: Poner el enlace correcto.
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ",
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
