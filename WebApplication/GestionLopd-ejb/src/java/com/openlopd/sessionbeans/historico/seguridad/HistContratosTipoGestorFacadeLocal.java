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

package com.openlopd.sessionbeans.historico.seguridad;

import com.openlopd.entities.historicos.seguridad.HistContratosTipoGestor;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargada de los métodos necesarios para la gestión del historial
 * de los permisos de un contrato tipo de un gestor.
 *
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @see com.openlopd.entities.historicos.seguridad.HistContratosTipoGestor
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
@Local
public interface HistContratosTipoGestorFacadeLocal {

    void create(HistContratosTipoGestor histContratosTipoGestor);

    void edit(HistContratosTipoGestor histContratosTipoGestor);

    void remove(HistContratosTipoGestor histContratosTipoGestor);

    HistContratosTipoGestor find(Object id);

    List<HistContratosTipoGestor> findAll();

    List<HistContratosTipoGestor> findRange(int[] range);

    int count();

}
