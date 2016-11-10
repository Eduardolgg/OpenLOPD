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

package com.openlopd.common.facturacion;

import com.openlopd.business.facturacion.FacturacionLocal;
import com.openlopd.entities.facturacion.TipoFormaPago;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase engargada de la gestión de los datos del jspf
 * WEB-INF.jspf.common.facturacion.setFormaPago.jspf
 * @author Eduardo L. García Glez.
 * @version 0.0.0 14 de mar de 2011
 */
public class TiposFormaPago implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(TiposFormaPago.class);
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    FacturacionLocal facturacion = lookupFacturacionLocal();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public TiposFormaPago() {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene el listado de tipos de formas de pago activas.
     * @return Listado de tipos de formas de pago activas.
     */
    public List<TipoFormaPago> getActivas() {
        return facturacion.getTipoFormasPagoActivas();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Lookup">
    private FacturacionLocal lookupFacturacionLocal() {
        try {
            Context c = new InitialContext();
            return (FacturacionLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Facturacion!com.openlopd.business.facturacion.FacturacionLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el bean de session Facturacion");
            throw new RuntimeException(ne);
        }
    }
    // </editor-fold>
}
