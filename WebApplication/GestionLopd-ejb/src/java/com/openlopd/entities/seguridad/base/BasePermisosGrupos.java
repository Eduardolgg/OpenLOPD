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
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase contendrá toda la descripción de permisos que es común a varias clases.
 *
 * IMPORTANTE: No se deben añadir nuevos permisos en esta clase, añadir estos
 * nuevos permisos al Interface y luego los métodos y propiedades correspondientes
 * en esta clase.
 * @author Eduardo L. García Glez.
 * @version 1.0.2 21 de mar de 2011
 * Modificaciones:
 *    29 de enero de 20011 Se establecen las propiedades/permisos como Byte para
 *    poder admitir mayor rango de permisos, Lectura/Escritura en cada uno de los módulos.
 *    21 de mar de 2011, Añadido el método importaPermisosDesdeContrato.
 */
@MappedSuperclass
public class BasePermisosGrupos extends AbstractPermisos {
    private static Logger logger = LoggerFactory.getLogger(BasePermisosGrupos.class);
    
    // <editor-fold defaultstate="collapsed" desc="Secction Constants">

    /**
     * Permite realizar acciones de lectura y escritura en el módulo en el
     * que está asignado.
     *
     * Se debe establece un permiso de lectura/escritura en aquellos
     * módulos en lo que se pede diferencias que un permiso de escritura
     * no lleva implícito que sea posible acceder a los datos que se encuentran
     * actualmente en el módulo.
     */
    @Transient
    public static final byte RW = 3;
    /**
     * Permite realizar acciones de escritura en el módulo en el que está
     * asignado.
     *
     * Permite establecer un permiso de escritura para un módulo, que se tenga
     * permiso de escritura no quiere decir que necesariamente el grupo
     * pueda leer los datos existentes por lo que si se encesita un acceso completo
     * es necesario la utilización de <code>RW</code> ya que es posible que el módulo
     * no permita leer pero si escribir con este permiso.
     */
    @Transient
    public static final byte ESCRITURA = 2;
    /**
     * Permita realizar acciones de lectura en el módulo al que está asignado.
     *
     * Permite el acceso como lectura un módulo, no se podrá modificar ni grabar
     * nuevos datos si solo se tiene este permiso.
     */
    @Transient
    public static final byte LECTURA = 1;

    /**
     * Indica que no se tiene ningún permiso de acceso al sistema.
     */
    @Transient
    public static final byte NO_ACCESS = 0;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Column (nullable = false)
    private Byte publico;
    @Column (nullable = false)
    private Byte usuarios;
    @Column (nullable = false)
    private Byte ficheros;
    @Column (nullable = false)
    private Byte incidencias;
    @Column (name = "gestion_personal", nullable = false)
    private Byte gestionPersonal;
    @Column (name = "gestion_empresas", nullable = false)
    private Byte gestionEmpresas;
    @Column (nullable = false)
    private Byte plantillas;
    @Column (name = "modos_destruccion", nullable = false)
    private Byte modosDestruccion;
    @Column (nullable = false)
    private Byte facturas;
    @Column (nullable = false)
    private Byte destinatarios;
    @Column (name = "documento_seguridad", nullable = false)
    private Byte documentoSeguridad;
    @Column (name = "registro_entrada", nullable = false)
    private Byte registroEntrada;
    @Column (name = "registro_salida", nullable = false)
    private Byte registroSalida;
    @Column (name = "inventario_soportes", nullable = false)
    private Byte inventarioSoportes;
    @Column (name = "tipos_de_soporte", nullable = false)
    private Byte tiposDeSoporte;
    @Column (name = "sisadmin", nullable = false)
    private Byte sysAdmin;
    @Column (name = "app_admin", nullable = false)
    private Byte appAdmin;
    @Column (name = "empresa_y_sedes", nullable = false)
    private Byte empresaYSedes;
    @Column (name = "documentos_generales", nullable = false)
    private Byte documentosGenerales;
    @Column (name = "registro_accesos", nullable = false)
    private Byte registroAccesos;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public BasePermisosGrupos() {

    }

