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

package com.openlopd.entities.facturacion;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión de las facturas emitidas.
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Entity
@Table(name = "facturas")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findById", query = "SELECT f FROM Factura f WHERE f.id = :id"),
    @NamedQuery(name = "Factura.findByIdEmpresa", query = "SELECT f FROM Factura f WHERE f.empresa = :idEmpresa")})
public class Factura implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(Factura.class);    
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;   
    @ManyToOne(optional = false)
    @JoinColumn(name = "idtipoformapago")
    private TipoFormaPago tipoFormaPago;
    @Column(name = "importe", precision = 6, scale = 2, nullable = false)
    private BigDecimal importe;
    @Column(nullable = false)
    private Long fecha;
    @OneToOne(optional = false)
    @JoinColumn(name = "iddocumento")
    private FileDataBase documento;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    private List<Producto> productos;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public Factura() {
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }

    public Factura(TipoFormaPago tipoFormaPago, BigDecimal importe) {
        this.tipoFormaPago = tipoFormaPago;
        this.importe = importe;
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
        
    }
    
    public Factura(short tipoFormaPago, BigDecimal importe) {
        this.tipoFormaPago = new TipoFormaPago(tipoFormaPago);
        this.importe = importe;
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }

    /**
     * Inicializa el objeto con todos los parámetros.
     * @param id
     * @param empresa
     * @param tipoFormaPago
     * @param importe
     * @param fecha 
     */
    public Factura(String id, Empresa empresa, TipoFormaPago tipoFormaPago, BigDecimal importe, Long fecha) {
        this.id = id;
        this.empresa = empresa;
        this.tipoFormaPago = tipoFormaPago;
        this.importe = importe;
        this.fecha = fecha;
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public FileDataBase getDocumento() {
        return documento;
    }

    public void setDocumento(FileDataBase documento) {
        this.documento = documento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public TipoFormaPago getTipoFormaPago() {
        return tipoFormaPago;
    }

    public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
        this.tipoFormaPago = tipoFormaPago;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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
    public boolean isActive() {
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
    public void setActive(boolean active) {
        this.active = active;
    }   

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", empresa=" + empresa + ", tipoFormaPago=" 
                + tipoFormaPago + ", importe=" + importe + ", fecha=" + fecha 
                + ", documento=" + documento + ", borrado=" + borrado + ", borradoPor=" 
                + borradoPor + ", active=" + active + ", productos=" + productos + '}';
    }
    // </editor-fold>
    
    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        
        try {
            js.put("DT_RowId", id);
            js.put("0", ManejadorFechas.getFechaHora(fecha, config.getTimeZone()));
            js.put("1", tipoFormaPago.getDescripcion());
            js.put("2", importe);
            js.put("3", "<a href=\"" + rb.getString("root") + "/download?file=" 
                    + documento.getId() + "\"><img src=\"" + rb.getString("root") 
                    + "/images/pdf.png\" /></a>");
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
            js.put("4", "<a class=\"table-action-EditData\">Edit</a>");
            js.put("5", "<a href=\"/Details/17\">Details</a>");//TODO: Poner el enlace correcto.
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }  
    }
}
