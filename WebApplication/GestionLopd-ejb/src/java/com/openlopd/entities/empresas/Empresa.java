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
import java.util.List;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entidad encargada del almacenamiento de los datos identificativos de una
 * empresa
 * @author Eduardo L. García Glez.
 * Fecha: 13 de ene de 2011
 * @version 1.0.1
 * Modificaciones:
 *    19 de mar de 2011, Añadido el método Empresa(String cif, String razonSocial, String telefono, String movil, String fax)
 */
@Entity
@Table(name = "empresas")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByCif", query = "SELECT e FROM Empresa e WHERE e.cif = :cif"),
    @NamedQuery(name = "Empresa.RecoveryTest", query = "SELECT e FROM Empresa e WHERE e.cif = :cif AND e.mailContacto = :email"),
    @NamedQuery(name = "Empresa.findMadre", query = ""
        + " SELECT e "
        + " FROM Empresa e, "
        + "      GestoresEmpresas ge  "
        + " WHERE e.cif = ge.gestoresEmpresasPK.cifGestor "
        + "   AND ge.gestoresEmpresasPK.idEmpresa = :idEmpresaHija ")
})
public class Empresa implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(Empresa.class);
    
    // <editor-fold defaultstate="expanded" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idEmpresa", nullable = false, length = 37)
    private String idEmpresa;
    @Column(nullable=false, length=10)
    private String cif;
    @Column(nullable=false, length=100)
    private String razonSocial;
    @Column(nullable=true, length=100)
    private String nombre;
    // TODO: puede ser de interés añadir o un listado de actividades o las actividades
    // que indica la agpd.
    @Column(nullable=true, length=100)   // TODO: puede ser de interés añadir o un listado de
    private String actividad;
    @Column(nullable=false, length=37)
    private String perContacto;
    @Column(nullable=true, length=100)
    private String mailContacto;   // Mail para contactar con la empresa.
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "empresa")
    private List<EmpresaSede> sedes;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    // </editor-fold>
    
    @Transient
    private boolean changePercontacto = false;
    @Transient
    private boolean changeNombreActividad = false;

    // <editor-fold defaultstate="expanded" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public Empresa(){
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Inicializa la entidad por su ID.
     * @param idEmpresa Identificador único de la empresa.
     */
    public Empresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }
    
    /**
     * Permite inicializar el objeto con los dátos basicos de información de la empresa
     * @param cif
     * @param razonSocia
     */
    public Empresa(String cif, String razonSocial) {
        this.cif = cif;
        this.razonSocial = razonSocial;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el identificador único de la empresa con el se puede
     * asociar a la empresa que gestiona a esta a través de la entidad.
     * <code>GestoresEmpresas</code>.
     * @return Devuelve el identificador único de la empresa.
     * @see com.openlopd.entities.seguridad.GestoresEmpresas
     */
    public String getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Establece el identificador único de la empresa con el se puede
     * asociar a la empresa que gestiona a esta a través de la entidad
     * <code>GestoresEmpresas</code>.
     * @see com.openlopd.entities.seguridad.GestoresEmpresas
     * @param idEmpresa Identificador de la empresa a asociar a la entidad.
     */
    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * Obtiene la actividad a la que se dedica la empresa.
     * @return Devuelve la actividad a la que se dedica la empresa.
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * Establece la actividad a la que se dedica la empresa.
     * @param Actividad Actividad a la que se dedica la empresa.
     */
    public void setActividad(String actividad) {
        if (this.actividad == null || !this.actividad.equals(actividad)) {
            this.actividad = actividad;
            this.changeNombreActividad = true;
        }
    }

    /**
     * Obtiene el CIF de la empresa, será el NIF en caso de tratarse de un autonomo.
     * @return CIF/NIF de la empresa/autonomo.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Establece el CIF de la empresa, será el NIF en caso de tratarse de un autonomo.
     * @param cif CIF/NIF de la empresa/autonomo.
     */
    public void setCif(String cif) {
        if (this.cif == null || !this.cif.equals(cif)) {
            this.cif = cif;
            this.changeNombreActividad = true;
        }
    }

//    /**
//     * Obtiene el código de área relacionado con el teléfono.
//     * @return Código de área telefónico.
//     */
//    public String getCodArea() {
//        return codArea;
//    }
//
//    /**
//     * Devuelve el código de área relacionado con el teléfono.
//     * @param codArea Código de área telefónico.
//     */
//    public void setCodArea(String codArea) {
//        this.codArea = codArea;
//    }

    /**
     * Obtiene el mail de contacto de la empresa.
     * @return Mail de contacto de la empresa.
     */
    public String getMailContacto() {
        return mailContacto;
    }

    /**
     * Persona de contacto en la empresa.
     * @return id de la persona de contacto de la empresa.
     */
    public String getPerContacto() {
        return perContacto;
    }

    /**
     * Persona de contacto en la empresa.
     * @param perContacto id de la persona de contacto de la empresa.
     */
    public void setPerContacto(String perContacto) {
        if(this.perContacto == null || !this.perContacto.equals(perContacto)){
            this.perContacto = perContacto;
            this.changePercontacto = true;
        }        
    }
    
    /**
     * Establece el mail de contacto de la empresa.
     * @param mailContacto Mail de contacto de la empresa.
     */
    public void setMailContacto(String mailContacto) {
        if (this.mailContacto == null || !this.mailContacto.equals(mailContacto)) {
            this.mailContacto = mailContacto;
            this.changeNombreActividad = true;
        }
    }    
    
    /**
     * Obtiene el nombre por el que se conoce a la empresa, para la identificación
     * real utilizar <code>getRazonSocial()</code>.
     * @return Nombre informal asignado a la empresa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el nombre por el que se conoce a la empresa, para la identificación
     * real utilizar <code>getRazonSocial()</code>.
     * @param nombre
     */
    public void setNombre(String nombre) {
        if (this.nombre == null || !this.nombre.equals(nombre)) {
            this.nombre = nombre;
            this.changeNombreActividad = true;
        }
    }

    /**
     * Obtiene el nombre de la persona con la que se debe contactar para cualquier
     * gestión relacionada con la empresa.
     * @return Persona de contacto de la empresa.
     */
//    public String getPerContacto() {
//        return perContacto;
//    }

    /**
     * Establece el nombre de la persona con la que se debe contactar para
     * cualquier gestión relacionada con la empresa.
     * @param perContacto persona de contacto de la empresa.
     */
//    public void setPerContacto(String perContacto) {
//        this.perContacto = perContacto;
//    }

    /**
     * Obtiene la razón social de la empresa.
     * @return razón social de la empresa.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Establece la razón social de la empresa.
     * @param razonSocial razón social de la empresa.
     */
    public void setRazonSocial(String razonSocial) {
        if (this.razonSocial == null || !this.razonSocial.equals(razonSocial)) {
            this.razonSocial = razonSocial;
            this.changeNombreActividad = true;
        }
    }
    
    public List<EmpresaSede> getSedes() {
        return sedes;
    }

    public void setSedes(List<EmpresaSede> sedes) {
        this.sedes = sedes;
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
    
    /**
     * Indica si la persona de contacto ha cambiado.
     * 
     * Se está utilizando este campo como responsable de seguridad.
     * 
     * @return true si ha cambiado, false en caso contrario.
     */
    public boolean isChangePercontacto() {
        return changePercontacto;
    }

    /**
     * Indica si han cambiado otros datos de la empresa.
     * 
     * Monitoriza cambios en el nombre, razón social, actividad y e-mail.
     * 
     * @return true se hay cambios false en caso contrario.
     */
    public boolean isChangeNombreActividad() {
        return changeNombreActividad;
    }
    
    /**
     * Busca la sede que realiza las gestiones de la LOPD.
     * @return Sede que gestiona la LOPD, null en caso de no encontrarla.
     */
    public EmpresaSede getSedeLopd() {
        for (EmpresaSede s: this.sedes) {
            if (s.isGestionaContratLOPD()) {
                return s;
            }
        }
        return null;        
    }


    // <editor-fold defaultstate="expanded" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) ||
                (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empresa{" + "idEmpresa=" + idEmpresa + ", cif=" + cif + ", razonSocial=" 
                + razonSocial + ", nombre=" + nombre + ", actividad=" + actividad 
                + ", perContacto=" + perContacto + ", mailContacto=" + mailContacto 
                + ", sedes=" + sedes + ", borrado=" + borrado + ", borradoPor=" 
                + borradoPor + ", active=" + active + ", changePercontacto=" 
                + changePercontacto + ", changeNombreActividad=" + changeNombreActividad + '}';
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
            js.put("DT_RowId", idEmpresa);
//            js.put("DT_RowClass", (showObsInDocs ? "gradeA" : "gradeB")); //TODO
            js.put("0", cif);
            js.put("1", razonSocial);
            js.put("2", (actividad != null ? actividad : ""));
            js.put("3", perContacto);
            js.put("4", (mailContacto != null ? mailContacto : ""));
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
            js.put("5", "<a class=\"table-action-EditData\">Edit</a>");
            js.put("6", "<a href=\"/Details/17\">Details</a>"); //TODO: Poner el enlace correcto.
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
