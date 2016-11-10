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

package com.openlopd.entities.seguridad.base;

import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Esta clase contendrá toda la descripción de permisos que es común a varias clases.
 *
 * IMPORTANTE: No se deben añadir nuevos permisos en esta clase, añadir estos
 * nuevos permisos al Interface y luego los métodos y propiedades correspondientes
 * en esta clase.
 * 
 * @see com.openlopd.entities.seguridad.ContratosPermisos
 * @see com.openlopd.entities.historicos.seguridad.HistContratosPermisos
 * @see com.openlopd.entities.seguridad.ContratosTipo
 * @see com.openlopd.entities.seguridad.ContratosTipoGestor
 * @author Eduardo L. García Glez.
 * @version 1.0.0
 */
@MappedSuperclass
public class BaseContratosPermisos extends AbstractPermisos {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Column (nullable = false)
    private Integer publico;
    @Column (nullable = false)
    private Integer usuarios;
    @Column (nullable = false)
    private Integer ficheros;
    @Column (nullable = false)
    private Integer incidencias;
    @Column (name = "gestion_personal", nullable = false)
    private Integer gestionPersonal;
    @Column (name = "gestion_empresas", nullable = false)
    private Integer gestionEmpresas;
    @Column (nullable = false)
    private Integer plantillas;
    @Column (name = "modos_destruccion", nullable = false)
    private Integer modosDestruccion;
    @Column (nullable = false)
    private Integer facturas;
    @Column (nullable = false)
    private Integer destinatarios;
    @Column (name = "documento_seguridad", nullable = false)
    private Integer documentoSeguridad;
    @Column (name = "registro_entrada", nullable = false)
    private Integer registroEntrada;
    @Column (name = "registro_salida", nullable = false)
    private Integer registroSalida;
    @Column (name = "inventario_soportes", nullable = false)
    private Integer inventarioSoportes;
    @Column (name = "tipos_de_soporte", nullable = false)
    private Integer tiposDeSoporte;
    @Column (name = "sisadmin", nullable = false)
    private Integer sysAdmin;
    @Column (name = "app_admin", nullable = false)
    private Integer appAdmin;
    @Column (name = "empresa_y_sedes", nullable = false)
    private Integer empresaYSedes;
    @Column (name = "documentos_generales", nullable = false)
    private Integer documentosGenerales;
    @Column (name = "registro_accesos", nullable = false)
    private Integer registroAccesos;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public BaseContratosPermisos() {

    }

