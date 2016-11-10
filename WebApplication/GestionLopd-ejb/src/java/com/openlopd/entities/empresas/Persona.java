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

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.lopd.Soporte;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entidad encargada de almacenar las personas de las empresas.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.1 19 de mar de 2011 Modificaciones: 19 de mar de 2011, Añadido
 * el constructor Persona(String nombre, String apellido1, String apellido2,
 * String telefono, String email, String perfil)
 */
@Entity
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Persona.findByIdEmpresa", query = ""
        + "SELECT p FROM Persona p "
        + "WHERE p.empresa.idEmpresa = :idEmpresa "
        + "  AND p.fFin IS NULL"
        + "  AND p.borrado IS NULL "
        + "  AND p.active = TRUE"),
    @NamedQuery(name = "Persona.findByIdEmpreasAndText", query = 
        "SELECT p FROM Persona p WHERE p.empresa.idEmpresa = :idEmpresa AND"
        + "(UPPER(p.nombre) like :term OR UPPER(p.apellido1) like :term OR UPPER(p.apellido2) like :term )"),
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Persona.findByPerContacto", query = "SELECT p FROM Persona p WHERE p.empresa.idEmpresa = :idEmpresa AND p.perContacto = :perContacto"),
    @NamedQuery(name = "Persona.RecoveryTest", query = ""
        + "SELECT p FROM Persona p "
        + "WHERE p.dni = :dni AND p.email = :email"),
    @NamedQuery(name = "Persona.findAutorizadosSalidaSoportes", query = ""
        + "SELECT p FROM Persona p WHERE  p.empresa = :empresa AND p.autorizadoSalidaSoportes = :autorizado "
        + "   AND p.fFin IS NULL AND p.borrado IS NULL AND p.active = TRUE"),
    @NamedQuery(name = "Persona.findAutorizadosEntradaSoportes", query = ""
        + "SELECT p FROM Persona p WHERE  p.empresa = :empresa AND p.autorizadoEntradaSoportes = :autorizado "
        + "   AND p.fFin IS NULL AND p.borrado IS NULL AND p.active = TRUE"),
    @NamedQuery(name = "Persona.findAutorizadosCopiaReproduccion", query = ""
        + "SELECT p FROM Persona p WHERE  p.empresa = :empresa AND p.autorizadoCopiaReproduccion = :autorizado "
        + "   AND p.fFin IS NULL AND p.borrado IS NULL AND p.active = TRUE"),
    @NamedQuery(name = "Persona.unsetPerContacto", query = ""
        + "UPDATE Persona p SET p.perContacto = FALSE WHERE p.perContacto = TRUE")
})
public class Persona extends DateTimeEntity implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(Persona.class);

    // <editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column(nullable = false, length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
    @Column(nullable = false, length = 25)
    private String nombre;
    @Column(nullable = false, length = 25)
    private String apellido1;
    @Column(nullable = true, length = 25)
    private String apellido2;
    @Column(nullable = true, length = 9)
    private String dni;
    @Column(nullable = false, length = 25)
    private Timestamp fInicio;
    private Timestamp fFin;
    private String usuario;
    private String perfil;
    private String telefono;
    private String email;
    private boolean perContacto;
    @ManyToMany(mappedBy = "personas")
    private List<Soporte> soportes;
    @Column (name ="aut_salida_soportes", nullable = true)
    private Boolean autorizadoSalidaSoportes;
    @Column (name ="aut_entrada_soportes", nullable = true)
    private Boolean autorizadoEntradaSoportes;
    @Column (name ="aut_copia_reproduccion", nullable = true)
    private Boolean autorizadoCopiaReproduccion;
    
    // Propiedades necedarias para la visualización de tablas en la interfaz.
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public Persona() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Inicializa el objeto con los datos de identificación y localización de la
     * persona.
     *
     * @param nombre a persona.
     * @param apellido1 Primer apellido.
     * @param apellido2 Segundo apellido en caso de que lo tenga
     * @param telefono Teléfono de contacto.
     * @param email E-mail de contacto.
     * @param perfil Perfir/cargo de la persona.
     */
    public Persona(String nombre, String apellido1, String apellido2, 
            String telefono, String email, String perfil) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.email = email;
        this.perfil = perfil;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Inicializa la entidad a través de su id.
     *
     * @param id
     */
    public Persona(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Pensado para su utilización para la creación de la persona de contacto en
     * nuevas altas.
     *
     * @param empresa Empresa a la que pertenece la persona.
     * @param nombre Nombre de la persona.
     * @param apellido1 Primer apellido de la persona.
     * @param apellido2 Segundo apellido de la persona.
     * @param fInicio Fecha de inicio.
     * @param perfil Perfil de la persona en la empresa.
     * @param telefono Telefono de la persona en la empresa.
     * @param email Email para el contacto con la persona.
     * @param perContacto Indica si es una persona de contacto.
     */
    public Persona(Empresa empresa, String nombre, String apellido1, 
            String apellido2, Timestamp fInicio, String perfil, 
            String telefono, String email, boolean perContacto) {
        this.empresa = empresa;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fInicio = fInicio;
        this.perfil = perfil;
        this.telefono = telefono;
        this.email = email;
        this.perContacto = perContacto;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getfFin() {
        return fFin;
    }

    public void setfFin(Timestamp fFin) {
        this.fFin = fFin;
    }

    public Timestamp getfInicio() {
        return fInicio;
    }

    public void setfInicio(Timestamp fInicio) {
        this.fInicio = fInicio;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPerContacto() {
        return perContacto;
    }

    public void setPerContacto(boolean perContacto) {
        this.perContacto = perContacto;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Soporte> getSoportes() {
        return soportes;
    }

    public void setSoportes(List<Soporte> soportes) {
        this.soportes = soportes;
    }

    public Boolean getAutorizadoSalidaSoportes() {
        return autorizadoSalidaSoportes;
    }

    public void setAutorizadoSalidaSoportes(Boolean autorizadoSalidaSoportes) {
        this.autorizadoSalidaSoportes = autorizadoSalidaSoportes;
    }

    public Boolean getAutorizadoEntradaSoportes() {
        return autorizadoEntradaSoportes;
    }

    public void setAutorizadoEntradaSoportes(Boolean autorizadoEntradaSoportes) {
        this.autorizadoEntradaSoportes = autorizadoEntradaSoportes;
    }

    public Boolean getAutorizadoCopiaReproduccion() {
        return autorizadoCopiaReproduccion;
    }

    public void setAutorizadoCopiaReproduccion(Boolean autorizadoCopiaReproduccion) {
        this.autorizadoCopiaReproduccion = autorizadoCopiaReproduccion;
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
    // </editor-fold>

    public String getNombreCompleto () {
        return this.nombre + " " + this.apellido1 + " " 
                + (this.apellido2 != null ? this.apellido2 : "");
    }
    
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
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", empresa=" + empresa + ", nombre=" 
                + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 
                + ", dni=" + dni + ", fInicio=" + fInicio + ", fFin=" + fFin + ", usuario=" 
                + usuario + ", perfil=" + perfil + ", telefono=" + telefono + ", email=" 
                + email + ", perContacto=" + perContacto + ", soportes=" + soportes 
                + ", autorizadoSalidaSoportes=" + autorizadoSalidaSoportes 
                + ", autorizadoEntradaSoportes=" + autorizadoEntradaSoportes 
                + ", autorizadoCopiaReproduccion=" + autorizadoCopiaReproduccion 
                + ", borrado=" + borrado + ", borradoPor=" + borradoPor + ", active=" + active + '}';
    }

    
    // </editor-fold>

    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        Integer colId = -1;

        try {
            js.put("DT_RowId", id);
            js.put("DT_RowClass", (fFin != null ? "gradeA" : "gradeB"));
            js.put("0", nombre);
            js.put("1", apellido1);
            js.put("2", usuario != null ? usuario : "");
            //Se elimina cada persona de contacto está en la empresa o sede.
//            js.put("3", perContacto ? "<div class=\"ui-icon ui-icon-check\">Si</div>" : "");
            js.put("3", fInicio != null ? ManejadorFechas.getFechaHora(fInicio.getTime(), 
                    config.getTimeZone()) : "");
            js.put("4", fFin != null ? ManejadorFechas.getFechaHora(fFin.getTime(), 
                    config.getTimeZone()) : "");
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
        Integer colId = js.length();
        try {
            js.put("5", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }    
}