    /**
     * Constructor inicializando los permisos de la Clase.
     * @param permisos Permisos con los que inicializar el objeto.
     * @exception UnknownColumnException Se produce cuando alguna de las columnas
     * de permisos no puede ser importada.
     */
    public BasePermisosGrupos(BasePermisosGrupos permisos) throws UnknownColumnException {
        this.importaPermisos(permisos);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GestSet">
    // TODO: Definir las propiedades que contendrán los permisos.
    @Override
    public Object getPublico() {
        return this.publico;
    }

    @Override
    public void setPublico(Object publico) {
        if (!(publico instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setPublico[Recibido: " 
                    + publico.toString() + "]");
        }
        this.publico = (Byte) publico;
    }

    @Override
    public Object getUsuarios() {
        return this.usuarios;
    }

    @Override
    public void setUsuarios(Object usuarios) {
        if (!(usuarios instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setUsuarios[Recibido: " 
                    + usuarios.toString() + "]");
        }
        this.usuarios = (Byte) usuarios;
    }

    @Override
    public Object getFicheros() {
        return this.ficheros;
    }

    @Override
    public void setFicheros(Object ficheros) {
        if (!(ficheros instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setFicheros[Recibido: " 
                    + ficheros.toString() + "]");
        }
        this.ficheros = (Byte) ficheros;
    }
    
    @Override
    public Object getIncidencias() {
        return this.incidencias;
    }

    @Override
    public void setIncidencias(Object incidencias) {
        if (!(incidencias instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setIncidencias[Recibido: " 
                    + incidencias.toString() + "]");
        }
        this.incidencias = (Byte) incidencias;
    }
    
    @Override
    public Object getGestionPersonal() {
        return this.gestionPersonal;
    }

    @Override
    public void setGestionPersonal(Object gestionPersonal) {
        if (!(gestionPersonal instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setGestionPersonal"
                    + "[Recibido: " + gestionPersonal.toString() + "]");
        }
        this.gestionPersonal = (Byte) gestionPersonal;
    }
    
    @Override
    public Object getGestionEmpresas() {
        return this.gestionEmpresas;
    }

    @Override
    public void setGestionEmpresas(Object gestionEmpresas) {
        if (!(gestionEmpresas instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setGestionEmpresas"
                    + "[Recibido: " + gestionEmpresas.toString() + "]");
        }
        this.gestionEmpresas = (Byte) gestionEmpresas;
    }
    
    @Override
    public Object getPlantillas() {
        return this.plantillas;
    }

    @Override
    public void setPlantillas(Object plantillas) {
        if (!(plantillas instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setPlantillas"
                    + "[Recibido: " + plantillas.toString() + "]");
        }
        this.plantillas = (Byte) plantillas;
    }
    
    @Override
    public Object getModosDestruccion() {
        return this.modosDestruccion;
    }

    @Override
    public void setModosDestruccion(Object modosDestruccion) {
        if (!(modosDestruccion instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setModosDestruccion"
                    + "[Recibido: " + modosDestruccion.toString() + "]");
        }
        this.modosDestruccion = (Byte) modosDestruccion;
    }
    
    @Override
    public Object getFacturas() {
        return this.facturas;
    }

    @Override
    public void setFacturas(Object facturas) {
        if (!(facturas instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setFacturas"
                    + "[Recibido: " + facturas.toString() + "]");
        }
        this.facturas = (Byte) facturas;
    }
    
    @Override
    public Object getDestinatarios() {
        return this.destinatarios;
    }

    @Override
    public void setDestinatarios(Object destinatarios) {
        if (!(destinatarios instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setDestinatarios"
                    + "[Recibido: " + destinatarios.toString() + "]");
        }
        this.destinatarios = (Byte) destinatarios;
    }
    
    @Override
    public Object getDocumentoSeguridad() {
        return this.documentoSeguridad;
    }

    @Override
    public void setDocumentoSeguridad(Object documentoSeguridad) {
        if (!(documentoSeguridad instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setDocumentoSeguridad"
                    + "[Recibido: " + documentoSeguridad.toString() + "]");
        }
        this.documentoSeguridad = (Byte) documentoSeguridad;
    }
    
    @Override
    public Object getRegistroEntrada() {
        return this.registroEntrada;
    }

    @Override
    public void setRegistroEntrada(Object registroEntrada) {
        if (!(registroEntrada instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setRegistroEntrada"
                    + "[Recibido: " + registroEntrada.toString() + "]");
        }
        this.registroEntrada = (Byte) registroEntrada;
    }
    
    @Override
    public Object getRegistroSalida() {
        return this.registroSalida;
    }

    @Override
    public void setRegistroSalida(Object registroSalida) {
        if (!(registroSalida instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setRegistroSalida"
                    + "[Recibido: " + registroSalida.toString() + "]");
        }
        this.registroSalida = (Byte) registroSalida;
    }
    
    @Override
    public Object getInventarioSoportes() {
        return this.inventarioSoportes;
    }

    @Override
    public void setInventarioSoportes(Object inventarioSoportes) {
        if (!(inventarioSoportes instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setInventarioSoportes"
                    + "[Recibido: " + inventarioSoportes.toString() + "]");
        }
        this.inventarioSoportes = (Byte) inventarioSoportes;
    }
    
    @Override
    public Object getTiposDeSoporte() {
        return this.tiposDeSoporte;
    }

    @Override
    public void setTiposDeSoporte(Object tiposDeSoporte) {
        if (!(tiposDeSoporte instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setTiposDeSoporte"
                    + "[Recibido: " + tiposDeSoporte.toString() + "]");
        }
        this.tiposDeSoporte = (Byte) tiposDeSoporte;
    }
    
    @Override
    public Object getSysAdmin() {
        return this.sysAdmin;
    }

    @Override
    public void setSysAdmin(Object sysAdmin) {
        if (!(sysAdmin instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setSysAdmin"
                    + "[Recibido: " + sysAdmin.toString() + "]");
        }
        this.sysAdmin = (Byte) sysAdmin;
    }
    
    @Override
    public Object getAppAdmin() {
        return this.appAdmin;
    }

    @Override
    public void setAppAdmin(Object appAdmin) {
        if (!(appAdmin instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setAppAdmin"
                    + "[Recibido: " + appAdmin.toString() + "]");
        }
        this.appAdmin = (Byte) appAdmin;
    }
    
    @Override
    public Object getEmpresaYSedes() {
        return this.empresaYSedes;
    }

    @Override
    public void setEmpresaYSedes(Object empresaYSedes) {
        if (!(empresaYSedes instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setEmpresaYSedes"
                    + "[Recibido: " + empresaYSedes.toString() + "]");
        }
        this.empresaYSedes = (Byte) empresaYSedes;
    }
    
    @Override
    public Object getDocumentosGenerales() {
        return this.documentosGenerales;
    }

    @Override
    public void setDocumentosGenerales(Object documentosGenerales) {
        if (!(documentosGenerales instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setDocumentosGenerales"
                    + "[Recibido: " + documentosGenerales.toString() + "]");
        }
        this.documentosGenerales = (Byte) documentosGenerales;
    }
    
    @Override
    public Object getRegistroAccesos() {
        return this.registroAccesos;
    }

    @Override
    public void setRegistroAccesos(Object registroAccesos) {
        if (!(registroAccesos instanceof Byte)) {
            throw new java.lang.ClassCastException("com.openlopd.entities."
                    + "seguridad.base.BasePermisosGrupos.setRegistroAccesos"
                    + "[Recibido: " + registroAccesos.toString() + "]");
        }
        this.registroAccesos = (Byte) registroAccesos;
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
    public Boolean hasAccess(ColumnasPermisos permiso, Byte modo) throws UnknownColumnException {
        return (Byte) this.getByName(permiso) >= modo;
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
    public final void importaPermisos(Object permisos) {
        if (!(permisos instanceof BasePermisosGrupos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BasePermisosGrupos.importaPermisos[Recibido: " + permisos.toString() + "]");
        }
        super.importaPermisos(permisos);
    }

    /**
     * Permite importar los permisos de acceso desde un contrato tipo.
     *
     * Util para nuevas altas o nuevos grupos con acceso a todas las áreas del
     * programa.
     * @param permisos Contrato desde el que importar los permisos.
     * @param tipoPermiso Tipo de permiso de acceso (Lectura/Escritura) ver
     * los tipos de permisos definidos en la clase.
     */
    public final void importaPermisosDesdeContrato(Object permisos, byte tipoPermiso) {
        if (!(permisos instanceof BaseContratosPermisos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BasePermisosGrupos.importaPermisosDesdeContrato[Recibido: " + permisos.toString() + "]");
        }
        BaseContratosPermisos p = (BaseContratosPermisos) permisos;
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                this.setByName(c,
                        ((Integer)p.getByName(c) > 0 ? tipoPermiso : BasePermisosGrupos.NO_ACCESS));
            }
        } catch  (UnknownColumnException e) {
            // Esta excepción no de debe producir ya que al utilizar el enumerado
            // no se puede enviar un nombre de columna que no existe.
            logger.error("Revisar el enumerado y las columnas de permisos.");
        } catch (Exception e) {
            logger.error("Error al importar los permisos desde ",
                    this.getClass().getName());
        }
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
        if (!(permisos instanceof BasePermisosGrupos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BasePermisosGrupos.compararPermisos[Recibido: " + permisos.toString() + "]");
        }
        return super.compararPermisos(permisos);
    }

    /**
     * Calcula el permiso resultante de agregar al objeto actual los permisos del objeto
     * destino.
     *
     * @param permisos Objeto que tiene los permisos a añadir al actual.
     * @exception UnknownColumnException Se produce cuando alguna de las columnas
     * de permisos no puede ser mezclada.
     */
    public void mezclarPermisos (BasePermisosGrupos permisos) throws UnknownColumnException {
        if (!(permisos instanceof BasePermisosGrupos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BasePermisosGrupos.mezclarPermisos[Recibido: " + permisos.toString() + "]");
        }
        byte p1, p2;
        for (ColumnasPermisos c: ColumnasPermisos.values()) {
            p1 = ((Byte) this.getByName(c)).byteValue();
            p2 = ((Byte) permisos.getByName(c)).byteValue();
            this.setByName(c, new Byte((byte) (p1 | p2)));
        }
    }

    /**
     * Calcula el permiso resultante de agregar al objeto actual los permisos de
     * los objetos de la lista.
     *
     * @param permisos Lista que tiene los permisos a añadir al actual.
     * @exception UnknownColumnException Se produce cuando alguna de las columnas
     * de permisos no puede ser mezclada.
     */
    public void mezclarPermisos(List<BasePermisosGrupos> permisos) throws UnknownColumnException {
        Iterator i = permisos.iterator();
        while (i.hasNext()) {
            this.mezclarPermisos((BasePermisosGrupos) i.next());
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BasePermisosGrupos)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.base.BasePermisosGrupos[Permisos=" + super.toString() + "]";
    }
    // </editor-fold>

}
