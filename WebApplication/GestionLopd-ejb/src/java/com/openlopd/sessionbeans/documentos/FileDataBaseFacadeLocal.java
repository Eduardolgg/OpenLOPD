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

package com.openlopd.sessionbeans.documentos;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.documentos.FileDataBase;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestión de ficheros.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Local
public interface FileDataBaseFacadeLocal {

    void create(FileDataBase fileDataBase);

    void edit(FileDataBase fileDataBase);

    void remove(FileDataBase fileDataBase);

    FileDataBase find(Object id);

    List<FileDataBase> findAll();

    List<FileDataBase> findRange(int[] range);

    int count();

    public FileDataBase copy(AccessInfo accessInfo, FileDataBase fileDataBase);
    
}
