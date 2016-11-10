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

/**
 * Esta Interfaz contiene los permisos comunes que deben tener las clases
 * se establecen como objetos que luego deberán ser convertidos a sus tipos finales
 * esto es necesario para que no se pierdan permisos al incluir nuevas funcionalidades
 * así podremos mantener entre todas las clases permisos que para algunas serán enteros
 * para otras booleans, para otras Strings etc.
 *
 * Por este motivo cualquier permiso nuevo que se añada al sistema debe hacerse
 * primero en este Interfaz y luego revisarse las clases afectadas.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0
 * Fecha 27 de enero de 2011
 * @deprecated Sustituida por AbstractPermisos
 */
public interface InterfazPermisos {
    // <editor-fold defaultstate="collapsed" desc="Section GestSet">
    // TODO: Definir las propiedades que contendrán los permisos.
    Object getUsuarios();
    void setUsuarios(Object usuarios);
    Object getFicheros();
    void setFicheros(Object incidencias);    
    Object getIncidencias();
    void setIncidencias(Object incidencias);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Extra">
    /**
     * Cópia de permisos.
     *
     * Permite hacer la copia en el objeto de los permisos de otro objeto
     * de las mismas carácterísticas.
     * @param permisos Permisos a los que realizar la cópia.
     */
    void importaPermisos(Object permisos);
    boolean compararPermisos (Object permisos);
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode();

    @Override
    public boolean equals(Object object);

    @Override
    public String toString();
    // </editor-fold>
}
