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

package com.openlopd.business.facturacion;

import com.openlopd.documents.CumplimentarPlantilla;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.facturacion.TipoFormaPago;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.exceptions.NumFacturaGenerationException;
import com.openlopd.sessionbeans.facturacion.ContadorFacturaFacadeLocal;
import com.openlopd.sessionbeans.facturacion.FacturaFacadeLocal;
import com.openlopd.sessionbeans.facturacion.TipoFormaPagoFacadeLocal;
import com.openlopd.sessionbeans.seguridad.ConstantesSeguridadFacadeLocal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean encargado de la lógica de negocio relacionada con la facturación
 * @author Eduardo L. García Glez.
 * @version 0.0.1 14 de feb de 2011
 * Modificaciones
 *    15 de mar de 2011 añadido método getTipoFormaPagoById(short id);
 */
@Stateless
public class Facturacion implements FacturacionLocal {
    @EJB
    private ConstantesSeguridadFacadeLocal constantesSeguridadFacade;
    @EJB
    private ContadorFacturaFacadeLocal contadorFacturaFacade;
    @EJB
    private FacturaFacadeLocal facturaFacade;
    @EJB
    private TipoFormaPagoFacadeLocal tipoFormaPagoFacade;
    
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());

    /**
     * Obtiene Un listado con los tipos de formas de pago que acualmente
     * pueden utilizarse.
     * @return Listado de tipos de formas de pago.
     */
    @Override
    public List<TipoFormaPago> getTipoFormasPagoActivas() {
        return tipoFormaPagoFacade.getActivos();
    }

    /**
     * Obtiene un tipo de forma de pago a través de su id.
     * @param id Identificador único de la forma de pago.
     * @return Tipo de la forma de pago correspondiente con el id recibido.
     */
    @Override
    public TipoFormaPago getTipoFormaPagoById(short id) {
        return tipoFormaPagoFacade.find(id);
    }
    
//    public Factura nuevaFactura(Empresa empresa, TipoFormaPago formaPago, ) {
//        FileDataBase docFactura = new CumplimentarPlantilla(null, empresa).generar("Factura");
//        Factura factura = new Factura(GenKey.newKey(), empresa,
//                getTipoFormaPagoById((short) 1), new BigDecimal(100), new Date().getTime());
//        facturaFacade.create(factura);
//        return factura;
//    }
    
    /**
     * Crea una nueva factura.
     * 
     * Se genera un identificador único para la factura.
     * 
     * @param factura factura a añadir al sistema.
     * @return factura creada.
     */
    @Override
    public Factura nuevaFactura(Factura factura) throws NumFacturaGenerationException {
        FileDataBase docFactura = new FileDataBase(constantesSeguridadFacade.findByName("IdDocTemporal").getValor());
        //TODO: Revisar la numeración de la factura, creo que la serie no es correcta.
        factura.setId(contadorFacturaFacade.nextId(rb.getString("empresaID"), "0").toString() + "-" + "0");
        factura.setFecha(new Date().getTime());
        facturaFacade.create(factura);        
        //factura.setDocumento(new CumplimentarPlantilla(null, factura.getEmpresa(), 
        //        factura, ColumnasPermisos.FACTURAS).generar("Factura"));
        //facturaFacade.edit(factura);
        return factura;
    }
}
