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

package com.jkingii.datatables;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object Data Table response.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class DTResponse<T extends JsonEntity> implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(DTResponse.class);
    
    public static final int TOTAL_DISPLAY_RECODS = 10;
    @Expose
    private int iTotalRecords;         //  Total records, before filtering (i.e. the total number of records in the database)
    @Expose
    private int iTotalDisplayRecords;  //  Total records, after filtering (i.e. the total number of records after filtering has been applied - not just the number of records being returned in this result set)
    @Expose
    private int sEcho;              //  An unaltered copy of sEcho sent from the client side. This parameter will change with each draw (it is basically a draw count) - so it is important that this is implemented. Note that it strongly recommended for security reasons that you 'cast' this parameter to an integer in order to prevent Cross Site Scripting (XSS) attacks.
    @Expose
    private String sColumns = null; //  Optional - this is a string of column names, comma separated (used in combination with sName) which will allow DataTables to reorder data on the client-side if required for display. Note that the number of column names returned must exactly match the number of columns in the table. For a more flexible JSON format, please consider using mDataProp.
    @Expose
    private List<T> aaData;            //  array  aaData  The data in a 2D array. Note that you can change the name of this parameter with sAjaxDataProp.
    
    // Configuración de la respuesta.
    ResponseConfig responseConfig;
    

    public DTResponse() {
    }

    public DTResponse(int iTotalRecords, int iTotalDisplayRecords, int sEcho, 
            String sColumns, ResponseConfig responseConfig) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.sEcho = sEcho;
        this.sColumns = sColumns;
        this.responseConfig = responseConfig;
    }
    
    public DTResponse<T> setInterval(List<T> data, int iDisplayStart, int iDisplayLength) {        
        this.aaData = data.subList(iDisplayStart, 
                iDisplayStart + iDisplayLength > data.size() ? data.size() : iDisplayStart + iDisplayLength);
        return this;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }
    
    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }
    
    private String aaDataToJson() {
        StringBuilder result = new StringBuilder();
        for(T e : aaData) {
            result.append(e.toTableEditableJson(responseConfig).toString());
            result.append(",");
        }
        return (result.length() > 0 ? result.substring(0, result.length() - 1) : "");
    }
    
    public String toJson() {        
            return "{\"iTotalRecords\":" + iTotalRecords + ","
                    + "\"iTotalDisplayRecords\":"+ iTotalDisplayRecords + ","
                    + "\"sEcho\":" + sEcho + ","
                    + "\"aaData\":["
                    + (aaData != null ? aaDataToJson() : "")
                    + "]}";
    }
}