    /**
     * Constructor inicializando los permisos de la Clase.
     * @param permisos
     * @exception UnknownColumnException Se emite si alguna de las columnas de
     * permisos no puede ser importada.
     */
    public BaseContratosPermisos(BaseContratosPermisos permisos) throws UnknownColumnException {
        this.importaPermisos(permisos);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GestSet">
    // TODO: Definir las propiedades que contendrán los permisos.
    @Override
    public Object getPublico() {
        return this.publico;
    }

    /**
     * // TODO: no se sabe por el momento si este método continua en el sistema
     * hasta que no se establezcan los permisos.
     * @param publico
     * @exception java.lang.ClassCastException Se emite esta excepción cuando
     * el objeto recibido no es de la clase adecuada.
     * @exception java.lang.NullPointerException Se produce esta excepción si se
     * intenta inicializar el objeto a un valor nulo.
     */
    @Override
    public void setPublico(Object publico) {
        if (!(publico instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setUsuarios[Recibido: " 
                    + publico.toString() + "]");
        }
        this.publico = (Integer) publico;
    }

    @Override
    public Object getUsuarios() {
        return this.usuarios;
    }

    /**
     * // TODO: no se sabe por el momento si este método continua en el sistema
     * hasta que no se establezcan los permisos.
     * @param usuarios
     * @exception java.lang.ClassCastException Se emite esta excepción cuando
     * el objeto recibido no es de la clase adecuada.
     * @exception java.lang.NullPointerException Se produce esta excepción si se
     * intenta inicializar el objeto a un valor nulo.
     */
    @Override
    public void setUsuarios(Object usuarios) {
        if (!(usuarios instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setUsuarios[Recibido: " 
                    + usuarios.toString() + "]");
        }
        this.usuarios = (Integer) usuarios;
    }

    @Override
    public Object getFicheros() {
        return this.ficheros;
    }

    @Override
    public void setFicheros(Object ficheros) {
        if (!(ficheros instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setFicheros[Recibido: " 
                    + ficheros.toString() + "]");
        }
        this.ficheros = (Integer) ficheros;
    }
    
    @Override
    public Object getIncidencias() {
        return this.incidencias;
    }

    @Override
    public void setIncidencias(Object incidencias) {
        if (!(incidencias instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setIncidencias[Recibido: " 
                    + incidencias.toString() + "]");
        }
        this.incidencias = (Integer) incidencias;
    }
    
    @Override
    public Object getGestionPersonal() {
        return this.gestionPersonal;
    }

    @Override
    public void setGestionPersonal(Object gestionPersonal) {
        if (!(gestionPersonal instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setGestionPersonal[Recibido: " 
                    + gestionPersonal.toString() + "]");
        }
        this.gestionPersonal = (Integer) gestionPersonal;
    }
    
    @Override
    public Object getGestionEmpresas() {
        return this.gestionEmpresas;
    }

    @Override
    public void setGestionEmpresas(Object gestionEmpresas) {
        if (!(gestionEmpresas instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setGestionEmpresas[Recibido: " 
                    + gestionEmpresas.toString() + "]");
        }
        this.gestionEmpresas = (Integer) gestionEmpresas;
    }
    
    @Override
    public Object getPlantillas() {
        return this.plantillas;
    }

    @Override
    public void setPlantillas(Object plantillas) {
        if (!(plantillas instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setPlantillas[Recibido: " 
                    + plantillas.toString() + "]");
        }
        this.plantillas = (Integer) plantillas;
    }
    
    @Override
    public Object getModosDestruccion() {
        return this.modosDestruccion;
    }

    @Override
    public void setModosDestruccion(Object modosDestruccion) {
        if (!(modosDestruccion instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setModosDestruccion[Recibido: " 
                    + modosDestruccion.toString() + "]");
        }
        this.modosDestruccion = (Integer) modosDestruccion;
    }
    
    @Override
    public Object getFacturas() {
        return this.facturas;
    }

    @Override
    public void setFacturas(Object facturas) {
        if (!(facturas instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setFacturas[Recibido: " 
                    + facturas.toString() + "]");
        }
        this.facturas = (Integer) facturas;
    }
    
    @Override
    public Object getDestinatarios() {
        return this.destinatarios;
    }

    @Override
    public void setDestinatarios(Object destinatarios) {
        if (!(destinatarios instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setDestinatarios[Recibido: " 
                    + destinatarios.toString() + "]");
        }
        this.destinatarios = (Integer) destinatarios;
    }
    
    @Override
    public Object getDocumentoSeguridad() {
        return this.documentoSeguridad;
    }

    @Override
    public void setDocumentoSeguridad(Object documentoSeguridad) {
        if (!(documentoSeguridad instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setDocumentoSeguridad[Recibido: " 
                    + documentoSeguridad.toString() + "]");
        }
        this.documentoSeguridad = (Integer) documentoSeguridad;
    }
    
    @Override
    public Object getRegistroEntrada() {
        return this.registroEntrada;
    }

    @Override
    public void setRegistroEntrada(Object registroEntrada) {
        if (!(registroEntrada instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setRegistroEntrada[Recibido: " 
                    + registroEntrada.toString() + "]");
        }
        this.registroEntrada = (Integer) registroEntrada;
    }
    
    @Override
    public Object getRegistroSalida() {
        return this.registroSalida;
    }

    @Override
    public void setRegistroSalida(Object registroSalida) {
        if (!(registroSalida instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setRegistroSalida[Recibido: " 
                    + registroSalida.toString() + "]");
        }
        this.registroSalida = (Integer) registroSalida;
    }
    
    @Override
    public Object getInventarioSoportes() {
        return this.inventarioSoportes;
    }

    @Override
    public void setInventarioSoportes(Object inventarioSoportes) {
        if (!(inventarioSoportes instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setInventarioSoportes[Recibido: " 
                    + inventarioSoportes.toString() + "]");
        }
        this.inventarioSoportes = (Integer) inventarioSoportes;
    }
    
    @Override
    public Object getTiposDeSoporte() {
        return this.tiposDeSoporte;
    }

    @Override
    public void setTiposDeSoporte(Object tiposDeSoporte) {
        if (!(tiposDeSoporte instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setTiposDeSoporte[Recibido: " 
                    + tiposDeSoporte.toString() + "]");
        }
        this.tiposDeSoporte = (Integer) tiposDeSoporte;
    }
    
    @Override
    public Object getSysAdmin() {
        return this.sysAdmin;
    }

    @Override
    public void setSysAdmin(Object sysAdmin) {
        if (!(sysAdmin instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setSysAdmin[Recibido: " 
                    + sysAdmin.toString() + "]");
        }
        this.sysAdmin = (Integer) sysAdmin;
    }
    
    @Override
    public Object getAppAdmin() {
        return this.appAdmin;
    }

    @Override
    public void setAppAdmin(Object appAdmin) {
        if (!(appAdmin instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setAppAdmin[Recibido: " 
                    + appAdmin.toString() + "]");
        }
        this.appAdmin = (Integer) appAdmin;
    }
    
    @Override
    public Object getEmpresaYSedes() {
        return this.empresaYSedes;
    }

    @Override
    public void setEmpresaYSedes(Object empresaYSedes) {
        if (!(empresaYSedes instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setEmpresaYSedes[Recibido: " 
                    + empresaYSedes.toString() + "]");
        }
        this.empresaYSedes = (Integer) empresaYSedes;
    }
    
    @Override
    public Object getDocumentosGenerales() {
        return this.documentosGenerales;
    }

    @Override
    public void setDocumentosGenerales(Object documentosGenerales) {
        if (!(documentosGenerales instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setDocumentosGenerales[Recibido: " 
                    + documentosGenerales.toString() + "]");
        }
        this.documentosGenerales = (Integer) documentosGenerales;
    }
    
    @Override
    public Object getRegistroAccesos() {
        return this.registroAccesos;
    }

    @Override
    public void setRegistroAccesos(Object registroAccesos) {
        if (!(registroAccesos instanceof Integer)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BaseContratosPermisos.setDocumentosGenerales[Recibido: " 
                    + registroAccesos.toString() + "]");
        }
        this.registroAccesos = (Integer) registroAccesos;
    }
    // </editor-fold>
    
        /**
     * Permite comprobar permisos.
     * @param permiso Identificador del permiso del que se quiere obtener
     * información.
     * @param modo Modo de acceso. (Lectura, escritura etc.)
     * @return true si se tiene permiso de acceso, false en caso contrario.
     * @throws UnknownColumnException si no se encuentra el identificador del
     * permiso.
     */
    public Boolean hasAccess(ColumnasPermisos permiso) throws UnknownColumnException {
        return (Integer) this.getByName(permiso) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Extra">
    /**
     * Cópia de permisos.
     *
     * Permite hacer la copia en el objeto de los permisos de otro objeto
     * de las mismas carácterísticas.
     * @param permisos Permisos a los que realizar la copia.
     * @exception java.lang.ClassCastException Se produce esta excepción si el
     * objeto enviado no es de la clase esperada.
     * @exception java.lang.NullPointerException Se produce esta excepción si se
     * intentan importar permisos de un objeto nulo.
     */
    @Override
    public final void importaPermisos(Object permisos){
        if (!(permisos instanceof BaseContratosPermisos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BaseContratosPermisos.importaPermisos[Recibido: " + permisos.toString() + "]");
        }
        super.importaPermisos(permisos);
    }

    /**
     * Permite Comparar los permisos de dos objetos.
     * @param permisos Objeto que contiene los permisos a comparar con el actual.
     * @return <code>true</code> en caso se ser iguales <code>false<code> en caso contrario.
     * @exception java.lang.NullPointerException Se produce esta excepción si se
     * intentan importar permisos de un objeto nulo.
     * @exception UnknownColumnException Se produce cuando alguna de las columnas
     * de permisos no puede ser comparada.
     */
    @Override
    public boolean compararPermisos(Object permisos) throws UnknownColumnException {
        if (!(permisos instanceof BaseContratosPermisos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BaseContratosPermisos.compararPermisos[Recibido: " + permisos.toString() + "]");
        }
        return super.compararPermisos(permisos);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BaseContratosPermisos)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.base.BaseContratosPermisos[Permisos=" + super.toString() + "]";
    }
    // </editor-fold>

}
