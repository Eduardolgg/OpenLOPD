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

package public_area;

import com.openlopd.business.seguridad.ContratosLocal;
import com.openlopd.entities.seguridad.ContratosTipo;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class CTarifas implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CTarifas.class);
    ContratosLocal contratos = lookupContratosLocal();
    private BigDecimal importe;

    public CTarifas() {
    }
    
    private String getImporteContrato_(short tipo) {
        this.importe = ((ContratosTipo) contratos.getContratoTipo(tipo)).getImporte();
        return this.importe.toPlainString();
    }
    
    public String getImporteB() {
        return this.getImporteContrato_((short) 0);
    }
    
    public String getImporteM() {
        return this.getImporteContrato_((short) 1);
    }
    
    public String getImporteP() {
        return this.getImporteContrato_((short) 2);
    }

    private ContratosLocal lookupContratosLocal() {
        try {
            Context c = new InitialContext();
            return (ContratosLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Contratos!com.openlopd.business.seguridad.ContratosLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean Contratos");
            throw new RuntimeException(ne);
        }
    }
}
