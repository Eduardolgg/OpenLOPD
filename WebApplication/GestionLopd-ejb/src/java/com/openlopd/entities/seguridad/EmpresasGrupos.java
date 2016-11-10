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
import javax.persistence.*;

/**
 * Esta entidad es la encargada de identificar cada uno de los grupos que ha
 * creado una empresa, es decir el listado de grupos de cada una de las distintas
 * empresas, de esta forma se consigue que cada empresa pueda crear los grupos
 * que les sean necesarios o la cantidad que tenga por contrato.
 * @author Eduardo L. García Glez.
 * Fecha: 16 de ene de 2011
 * @version 1.0.1
 * Modificaciones:
 *    24 ene 2011: Se modifica clave primaria para adaptarse al estandard,
 *                 y se añaden Querys
 */
@Entity
@Table (name = "empresas_grupos")
@NamedQueries({
    @NamedQuery(name = "EmpresasGrupos.findAll", query = "SELECT m FROM EmpresasGrupos m"),
    @NamedQuery(name = "EmpresasGrupos.findByidEmpresa", query = "SELECT m FROM EmpresasGrupos m WHERE m.empresasGruposPK.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "EmpresasGrupos.findByIdGrupo", query = "SELECT m FROM EmpresasGrupos m WHERE m.empresasGruposPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "EmpresasGrupos.findByNombre", query = ""
        + "SELECT m FROM EmpresasGrupos m "
        + "WHERE m.nombre = :nombre "
        + "  AND m.empresasGruposPK.idEmpresa = :idEmpresa "),
    @NamedQuery(name = "EmpresasGrupos.findByIdUsuario", query = ""
        + "SELECT g FROM EmpresasGrupos g, GruposUsuarios gu "
        + "WHERE g.empresasGruposPK.idGrupo = gu.gruposUsuariosPK.idGrupo "
        + "  AND g.empresasGruposPK.idEmpresa = :idEmpresa "
        + "  AND gu.gruposUsuariosPK.idUsuario = :idUsuario ")
})
public class EmpresasGrupos implements Serializable {
    private static final long serialVersionUID = 1L;
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @EmbeddedId
    protected EmpresasGruposPK empresasGruposPK;
    @Column (length = 25, nullable = false)
    private String nombre;
    @Column (length = 100, nullable = false)
    private String descripcion;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public EmpresasGrupos() {
        this.empresasGruposPK = new EmpresasGruposPK();
    }

    /**
     * Permite inicializar todos los parámetros.
     * @param idEmpresa idEmpresa de la empresa/autonomo al que pertenece el grupo.
     * @param idGrupo Identificador único del grupo.
     * @param nombre Nombre para identificar al grupo.
     * @param descripcion Texto descriptivo sobre el grupo.
     */
    public EmpresasGrupos(String idEmpresa, String idGrupo, String nombre, String descripcion) {
        this.empresasGruposPK = new EmpresasGruposPK(idEmpresa, idGrupo);
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Permite inicializar todos los parámetros.
     * @param empresasGruposPK Clave primaria de la entidad.
     * @param nombre Nombre para identificar al grupo.
     * @param descripcion Texto descriptivo sobre el grupo.
     */
    public EmpresasGrupos(EmpresasGruposPK empresasGruposPK, String nombre, String descripcion) {
        this.empresasGruposPK = empresasGruposPK;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction GetSet">
    /**
     * Obtiene el CIF/NIF de la empresa/autonomo.
     * En relación al grupo que se encuentra en la propiedad <b>idGrupo</b>.
     * @return cif/nif de la empresa/autonomo.
     */
    public String getIdEmpresa() {
        return this.empresasGruposPK.getIdEmpresa();
    }

    /**
     * Establece el CIF/NIF de la empresa/autonomo.
     * En relación al grupo que se encuentra en la propiedad <b>idGrupo</b>.
     * @param cif CIF/NIF de la empresa/autonomo.
     */
    public void setIdEmpresa(String idEmpresa) {
        this.empresasGruposPK.setIdEmpresa(idEmpresa);
    }

    /**
     * Obtiene el conjunto de valores de la clave primaria.
     * @return Clave primaria de la entidad.
     */
    public EmpresasGruposPK getEmpresasGruposPK() {
        return empresasGruposPK;
    }

    /**
     * Establece la clave primaria de la entidad.
     *
     * Establece el conjunto de valores que forman la clave primaria de esta
     * entidad.
     * @param empresasGruposPK Clave primaria.
     */
    public void setEmpresasGruposPK(EmpresasGruposPK empresasGruposPK) {
        this.empresasGruposPK = empresasGruposPK;
    }

    /**
     * Establece el nombre identificativo del grupo.
     *
     * Para una descripción completa para saber a qué está orientado el grupo
     * es necesario ir a la propiedad <code>descripción</code>
     * @return Nombre identificativo del grupo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre identificativo del grupo.
     *
     * Para una descripción completa para saber a qué está orientado el grupo
     * es necesario ir a la propiedad <code>descripción</code>
     * @param nombre Nombre identificativo del grupo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del grupo.
     * @return <b>String</b> con la descripción del grupo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripición del grupo.
     * @param descripcion Descripción del grupo a almacenar.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el identificador único del grupo.
     * @return Identificador único del grupo.
     */
    public String getIdGrupo() {
        return this.empresasGruposPK.getidGrupo();
    }

    /**
     * Establece el identificador único del grupo.
     * @param idGrupo Identificador único del grupo.
     */
    public void setIdGrupo(String idGrupo) {
        this.empresasGruposPK.setidGrupo(idGrupo);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresasGruposPK != null ? empresasGruposPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresasGrupos)) {
            return false;
        }
        EmpresasGrupos other = (EmpresasGrupos) object;
        if ((this.empresasGruposPK == null && other.empresasGruposPK != null) ||
                (this.empresasGruposPK != null && !this.empresasGruposPK.equals(other.empresasGruposPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.EmpresasGrupos[id=" + empresasGruposPK + "]";
    }
    // </editor-fold>
}
