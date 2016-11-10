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

package com.openlopd.sessionbeans.lopd;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.lopd.PersonaFichero;
import com.openlopd.entities.lopd.PersonaFicheroPK;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class PersonaFicheroFacade extends AbstractFacade<PersonaFichero> implements PersonaFicheroFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFicheroFacade() {
        super(PersonaFichero.class);
    }
    
    @Override
    public List<PersonaFichero> findByIdPersona(String idPersona) {
        Query q = em.createNamedQuery("PersonaFichero.findByIdPersona");
        q.setParameter("idPersona", idPersona);
        return q.getResultList();
    }
    
    /**
     * Elimina de la tabla todos los registros asociados a la persona.
     * @param idPersona identificador de la persona a eliminar.
     * @return Número de entradas eliminadas.
     */
    @Override
    public int removeAll(String idPersona) {
        Query q = em.createNamedQuery("PersonaFichero.DeleteByIdPersona");
        q.setParameter("idPersona", idPersona);
        return q.executeUpdate();
    }
    
    /**
     * Inserta todos los ficheros asociados a la persona.
     * @param accessInfo información de acceso del usuario que realiza el cambio.
     * @param ficheros ficheros a insertar.
     * @param idPersona identificador de la persona a la que asignar los ficheros.
     */
    @Override
    public void insertAll(AccessInfo accessInfo, String ficheros, String idPersona){
        if (ficheros == null) {
            return;
        }
        
        String [] lf = ficheros.split(";");
        for (String idFichero: lf) {
            PersonaFichero pf = new PersonaFichero(
                    new PersonaFicheroPK(idPersona, idFichero),
                    new Date().getTime(),
                    accessInfo.getUserInfo().getUsuario());
            this.create(pf);
        }
    }    
    
    /**
     * Actualiza los ficheros asociados a una persona.
     * 
     * Nota: primero borra todos y luego los inserta.
     * 
     * @param accessInfo información de acceso del usuario que realiza el cambio.
     * @param ficheros ficheros a actualizar.
     * @param idPersona identificador de la persona a la que asignar los ficheros.
     */
    @Override
    public void updateAll(AccessInfo accessInfo, String ficheros, String idPersona){        
        this.removeAll(idPersona);
        this.insertAll(accessInfo, ficheros, idPersona);
    }
}
