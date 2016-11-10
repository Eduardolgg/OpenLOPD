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

package com.openlopd.entities.seguridad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entidad que se encarga de gestionar los usuarios y passwords para el acceso
 * a la aplicación.
 * @author Eduardo L. García Glez.
 * Fecha: 9 de ene de 2011
 * @version 1.0.3
 * Modificaciones:
 *   9 de ene de 2011 se añaden get/set que faltaban.
 *   9 de ene de 2011 se añaden límites en los strings.
 *   9 de ene de 2011 se modifican los comentarios para que salgen en
 *   javadoc.
 *   06 de ene de 2011 Nueva columna para el cif de la empresa del usuario, de esta forma
 *   se tiene n acceso rápido a los permisos propios de la empresa.
 */
@Entity
@Table(name = "shadow")
@NamedQueries({
    @NamedQuery(name = "Shadow.findAll", query = "SELECT s FROM Shadow s"),
    @NamedQuery(name = "Shadow.findByUsuario", query = "SELECT s FROM Shadow s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "Shadow.findByClave", query = "SELECT s FROM Shadow s WHERE s.clave = :clave"),
    @NamedQuery(name = "Shadow.findByCif", query = "SELECT s FROM Shadow s WHERE s.cif = :cif"),
    @NamedQuery(name = "Shadow.findByRazonSocial", query = "SELECT s FROM Shadow s WHERE s.razonSocial = :razonSocial"),
    @NamedQuery(name = "Shadow.findByFechaFin", query = "SELECT s FROM Shadow s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "Shadow.findByGestor", query = "SELECT s FROM Shadow s WHERE s.gestor = :gestor"),
    @NamedQuery(name = "Shadow.findOnlineUsers", query = ""
        + "SELECT s FROM Shadow s "
        + "WHERE s.ultimoAcceso >= :lastAccessDate "
        + "ORDER BY s.idEmpresa ASC"),
    @NamedQuery(name = "Shadow.findLastOnlineUsers", query =""
        + "SELECT s FROM Shadow s "
        + "WHERE s.ultimoAcceso < :lastAccessDate "
        + "  AND s.ultimoAcceso IS NOT NULL "
        + "ORDER BY s.ultimoAcceso DESC")
})
public class Shadow implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario", nullable = false, length = 255)
    private String usuario;
    @Basic(optional = false)
    @Column(name = "clave", nullable = false, length = 32)
    private String clave;
    @Column(name = "tipo_cifrado", nullable = false, length = 10)
    private String tipoCifrado;
    @Column(name = "cif", nullable = false, length = 9)
    private String cif;
    @Column(name = "id_empresa", nullable = false, length = 37)
    private String idEmpresa;
    @Basic(optional = false)
    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;
    @Column(name = "fecha_fin", nullable = false)
    private Timestamp fechaFin;
    @Basic(optional = false)
    @Column(name = "gestor", nullable = false)
    private boolean gestor;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne
    private TiposUsuario tipo;
    @Column(name = "ultimo_acceso", nullable = true, columnDefinition = " default null")
    private Long ultimoAcceso = null;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructo por defecto.
     */
    public Shadow() {
        this.gestor = false;
    }

    /**
     * Permite asignar la clave primaria a la entida.
     * @param usuario Identificador del usuario.
     */
    public Shadow(String usuario) {
        this.usuario = usuario;
        this.gestor = false;
    }

    /**
     * Permite crear el objeto asignando el usuario y la clave.
     * @param usuario Identificador del usuario.
     * @param clave Clave del usuario.
     */
    public Shadow(String usuario, String clave, String tipoCifrado) {
        this.usuario = usuario;
        this.clave = clave;
        this.tipoCifrado = tipoCifrado;
        this.gestor = false;
    }

    /**
     * Permite inicializar la entidad y todas sus propiedades
     * @param usuario Identificador del usuario.
     * @param clave Clave del usuario.
     * @param cif CIF de la empresa a la que pertenece el usuario.
     * @param razonSocial Razón social de la empresa a la que pertenece el usuario.
     * @param fechaFin Fecha fin de los privilegios de entrada.
     * @param gestor Indica si puede gestionar subempresas.
     */
    public Shadow(String usuario, String clave, String tipoCifrado, String cif,
            String idEmpresa, String razonSocial, Timestamp fechaFin, boolean gestor,
            TiposUsuario tipo) {
        this.usuario = usuario;
        this.clave = clave;
        this.tipoCifrado = tipoCifrado;
        this.cif = cif;
        this.idEmpresa = idEmpresa;
        this.razonSocial = razonSocial;
        this.fechaFin = fechaFin;
        this.gestor = gestor;
        this.tipo = tipo;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Establece el Id de usuario, <b>Importante ver descripción.</b>
     *
     * Aunque se utiliza usuario como nombre de la columna, no se refiere a un
     * nombre de usuario como normalmente se conoce sino que el nombre de
     * usuario o el identificador utilizado dependerá del quien accede ya sea
     * persona física o juridica por tanto para empresas y autonomos el nombre
     * de usuario será el CIF o DNI según corresponda y para otro tipo de
     * usuarios un identificador único.
     * 
     * @return Identificador único del usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el id del usuario, <b>Importante ver descripción.</b>
     *
     * Aunque se utiliza usuario como nombre de la columna, no se refiere a un
     * nombre de usuario como normalmente se conoce sino que el nombre de
     * usuario o el identificador utilizado dependerá del quien accede ya sea
     * persona física o juridica por tanto para empresas y autonomos el nombre
     * de usuario será el CIF o DNI según corresponda y para otro tipo de
     * usuarios un identificador único.
     * @param usuario Identificador único del usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la clave encriptada del usuario.
     * 
     * Es necesario ver la propiedad <code>tipoCifrado</code> para obtener el 
     * algoritmo de cifrado utilizado en la encriptación.
     * @return Clave encriptada del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece la clave encriptada del usuario.
     *
     * Es necesario establecer la propiedad <code>tipoCifrado</code> para obtener el
     * algoritmo de cifrado utilizado en la encriptación.
     * @param clave Clave encriptada del usuario.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Establece el tipo de cifrado de la clave.
     *
     * Esta propiedad se usa conjuntamente con la propiedad <code>setClave</code>
     * para poder encriptar/desencriptar el password del usuario.
     * @return Algoritmo de cifrado utilizado para encriptar la clave del usuario.
     */
    public String getTipoCifrado() {
        return tipoCifrado;
    }

    /**
     * Establece el tipo de cifrado de la clave.
     *
     * Esta propiedad se usa conjuntamente con la propiedad <code>setClave</code>
     * para poder encriptar/desencriptar el password del usuario.
     * @param tipoCifrado Tipo de cifrado utilizado para encriptar desencriptar
     * la clave del usuario.
     */
    //TODO: en el paquete de cifrado establecer las constantes que indique el tipo de cifrado permitido.
    public void setTipoCifrado(String tipoCifrado) {
        this.tipoCifrado = tipoCifrado;
    }

    /**
     * Obtiene el CIF/NIF de la empresa para el que trabaja el usuario.
     * @return CIF/NIF de la empresa para la que trabaja el usuario.
     */
    public String getCif(){
        return this.cif;
    }

    /**
     * Establece el CIF/NIF de la empresa para el que trabaja el usuario.
     * @param cif CIF/NIF de la empresa para la que trabaja el usuario.
     */
    public void setCif(String cif){
        this.cif = cif;
    }

    /**
     * Obtiene el identificador único de la empresa.
     * 
     * Es necesario un identificador único ya que en la tabla
     * empresas si pueden repetirse los cif, así que este idEmpresa
     * coincide con el idEmpresa de la entidad Empresa.
     * 
     * @return Identificador único de la empresa.
     */
    public String getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Establece el identificador único de la empresa.
     * 
     * Es necesario un identificador único ya que en la tabla
     * empresas si pueden repetirse los cif, así que este idEmpresa
     * coincide con el idEmpresa de la entidad Empresa.
     * 
     * @param idEmpresa Identificador único de la empresa.
     */
    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    /**
     * Obtiene la razón social de la empresa
     *
     * En caso de ser un autonomo su nombre
     * y en caso de ser un usuario creado por una empresa correspondería
     * el uso del nombre de su empresa.
     * <b>Ojo, hay redundancia con esta propiedad en otras tablas</b>
     * @return Razón social correspondiente según sea empresa/autonomo/usuario.
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Establece la razón social de la empresa.
     * 
     * En caso de ser un autonomo su nombre
     * y en caso de ser un usuario creado por una empresa correspondería
     * el uso del nombre de su empresa.
     * <b>Ojo, hay redundancia con esta propiedad en otras tablas</b>
     * @param razonSocial Razón social o nombre a almacenar.
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    /**
     * Obtiene la fecha fin del contrato/acceso.
     *
     * A través de esta fecha se obtiene cuando
     * debe eliminarse el acceso al usuario.
     * @return Fecha de caducidad de la cuenta del usuario. //TODO: ¿Es necesario indicar el formato u zona horaria?
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha fin del contrato/acceso.
     *
     * A través de esta fecha se obtiene cuando debe eliminarse el acceso al usuario.
     * @param fechaFin Fecha de caducidad de la cuenta del usuario. //TODO:¿Es necesario indicar el formato u zona horaria?
     */
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    /**
     * Establece si la empresa/autonomo está habilitado gestor o no.
     *
     * Una empresa en caso de ser "Gestora" tiene la habilidad de crear empresas
     * asociadas a su cuenta a las cuales les puede hacer las gestiones permitidas
     * por la aplicación o las que se les hayan asignado.
     *
     * La propiedad se establece a <code>true</code> si se considara gestor o
     * <code>false</code> en caso contrario.
     *
     * En caso de tratarse de un usuario de una empresa esta propiedad se
     * establece a <code>false</code>.
     *
     * Nota: por defecto esta propiedad es <code>false</code>.
     * @return <code>true</code> si se trata de un gestor <code>false</code> en
     * caso contrario.
     * @see com.openlopd.entities.seguridad.TiposUsuario
     */
    public boolean getGestor() {
        return gestor;
    }

    /**
     * Obtiene si la empresa/autonomo está habilitado gestor o no.
     *
     * Una empresa en caso de ser "Gestora" tiene la habilidad de crear empresas
     * asociadas a su cuenta a las cuales les puede hacer las gestiones permitidas
     * por la aplicación o las que se les hayan asignado.
     *
     * La propiedad se establece a <code>true</code> si se considara gestor o
     * <code>false</code> en caso contrario.
     *
     * En caso de tratarse de un usuario de una empresa esta propiedad se
     * establece a <code>false</code>.
     *
     * Nota: por defecto esta propiedad es <code>false</code>.
     * @param gestor <code>true</code> si se trata de un gestor <code>false</code> en
     * caso contrario.
     * @see com.openlopd.entities.seguridad.TiposUsuario
     */
    public void setGestor(boolean gestor) {
        this.gestor = gestor;
    }

    /**
     * El tipo es el tipo de usuario del que se trata el usuario en concreto
     * cargado en el objeto.
     * @see com.openlopd.entities.seguridad.TiposUsuario
     */
    public TiposUsuario getTipo() {
        return tipo;
    }

    /**
     * El tipo es el tipo de usuario del que se trata el usuario en concreto
     * cargado en el objeto.
     * @see com.openlopd.entities.seguridad.TiposUsuario
     */
    public void setTipo(TiposUsuario tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la fecha y hora del último acceso del usuario al sistema.
     * @return fecha y hora en formato long.
     */
    public Long getUltimoAcceso() {
        return ultimoAcceso;
    }

    /**
     * Establece la fecha y hora del último acceso del usuario al sistema.
     * @param ultimoAcceso fecha y hora en formato long.
     */
    public void setUltimoAcceso(Long ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shadow)) {
            return false;
        }
        Shadow other = (Shadow) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.Shadow[usuario=" + usuario + "]";
    }
    // </editor-fold>
}
