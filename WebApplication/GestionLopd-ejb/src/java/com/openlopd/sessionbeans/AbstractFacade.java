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

package com.openlopd.sessionbeans;

import com.jkingii.datatables.AbstractCDataTable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Clase encargada de mantener las operaciones que realizan los Bean de Session
 * sobre las entidades.
 * @param <T> Clase de la entidad sobre la que realizar operaciones.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.entities
 * @see com.openlopd.sessionbeans
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    /**
     * Permite inicializar el objeto enviandole la clase sobre la que se
     * realizarán las operaciones.
     *
     * @param entityClass Entidad sobre la que se realizarán las operaciones
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Obtiene el entity manager del Session Bean que lo implemente.
     *
     * @return Etity Manager de la clase.
     * @see javax.persistence.EntityManager
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Realiza la persistencia de la entidad
     *
     * Envía a <code>entity</code> a la base de datos.
     * @param entity Entidad sobre la que realizar la persistencia de datos.
     * @throws EntityExistsException Si la entidad ya existe (EntityExistsException
     * puede ser lanzada cuando se invoca la operación de persistencia, esta excepción
     * u otra PersistenceException se pueden lanzar en el momento del flush o el commit.)
     * @throws IllegalStateException Se lanza si el Entity Manager está cerrado.
     * @throws IllegalArgumentException Si el objeto recibido no es una entidad.
     * @throws TransactionRequiredException if invoked on a container-managed
     * entity manager of type PersistenceContextType.TRANSACTION and there is
     * no transaction.
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Permite modificar los valores de una entidad que ya se encuentran en
     * base de datos.
     *
     * Esta operación es equivalente un UPDATE en SQL.
     *
     * @param entity Entidad sobre la que realizar la operación.
     * @throws IllegalStateException En caso de que el EntityManager esté cerrado.
     * @throws IllegalArgumentException Si el objeto recibido no es una entidad.
     * @throws TransactionRequiredException if invoked on a container-managed
     * entity manager of type PersistenceContextType.TRANSACTION and there is
     * no transaction.
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Permite eliminar una fila de una tabla.
     *
     * Elimina la fila de la tabla asociada a la entidad recibida como parámetro
     * se eliminarán la fila que corresponde con la entidad, es equivalente a una
     * operación DELETE en SQL.
     * 
     * @param entity Entidad sobre la que realizar la operación.
     * @throws IllegalStateException En caso de que el EntityManager esté cerrado.
     * @throws IllegalArgumentException Si el objeto recibido no es una entidad.
     * @throws TransactionRequiredException if invoked on a container-managed
     * entity manager of type PersistenceContextType.TRANSACTION and there is
     * no transaction.
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Permite buscar información en base de datos a través de la clave primaria.
     *
     * Se buscará sobre la entidad asociada en la inicialización del objeto.
     *
     * @param id Id del objeto que se quiere encontrar en la base de datos.
     * @return Entidad encontrada con el id especificado o null si la entidad no
     * existe.
     * @throws IllegalStateException En caso de que el EntityManager esté cerrado.
     * @throws IllegalArgumentException Si el objeto recibido no es una entidad.
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Obtener todas las filas de la tabla relacionada con la entidad.
     * @return Una lista con todas las entidades o null en caso de no existir.
     * @throws IllegalStateException En caso de que el EntityManager esté cerrado.
     * @throws IllegalArgumentException Si el objeto recibido no es una entidad.
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * TODO: Documentar este método
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Calcula el número de filas de una entidad.
     * 
     * @return Númer de filas encontradas en la entidad.
     * @throws IllegalStateException En caso de que el EntityManager esté cerrado.
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
//    public List<T> findAllExt(int init, int limit) {
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
////        cq.orderBy(getEntityManager().getCriteriaBuilder().asc(o)); 
//        Query q = getEntityManager().createQuery(cq);
//        return q.getResultList().subList(init, init+limit);
//    }    
//    
//    public abstract SingularAttribute getExpression(int iShortCol);
//    
//    protected List<Order> findAllFiltering(AbstractCDataTable a, Root<T> root) {
//        List<Order> order = new ArrayList<Order>();
//        for(int i = 0; i < a.getiSortingCols(); i++ ) {
//            if (a.getsSortDir_C(i).equals("asc")) {
//                order.add(getEntityManager().getCriteriaBuilder().asc(root.get(getExpression(a.getiSortCol_C(i)))));
//            } else {
//                order.add(getEntityManager().getCriteriaBuilder().desc(root.get(getExpression(a.getiSortCol_C(i)))));
//            }   
//        }
//        return order;
//    }
//    
//    public List<T> findAllFiltering(AbstractCDataTable a) {
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<T> root = cq.from(entityClass);
//        cq.select(root);
//        cq.orderBy(findAllFiltering(a, root));
//        Query q = getEntityManager().createQuery(cq);
//        return q.getResultList();
//    }

}
