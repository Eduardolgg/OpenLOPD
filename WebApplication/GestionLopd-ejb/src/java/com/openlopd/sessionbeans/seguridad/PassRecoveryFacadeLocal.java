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

package com.openlopd.sessionbeans.seguridad;

import com.openlopd.entities.seguridad.PassRecovery;
import java.util.List;
import javax.ejb.Local;

/**
 * Logica de recuperación de contraseñas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Local
public interface PassRecoveryFacadeLocal {

    void create(PassRecovery passRecovery);

    void edit(PassRecovery passRecovery);

    void remove(PassRecovery passRecovery);

    PassRecovery find(Object id);

    List<PassRecovery> findAll();

    List<PassRecovery> findRange(int[] range);

    int count();
    
}
