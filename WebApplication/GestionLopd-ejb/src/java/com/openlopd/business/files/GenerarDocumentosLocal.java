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

package com.openlopd.business.files;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import java.io.File;
import java.util.Map;
import javax.ejb.Local;

/**
 * Se encarga de gestionar la generación de los documentos a partir de
 * plantillas.
 * 
 * @author Eduardo L. García GLez.
 */
@Local
public interface GenerarDocumentosLocal {

    public FileDataBase CumplimentarPlantilla(AccessInfo accessInfo, String nombrePlantilla, ColumnasPermisos permiso);

    public FileDataBase CumplimentarPlantilla(Empresa empresaGeneradora, Empresa empresaDestino, String nombrePlantilla, ColumnasPermisos permiso);

    public com.openlopd.entities.documentos.FileDataBase GenerarNotificacionAgpdFirmada(com.openlopd.agpd.nota.xml.DatosFichero datosFichero, Fichero fichero) throws Exception;

    public java.io.File GenerarFicheroEnDisco(java.lang.String fileText) throws java.io.FileNotFoundException, java.io.IOException;

    public com.openlopd.entities.documentos.FileDataBase guardarFichero(File file, Empresa empresa, Shadow userInfo, ColumnasPermisos permiso) throws java.io.FileNotFoundException, java.io.IOException;

    public com.openlopd.entities.documentos.FileDataBase GenerarNotificacionAgpd(com.openlopd.agpd.nota.xml.DatosFichero datosFichero, Fichero fichero) throws java.lang.Exception;

    public FileDataBase CumplimentarPlantilla(AccessInfo accessInfo, String nombrePlantilla, Map<String, String[]> formData, ColumnasPermisos permiso);

    public boolean existTmpDir();
    
}
