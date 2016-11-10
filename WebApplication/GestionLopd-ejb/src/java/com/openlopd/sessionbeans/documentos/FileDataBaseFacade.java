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
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Gestión de ficheros.
 *
 * @author Eduardo L. García GLez.
 * @version 0.0.0
 */
@Stateless
public class FileDataBaseFacade extends AbstractFacade<FileDataBase> implements FileDataBaseFacadeLocal {

    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FileDataBaseFacade() {
        super(FileDataBase.class);
    }

    @Override
    public FileDataBase copy(AccessInfo accessInfo, FileDataBase fileDataBase) {
        FileDataBase f = new FileDataBase(GenKey.newKey(), 
                accessInfo.getSubEmpresa(), accessInfo.getUserInfo(),
                fileDataBase.getUploadDate(), fileDataBase.getFilename(),
                fileDataBase.getMimeType(), fileDataBase.getFile(),
                fileDataBase.getMd5(), fileDataBase.getSize(), 
                fileDataBase.getPermiso());
        this.create(f);
        return f;
    }
}
