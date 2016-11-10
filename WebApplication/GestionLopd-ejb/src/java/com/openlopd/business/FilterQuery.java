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

package com.openlopd.business;

import java.util.Objects;

/**
 * Extrae un comando de un texto de búsqueda.
 *
 * @author Eduardo L. García Glez.
 */
public class FilterQuery {

    private String textSearch;
    private String command = "";
    private String filter = null;

    public FilterQuery(String textSearch) {
        this.textSearch = textSearch;

        if (textSearch != null) {
            String trimSearch = textSearch.trim();
            if (trimSearch.startsWith("cmd:")) {
                int spaceIndex = trimSearch.indexOf(" ");
                if (trimSearch.length() > 4) {
                    int endComand = spaceIndex > 0 ? spaceIndex : trimSearch.length();
                    this.command = trimSearch.substring(4, endComand);
                }
                
                this.filter = spaceIndex > 0 ? trimSearch.
                        substring(spaceIndex + 1, trimSearch.length()): "";
            } else {
                this.filter = textSearch;
            }
        }
    }
    
 

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.textSearch);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FilterQuery other = (FilterQuery) obj;
        if (!Objects.equals(this.textSearch, other.textSearch)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FilterQuery{" + "textSearch=" + textSearch + ", command=" + command + ", filter=" + filter + '}';
    }
}
