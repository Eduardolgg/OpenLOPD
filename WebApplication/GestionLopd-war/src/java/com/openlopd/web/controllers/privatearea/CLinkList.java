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

package com.openlopd.web.controllers.privatearea;

import com.openlopd.business.interfaz.InterfazLocal;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.interfaz.LinkList;
import com.openlopd.entities.interfaz.OperacionLopd;
import com.openlopd.sessionbeans.interfaz.LinkListFacadeLocal;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador del listado de links y Operaciones del área privada.
 * 
 * Permite obtener el menú del sistema para el usuario y las operaciones
 * asignadas a este.
 * 
 * @author Eduardo L. García GLez.
 */
public class CLinkList implements Serializable {
    OperacionLopdFacadeLocal operacionLopdFacade = lookupOperacionLopdFacadeLocal();
    LinkListFacadeLocal linkListFacade = lookupLinkListFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CLinkList.class);
    private AccessInfo accessInfo;
    
    InterfazLocal interfaz = lookupInterfazLocal();

    /**
     * Constructor por defecto.
     */
    public CLinkList() {
    }

    /**
     * Establece la información de acceso del usuario.
     * @param accessInfo Información de acceso del usuario.
     */
    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    /**
     * Obtiene el menú de usuario.
     * @return listado de links a los que el usuario tiene acceso.
     */
    public String getLinks() {
        return interfaz.getLinkListByGroupString(accessInfo);
    }
    
    /**
     * Obtiene el listado de operaciones.
     * 
     * Este listado de operaciones son las que permiten al usuario acceder
     * rápidamente a las distintos formularios que debe cumplimentar, además
     * permiten calcular las operaciones que el usuario tiene pendientes.
     * 
     * @return Listado de operaciones para las que el usuario tiene permisos.
     */
    public List<OperacionLopd> getOperations() {
        return operacionLopdFacade.findOperations(accessInfo);
    }
    
    public List<LinkList> getTareas() {
        return linkListFacade.findTareasHabituales(accessInfo);
    }
    
    /**
     * Obtiene el porcentaje de operaciones que el usuario ha completado.
     * @return Porcentaje de opreraciones completadas.
     * @see getOperations()
     */
    public Integer getPorcentajeCompletado() {
        return operacionLopdFacade.getPorcentajeCompletado(accessInfo);
    }
    
    /**
     * Obtiene la siguiente operación a realizar.
     * 
     * Permite informar al usuario de la siguiente operación de las no 
     * realizadas la cual es el siguiente paso lógico a completar.
     * 
     * @return Siguiente operación recomendada.
     */
    public OperacionLopd getOperacionRecomendada() {
        return operacionLopdFacade.getSiguienteOpRecomendada(accessInfo);
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private InterfazLocal lookupInterfazLocal() {
        try {
            Context c = new InitialContext();
            return (InterfazLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Interfaz!com.openlopd.business.interfaz.InterfazLocal");
        } catch (NamingException ne) {
            logger.error("Imposible recuperar el bean de session Interfaz");
            throw new RuntimeException(ne);
        }
    }

    private LinkListFacadeLocal lookupLinkListFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (LinkListFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/LinkListFacade!com.openlopd.sessionbeans.interfaz.LinkListFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener LinkListFacade");
            throw new RuntimeException(ne);
        }
    }
    
    private OperacionLopdFacadeLocal lookupOperacionLopdFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OperacionLopdFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OperacionLopdFacade!com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener OperacionLopdFacade");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
