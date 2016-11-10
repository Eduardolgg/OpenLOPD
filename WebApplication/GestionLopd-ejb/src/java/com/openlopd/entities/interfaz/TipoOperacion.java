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

package com.openlopd.entities.interfaz;

import java.util.ArrayList;
import java.util.List;

/**
 * Identificadores de los tipos de operación.
 *
 * Esta clase se utiliza en los entities de la base de datos, en forma de
 * Ordinal, para añadir hay que hacerlo siempre por el final para no romper la
 * base de datos.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 15 de dic de 2012
 */
public enum TipoOperacion {

    Ficheros(false),                   /*    0 */
    DocumentoSeguridad(false),         /*    1 */
    GestionPersonal(true),             /*    2 */
    ProtocolosSeguridad(false),        /*    3 */
    InventarioDeSoportes(false),       /*    4 */
    ModosDeDestruccion(false),         /*    5 */
    GestionDePlantillas(false),        /*    6 */
    NombramientoRespSeguridad(false),  /*    7 */
    ClausulaEMail(false),              /*    8 */
    DesignarRespSeguridad(true),       /*    9 */
    FuncionesYObligaciones(false),     /*   10 */
    InformacionAlPersonal(false),      /*   11 */
    TiposSoportes(false),              /*   12 */
    InformacionALosAfectados(false);   /*   13 */
    
    private boolean defaulValue;
    
    TipoOperacion(boolean defaultValue) {
        this.defaulValue = defaultValue;
    }

    /**
     * Obtiene el valor por defecto de la operación LOPD.
     * 
     * Permite inicializar operaciones.
     * 
     * @return true en caso de estar realizada por defecto, false en caso
     * contrario.
     */
    public boolean getDefaulValue() {
        return defaulValue;
    }
    
    /**
     * Obtiene la lista de actualización de una operación.
     * @return Listado de operaciones que tienen que ser actualizadas.
     */
    public static List<OperacionLopd> getUpdateList(TipoOperacion operacion, 
            Boolean realizada) {
        List<OperacionLopd> list = new ArrayList<OperacionLopd>();
        list.add(new OperacionLopd(operacion, realizada));
        
        switch (operacion) {
            case Ficheros:
                list.add(new OperacionLopd(TipoOperacion.DocumentoSeguridad, Boolean.FALSE));
                break;
            case DocumentoSeguridad:
                break;
            case GestionPersonal:
                break;
            case ProtocolosSeguridad:                
                list.add(new OperacionLopd(TipoOperacion.DocumentoSeguridad, Boolean.FALSE));
                break;
            case InventarioDeSoportes:
                break;
            case ModosDeDestruccion:
                break;
            case GestionDePlantillas:
                break;
            case NombramientoRespSeguridad:
                list.add(new OperacionLopd(TipoOperacion.DocumentoSeguridad, Boolean.FALSE));
                break;
            case ClausulaEMail:
                break;
            case DesignarRespSeguridad:
                list.add(new OperacionLopd(TipoOperacion.DocumentoSeguridad, Boolean.FALSE));
                list.add(new OperacionLopd(TipoOperacion.NombramientoRespSeguridad, Boolean.FALSE));
                break; 
            case FuncionesYObligaciones:
                break;
            case InformacionAlPersonal:
                break;
            case TiposSoportes:
                break;
            case InformacionALosAfectados:
                break;
            default:
                throw new AssertionError(operacion.name());
        }        
        return list;
    }
    
}
