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

import com.openlopd.business.seguridad.AccessInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * 16 de may de 2012
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.1
 */
public abstract class AbstractCDataTable implements Serializable {
    
    // Needed to set all array params.
    protected HttpServletRequest httpServletRequest;
    // Display start point in the current data set.
    protected int iDisplayStart;
    // Number of records that the table can display in the current draw.
    protected int iDisplayLength;
    // Number of columns being displayed.
    protected int iColumns;
    // Global search field.
    protected String sSearch;
    // Global filter.
    protected Boolean bRegex;
    // Indicator for if a column is flagged as searchable or not 
    // on the client-side
    protected List<String> bSearchable_C;
    // Individual column filter
    protected List<String> sSearch_C;
    // True if the individual column filter should be treated as a regular 
    // expression for advanced filtering, false if not
    protected List<String> bRegex_C;
    // Indicator for if a column is flagged as sortable or not 
    // on the client-side
    protected List<String> bSortable_C;
    // Number of columns to sort on
    protected int iSortingCols;
    // Column being sorted on (you will need to decode this number 
    // for your database)
    protected List<String> iSortCol_C;
    // Direction to be sorted - "desc" or "asc".
    protected List<String> sSortDir_C;
    // The value specified by mDataProp for each column. This can be useful 
    // for ensuring that the processing of data is independent from the 
    // order of the columns.
    protected List<String> mDataProp_C;
    // Information for DataTables to use for rendering.
    protected int sEcho;

    public AbstractCDataTable() {
    }
    
    /**
     * HTTP Request sended to client.
     *
     * @param httpServletRequest
     */
    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
        setbSearchable_C(httpServletRequest);
        setsSearch_C(httpServletRequest);
        setbRegex_C(httpServletRequest);
        setbSortable_C(httpServletRequest);
        setiSortCol_C(httpServletRequest);
        setsSortDir_C(httpServletRequest);
        setmDataProp_C(httpServletRequest);
    }

    /**
     * Display start point.
     *
     * @param iDisplayStart Display start point in the current data set.
     */
    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    /**
     * Number of records that the table can display in the current draw.
     *
     * It is expected that the number of records returned will be equal to this
     * number, unless the server has fewer records to return.
     *
     * @param iDisplayLength Number of records
     */
    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }
    
    

    /**
     * Number of columns being displayed.
     *
     * (useful for getting individual column search info)
     *
     * @param iColumns Number of columns being displayed.
     */
    public void setiColumns(int iColumns) {
        this.iColumns = iColumns;
    }

    /**
     * Global search field.
     *
     * @param sSearch Global search field
     */
    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

    /**
     * Global filter.
     *
     * @param bRegex
     * <code>true</code> if the global filter should be treated as a regular
     * expression for advanced filtering,
     * <code>false</code> if not.
     */
    public void setbRegex(Boolean bRegex) {
        this.bRegex = bRegex;
    }

    public int getsEcho() {
        return sEcho;
    }    

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public Boolean getbRegex_C(int col) throws IndexOutOfBoundsException {
        return getBoolean(bRegex_C, col);
    }

    private void setbRegex_C(HttpServletRequest httpServletRequest) {
        this.bRegex_C = getArrayParam(httpServletRequest, "bRegex_");
    }

    public Boolean getbSearchable_C(int col) throws IndexOutOfBoundsException {
        return getBoolean(bSearchable_C, col);
    }

    private void setbSearchable_C(HttpServletRequest httpServletRequest) {
        this.bSearchable_C = getArrayParam(httpServletRequest, "bSearchable_");
    }

    public Boolean getbSortable_C(int col) throws IndexOutOfBoundsException {
        return getBoolean(bSortable_C, col);
    }

    private void setbSortable_C(HttpServletRequest httpServletRequest) {
        this.bSortable_C = getArrayParam(httpServletRequest, "bSortable_");
    }

    public Integer getiSortCol_C(int col) throws IndexOutOfBoundsException {
        return getInteger(iSortCol_C, col);
    }

    private void setiSortCol_C(HttpServletRequest httpServletRequest) {
        this.iSortCol_C = getArrayParam(httpServletRequest, "iSortCol_");
    }
    
    public int getiSortingCols(){
        return this.iSortingCols;
    }
    
    public void setiSortingCols(int iSortingCols){
        this.iSortingCols = iSortingCols;
    }

    public String getmDataProp_C(int col) throws IndexOutOfBoundsException {
        return mDataProp_C.get(col);
    }
    
    private void setmDataProp_C(HttpServletRequest httpServletRequest) {
        this.mDataProp_C = getArrayParam(httpServletRequest, "mDataProp_");
    }

    public void getsSearch_C(int col) throws IndexOutOfBoundsException {
        sSearch_C.get(col);
    }
    
    private void setsSearch_C(HttpServletRequest httpServletRequest) {
        this.sSearch_C = getArrayParam(httpServletRequest, "sSearch_");
    }

    public String getsSortDir_C(int col) throws IndexOutOfBoundsException {
        return sSortDir_C.get(col);
    }
    
    private void setsSortDir_C(HttpServletRequest httpServletRequest) {
        this.sSortDir_C = getArrayParam(httpServletRequest, "sSortDir_");
    }

    //<editor-fold defaultstate="collapsed" desc="Internal Business">
    private Integer getInteger(List<String> list, int col) throws IndexOutOfBoundsException {
        return Integer.valueOf(list.get(col));
    }
    
    private Boolean getBoolean(List<String> list, int col) throws IndexOutOfBoundsException {
        return Boolean.valueOf(list.get(col));
    }
    
    private List<String> getArrayParam(HttpServletRequest httpServletRequest, String paramPrefix) {
        Integer i = 0;
        String param;
        List<String> result = new ArrayList();
        while ((param = httpServletRequest.getParameter(paramPrefix + i++)) != null) {
            result.add(param);
        }
        return result;
    }
    //</editor-fold>
    
    protected String getJson(DTResponse dtResponse) {
        return dtResponse.toJson();
    }
    
    //<editor-fold defaultstate="collapsed" desc="External Business">
    public abstract String getJson();
    public abstract Boolean isAuthorizedAccess();
    public ResponseConfig getResponseConfig(AccessInfo accessInfo) {
        ResponseConfig config = new ResponseConfig();
        config.setTimeZone(accessInfo.getTimeZone());
        return config;
    }
    //</editor-fold>

    public int getIDisplayStart() {
        return this.iDisplayStart;
    }

    public int getIDisplayLength() {
        return this.iDisplayLength;
    }

    public Object getSSearch() {
        return this.sSearch;
    }
}
