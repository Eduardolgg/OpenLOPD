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
 * Enumerado que lista todas las columnas de permisos existentes.
 *
 * A través de este enumerado se puede obtener un listado de las columnas
 * de permisos de forma que se puedan realizar operaciones de bucles y similares.
 * 
 * Nota: Siempre añadir por el final, está mapeado a base de datos.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0
 * Fecha 30 de enero de 2011
 *
 */
public enum ColumnasPermisos {
 /* Nombre              código */
    PUBLICO,            /*   0 */
    USUARIOS,           /*   1 */
    FICHEROS,           /*   2 */
    INCIDENCIAS,        /*   3 */
    GESTION_PERSONAL,   /*   4 */
    GESTION_EMPRESAS,   /*   5 */
    PLANTILLAS,         /*   6 */
    MODOS_DESTRUCCION,  /*   7 */
    FACTURAS,           /*   8 */
    DESTINATARIOS,      /*   9 */
    DOCUMENTO_SEGURIDAD,/*  10 */
    REGISTRO_ENTRADA,   /*  11 */
    REGISTRO_SALIDA,    /*  12 */
    INVENTARIO_SOPORTES,/*  13 */
    TIPOS_DE_SOPORTES,  /*  14 */
    SYS_ADMIN,          /*  15 */
    APP_ADMIN,          /*  16 */
    EMPRESA_Y_SEDES,    /*  17 */
    DOCUMENTOS_GENERALES,/*  18 */  
    REGISTRO_ACCESOS    /*  19 */
}