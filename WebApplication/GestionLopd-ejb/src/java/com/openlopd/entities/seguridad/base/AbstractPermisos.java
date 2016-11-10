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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase contiene los permisos comunes que deben tener las clases
 * se establecen como objetos que luego deberán ser convertidos a sus tipos finales
 * esto es necesario para que no se pierdan permisos al incluir nuevas funcionalidades
 * así podremos mantener entre todas las clases permisos que para algunas serán enteros
 * para otras booleans, para otras Strings etc.
 *
 * Por este motivo cualquier permiso nuevo que se añada al sistema debe hacerse
 * primero en esta clase y luego revisarse las clases afectadas.
 *
 * En esta clase abstracta se deben además añadir todos aquellos métodos que sean
 * comunes para todas aquellas clases que gestionan permisos.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.1
 * Fecha 27 de enero de 2011
 * @see com.openlopd.entities.seguridad.base.InterfazPermisos
 * Modificaciones:
 *    05 de feb de 2011, se eliminan excepciones innecesarias en los métodos
 *    extra y constructores ya que no pueden ser producidas por el usuario sino por
 *    una mala ampliación del sistema (añadir columnas de premisos), errores
 *    de este tipo de ampliación serán enviados al sistema de log.
 */
public abstract class AbstractPermisos {
    private static Logger logger = LoggerFactory.getLogger(AbstractPermisos.class);
    
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    ColumnasPermisos columnasPermisos;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GestSet">
    // TODO: Definir las propiedades que contendrán los permisos.
    abstract Object getPublico();
    abstract void setPublico(Object publico);
    abstract Object getUsuarios();
    abstract void setUsuarios(Object usuarios);
    abstract Object getFicheros();
    abstract void setFicheros(Object ficheros);   
    abstract Object getIncidencias();
    abstract void setIncidencias(Object gestionPersonal);
    abstract Object getGestionPersonal();
    abstract void setGestionPersonal(Object gestionPersonal);
    abstract Object getGestionEmpresas();
    abstract void setGestionEmpresas(Object gestionEmpresas);
    abstract Object getPlantillas();
    abstract void setPlantillas(Object plantillas);
    abstract Object getModosDestruccion();
    abstract void setModosDestruccion(Object modosDestruccion);
    abstract Object getFacturas();
    abstract void setFacturas(Object facturas);
    abstract Object getDestinatarios();
    abstract void setDestinatarios(Object destinatarios);
    abstract Object getDocumentoSeguridad();
    abstract void setDocumentoSeguridad(Object documentoSeguridad);
    abstract Object getRegistroEntrada();
    abstract void setRegistroEntrada(Object documentoSeguridad);
    abstract Object getRegistroSalida();
    abstract void setRegistroSalida(Object documentoSeguridad);
    abstract Object getInventarioSoportes();
    abstract void setInventarioSoportes(Object inventarioSoportes);
    abstract Object getTiposDeSoporte();
    abstract void setTiposDeSoporte(Object inventarioSoportes);
    abstract Object getSysAdmin();
    abstract void setSysAdmin(Object sysAdmin);
    abstract Object getAppAdmin();
    abstract void setAppAdmin(Object appAdmin);    
    abstract Object getEmpresaYSedes();
    abstract void setEmpresaYSedes(Object empresaYSede);
    abstract Object getDocumentosGenerales();
    abstract void setDocumentosGenerales(Object documentosGenerales);
    abstract Object getRegistroAccesos();
    abstract void setRegistroAccesos(Object registroAccesos);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Extra">
    public void importaListaPermisos(List permisos) {
        for(Object p: permisos) {
            importaPermisos(p);
        }
    }
    
