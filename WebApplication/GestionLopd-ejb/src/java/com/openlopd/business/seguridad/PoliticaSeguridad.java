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

package com.openlopd.business.seguridad;

import com.openlopd.entities.seguridad.ConstantesSeguridad;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permite convertir las constantes de segurdiad en la política de seguridad del
 * sistema.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0 10 de feb de 2011
 */
public final class PoliticaSeguridad implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(PoliticaSeguridad.class);
    
    public Constantes constantesSeguridad;
    private int longMinClave;

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor predeterminado.
     */
    public PoliticaSeguridad() {
        this.longMinClave = 0;
    }

    /**
     * Inicializa el objeto a través de una entidad con la definición de las
     * constantes.
     *
     * @param politica Objeto con las constantes de seguridad del sistema.
     */
    public PoliticaSeguridad(List<ConstantesSeguridad> politica) {
        this.importarPolítica(politica);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    public int getLongMinClave() {
        return longMinClave;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos Extra">
    /**
     * Permite importar los permisos al objeto.
     *
     * @param politica Lista de entidades con los permisos a añadir al objeto.
     */
    public void importarPolítica(List<ConstantesSeguridad> politica) {
        if(politica.size() != constantesSeguridad.values().length){
            logger.warn("Se detectan problemas con las constantes de "
                    + "seguridad, se esperaban [" + constantesSeguridad.values().length 
                    + "] y se encontraron [" + politica.size() + "]");
        }
        if (!politica.isEmpty()) {
            for (Iterator i = politica.iterator(); i.hasNext();) {
                setConstante((ConstantesSeguridad) i.next());
            }
        }
    }

    /**
     * Permite asignar una constante a través de una entidad.
     * 
     * @param constante Entidad con la constante a añadir.
     * @return <code>true</code> En caso de poder añadir al objeto la constante,
     * <code>false</code> en caso contrario.
     */
    public boolean setConstante(ConstantesSeguridad constante) {
        boolean estado = false;

        switch (constantesSeguridad.valueOf(constante.getNombre())) {
            case LONG_MIN_CLAVE:
                this.longMinClave = new Integer(constante.getValor());
                estado = true;
                break;
            default:
                logger.warn("La constante [{}] es desconocida.", constante.getNombre());
                estado = false;
        }
        return estado;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Standard Methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PoliticaSeguridad other = (PoliticaSeguridad) obj;
        if (this.longMinClave != other.longMinClave) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.longMinClave;
        return hash;
    }

    @Override
    public String toString() {
        return "PoliticaSeguridad{" + "longMinClave=" + longMinClave + '}';
    }
    // </editor-fold>
}