    /**
     * Cópia de permisos.
     *
     * Permite hacer la copia en el objeto de los permisos de otro objeto
     * de las mismas carácterísticas.
     * @param permisos Permisos a los que realizar la cópia.
     */
    public void importaPermisos(Object permisos) {
        if (!(permisos instanceof AbstractPermisos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BaseContratosPermisos.importaPermisos[Recibido: " + permisos.toString() + "]");
        }
        AbstractPermisos castPermisos = (AbstractPermisos) permisos;
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                this.setByName(c, castPermisos.getByName(c));
            }
        } catch (UnknownColumnException e) {
            // Esta excepción no de debe producir ya que al utilizar el enumerado
            // no se puede enviar un nombre de columna que no existe.
            logger.error("Revisar el enumerado y las columnas de permisos.");
        }
    }

    /**
     * Inicializa los valores de los permisos con el objeto recibido.
     * @param permiso Permiso a asignar a cada una de las columnas.
     */
    public void inicializarPermisos (Object permiso) {
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                this.setByName(c, permiso);  // TODO: Ver que alcambia un permiso no cambian todos;
            } 
        } catch  (UnknownColumnException e) {
            // Esta excepción no de debe producir ya que al utilizar el enumerado
            // no se puede enviar un nombre de columna que no existe.
            logger.error("Revisar el enumerado y las columnas de permisos.");
        }
    }

    /**
     * Compara todos los permisos definidos en la clase abstracta.
     *
     * Esto es una ayuda al método Equals para las clases que extiendan esta.
     * Utilizando este método se prodrá realizar una comparación de todos los
     * permisos a través del método equals del objeto que contiene el permiso
     * sin problemas en el aumento de los permisos en el sistema.
     * @param permisos Objeto que tiene los permisos a comparar con el objeto actual.
     * @return <code>true</code> en caso de que todos los permisos sean iguales
     * <code>false</code> en caso contrario.
     * @throws UnknownColumnException emite esta excepción en caso de tener problemas
     * en el acceso a una columna.
     */
    public boolean compararPermisos (Object permisos) throws UnknownColumnException {
        if (!(permisos instanceof AbstractPermisos)) {
            throw new java.lang.ClassCastException("com.openlopd.entities.seguridad.base.BaseContratosPermisos.compararPermisos[Recibido: " + permisos.toString() + "]");
        }
        AbstractPermisos castPermisos = (AbstractPermisos) permisos;
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                if (!this.getByName(c).equals(castPermisos.getByName(c))) {
                    return false;
                }
            }
        } catch (UnknownColumnException e) {
            // Esta excepción no de debe producir ya que al utilizar el enumerado
            // no se puede enviar un nombre de columna que no existe.
            logger.error("Revisar el enumerado y las columnas de permisos.");
            return false;
        }
        return true;
    }

    /**
     * Obtiene el objeto asociado a la columna <code>name</code>.
     * @param name Nombre de la columna que se espera obtener.
     * @return objeto con los datos solicitados.
     * @throws UnknownColumnException Se emite esta excepción si el nombre
     * solicitado no está relacionado con una columna.
     */
    public Object getByName(String name) throws UnknownColumnException {
        return getByName(ColumnasPermisos.valueOf(name));
    }

    /**
     * Obtiene el objeto asociado a la columna  <code>name</code>.
     * @param col Identificador de la columna, ver el enumerado <code>ColumnasPermisos</code>.
     * @return objeto con los datos solicitados.
     * @throws UnknownColumnException Se emite esta excepción si el nombre
     * solicitado no está relacionado con una columna.
     */
    public Object getByName(ColumnasPermisos col) throws UnknownColumnException {
        switch (col) {
            case PUBLICO:
                return this.getPublico();
            case USUARIOS:
                return this.getUsuarios();
            case FICHEROS:
                return this.getFicheros();
            case INCIDENCIAS:
                return this.getIncidencias();
            case GESTION_PERSONAL:
                return this.getGestionPersonal();
            case GESTION_EMPRESAS:
                return this.getGestionEmpresas();
            case PLANTILLAS:
                return this.getPlantillas();
            case MODOS_DESTRUCCION:
                return this.getModosDestruccion();
            case FACTURAS:
                return this.getFacturas();
            case DESTINATARIOS:
                return this.getDestinatarios();
            case DOCUMENTO_SEGURIDAD:
                return this.getDocumentoSeguridad();
            case REGISTRO_ENTRADA:
                return this.getRegistroEntrada();
            case REGISTRO_SALIDA:
                return this.getRegistroSalida();
            case INVENTARIO_SOPORTES:
                return this.getInventarioSoportes();
            case TIPOS_DE_SOPORTES:
                return this.getTiposDeSoporte();
            case SYS_ADMIN:
                return this.getSysAdmin();
            case APP_ADMIN:
                return this.getAppAdmin();
            case EMPRESA_Y_SEDES:
                return this.getEmpresaYSedes();
            case DOCUMENTOS_GENERALES:
                return this.getDocumentosGenerales();
            case REGISTRO_ACCESOS:
                return this.getRegistroAccesos();
            default:
                throw new UnknownColumnException ("La columna: " + col.toString() + "no existe.");
        }
    }

    /**
     * Establece el objeto asociado a la columna <code>name</code>.
     * @param name Nombre de la columna que se espera obtener.
     * @param value Valor a almacenar en la columna.
     * @throws UnknownColumnException Se emite esta excepción si el nombre
     * solicitado no está relacionado con una columna.
     */
    public void setByName(String name, Object value) throws UnknownColumnException {
        setByName(columnasPermisos.valueOf(name), value);
    }   

    /**
     * Establece el objeto asociado a la columna  <code>name</code>.
     * @param col Identificador de la columna, ver el enumerado <code>ColumnasPermisos</code>.
     * @param value Valor a almacenar en la columna.
     * @throws UnknownColumnException Se emite esta excepción si el nombre
     * solicitado no está relacionado con una columna.
     */
    public void setByName(ColumnasPermisos col, Object value) throws UnknownColumnException {
        switch (col) {
            case PUBLICO:
                this.setPublico(value);
                break;
            case USUARIOS:
                this.setUsuarios(value);
                break;
            case FICHEROS:
                this.setFicheros(value);
                break;
            case INCIDENCIAS:
                this.setIncidencias(value);
                break;
            case GESTION_PERSONAL:
                this.setGestionPersonal(value);
                break;
            case GESTION_EMPRESAS:
                this.setGestionEmpresas(value);
                break;
            case PLANTILLAS:
                this.setPlantillas(value);
                break;
            case MODOS_DESTRUCCION:
                this.setModosDestruccion(value);
                break;
            case FACTURAS:
                this.setFacturas(value);
                break;
            case DESTINATARIOS:
                this.setDestinatarios(value);
                break;
            case DOCUMENTO_SEGURIDAD:
                this.setDocumentoSeguridad(value);
                break;
            case REGISTRO_ENTRADA:
                this.setRegistroEntrada(value);
                break;
            case REGISTRO_SALIDA:
                this.setRegistroSalida(value);
                break;
            case INVENTARIO_SOPORTES:
                this.setInventarioSoportes(value);
                break;
            case TIPOS_DE_SOPORTES:
                this.setTiposDeSoporte(value);
                break;
            case SYS_ADMIN:
                this.setSysAdmin(value);
                break;
            case APP_ADMIN:
                this.setAppAdmin(value);
                break;
            case EMPRESA_Y_SEDES:
                this.setEmpresaYSedes(value);
                break;
            case DOCUMENTOS_GENERALES:
                this.setDocumentosGenerales(value);
                break;
            case REGISTRO_ACCESOS:
                this.setRegistroAccesos(value);
                break;
            default:
                throw new UnknownColumnException ("La columna: " + col.toString() + "no existe.");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode(){
        int hash = 0;
        for (ColumnasPermisos c : ColumnasPermisos.values()) {
            try {
                hash += (this.getByName(c) != null ? this.getByName(c).hashCode() : 0);
            } catch (Exception e) {
                // Esto evita que se produzcan errores en caso de que los permisos
                // sean nulos.
                logger.error("Revisar el enumerado y las columnas de permisos.");
            }
        }
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AbstractPermisos)) {
            return false;
        }
        AbstractPermisos other = (AbstractPermisos) object;
        if (other == null) {
            return false;
        }
        try {
            this.compararPermisos(other);
        } catch (Exception e) {
            logger.error("Revisar el enumerado y las columnas de permisos.");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (ColumnasPermisos c : ColumnasPermisos.values()) {
            try {
                s += c.toString() + ": " + (this.getByName(c) != null ? this.getByName(c).toString() : null);
            } catch (Exception e) {
                // Si la columna no existe no tiene por qué estar en el string
                // no es necesario realizar un agestión de los errores.
                logger.error("Revisar el enumerado y las columnas de permisos.");
            }
        }
        return  s;
    }
    // </editor-fold>
}
