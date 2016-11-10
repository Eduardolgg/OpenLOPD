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

package com.openlopd.entities.seguridad.base;

import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.entities.seguridad.base.BaseContratosPermisos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eduardo L. García Glez.
 * @see com.openlopd.entities.seguridad.base.BaseContratosPermisos
 * @see com.openlopd.entities.seguridad.base.AbstractPermisos
 * @version 1.0.0
 * Fecha 30 de enero de 2011
 */
public class BasePermisosGruposTest {

    public BasePermisosGruposTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getUsuarios method, of class BasePermisosGrupos.
     */
//    @Test
//    public void testGetUsuarios() {
//        System.out.println("getUsuarios");
//        BasePermisosGrupos instance = new BasePermisosGrupos();
//        Object expResult = null;
//        Object result = instance.getUsuarios();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setUsuarios method, of class BasePermisosGrupos.
     */
//    @Test
//    public void testSetUsuarios() {
//        System.out.println("setUsuarios");
//        Object usuarios = null;
//        BasePermisosGrupos instance = new BasePermisosGrupos();
//        instance.setUsuarios(usuarios);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getFicheros method, of class BasePermisosGrupos.
     */
//    @Test
//    public void testGetFicheros() {
//        System.out.println("getFicheros");
//        BasePermisosGrupos instance = new BasePermisosGrupos();
//        Object expResult = null;
//        Object result = instance.getFicheros();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setFicheros method, of class BasePermisosGrupos.
     */
//    @Test
//    public void testSetFicheros() {
//        System.out.println("setFicheros");
//        Object ficheros = null;
//        BasePermisosGrupos instance = new BasePermisosGrupos();
//        instance.setFicheros(ficheros);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of importaPermisos method, of class BasePermisosGrupos.
     */
    @Test
    public void testImportaPermisos() throws Exception {
        System.out.println("importaPermisos");
        Object permisos = null;
        BasePermisosGrupos instance = new BasePermisosGrupos();

        // Se espera esta exceptión en caso de inicializar lo permisos a través de
        // un objeto nulo.
        try {
            instance.importaPermisos(permisos);
            fail("Se esperaba una NullPointerException");
        } catch (java.lang.NullPointerException e) {
            // Se esperava esta excepción.
        } catch (Exception e) {
            fail("No se esperava esta exceptión: [" + e.toString() + "]" + e.getMessage());
        }

        // Se intentan importar permisos nulos.
        try {
            instance.importaPermisos(new BasePermisosGrupos());
            assertEquals(null, instance.getUsuarios());
        } catch (NullPointerException e) {
            // Se esperaba esta excepción.
        } catch (Exception e) {
            fail ("Se ha producido una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Se compara con un objeto diferente que no es de permisos.
        try {
            instance.importaPermisos(new Integer(200));
            fail("Se esperaba una ClassCastException");
        } catch (ClassCastException e) {
            // Se esperaba esta excepción.
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Se compara con un objeto de permisos diferentes.
        BaseContratosPermisos otrosPermisos = new BaseContratosPermisos();
        try {
            otrosPermisos.setFicheros(new Integer("1"));
            otrosPermisos.setUsuarios(new Integer("1"));
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada al crear el objeto para la comparación: [" +
                    e.toString() + "]" + e.getMessage());
        }
        try {
            instance.importaPermisos(otrosPermisos);
            fail("Se debió producir una excepción del tipo ClassCastException.");
        } catch (ClassCastException e) {
            // Se espera que se produzca esta exceptión.
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Se compara con permisos reales iguales.
        BasePermisosGrupos nuevosPermisos = new BasePermisosGrupos();
        try {
            nuevosPermisos.setFicheros(new Byte("1"));
            nuevosPermisos.setUsuarios(new Byte("2"));
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        try {
            instance.importaPermisos(nuevosPermisos);
            assertEquals(nuevosPermisos.toString(), instance.toString());
            assertEquals(nuevosPermisos, instance);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Se vuelve a importar permisos en un mismo objeto.
        BasePermisosGrupos nuevosPermisos2 = new BasePermisosGrupos();
        try {
            nuevosPermisos2.setFicheros((byte) 11);
            nuevosPermisos2.setUsuarios((byte) 20);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        try {
            instance.importaPermisos(nuevosPermisos2);
            assertEquals(nuevosPermisos2.toString(), instance.toString());
            assertEquals(nuevosPermisos2, instance);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
    }

    /**
     * Test of compararPermisos method, of class BasePermisosGrupos.
     */
    @Test
    public void testCompararPermisos() throws Exception {
        System.out.println("compararPermisos");
        Object permisos = null;
        BasePermisosGrupos instance = new BasePermisosGrupos();
        boolean expResult = false;
        boolean result;

        instance.setFicheros((byte) 10);
        instance.setUsuarios((byte) 50);
        // Comparar con objeto nulo.
        try {
            result = instance.compararPermisos(permisos);
            assertEquals(expResult, result);
            fail("Error: No se pueden comparar objetos nulos. [testCompararPermisos]");
        } catch (ClassCastException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (NullPointerException e) {
            // Se esperaba esta excepción, no se pueden comparar objetos nulos.
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Comparar con otro tipo de objeto.
        BaseContratosPermisos bpg = new BaseContratosPermisos();
        try {
            bpg.setFicheros(10);
            bpg.setUsuarios(50);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
        try {
            result = instance.compararPermisos(bpg);
            fail("Error: Se debió producir una excepción");
        } catch (ClassCastException e) {
            // Se esperaba esta excepción, no se pueden comparar objetos nulos.
        } catch (NullPointerException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Comparar con un objeto con permisos distintos.
        // TODO: Comparar todos los permisos.
        BasePermisosGrupos bcp = new BasePermisosGrupos();
        try {
            bcp.setFicheros((byte) 10);
            bcp.setUsuarios((byte) 2);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
        try {
            result = instance.compararPermisos(bcp);
            assertTrue("Los permisos deberían ser distintos", !result);
        } catch (ClassCastException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (NullPointerException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        // Comparar con permisos iguales.
        // TODO: Comparar todos los permisos.
        bcp = new BasePermisosGrupos();
        try {
            bcp.setFicheros((byte) 10);
            bcp.setUsuarios((byte) 50);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
        try {
            result = instance.compararPermisos(bcp);
            assertTrue("Los permisos deberían ser iguales", result);
        } catch (ClassCastException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (NullPointerException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
    }

    private Byte myPruebaPermiso (byte pInicial, byte pExtra) {
        BasePermisosGrupos instance = new BasePermisosGrupos();
        BasePermisosGrupos other = new BasePermisosGrupos();        
        
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                instance.setByName(c, pInicial);
                other.setByName(c, pExtra);
            }
            instance.mezclarPermisos(other);
            return (Byte) instance.getByName(ColumnasPermisos.values()[1]);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
        return null;
    }
    
    /**
     * Test of mezclarPermisos method, of class BasePermisosGrupos.
     */
    @Test
    public void testMezclarPermisos() throws Exception {
        System.out.println("mezclarPermisos");
        BasePermisosGrupos permisos = null;
        BasePermisosGrupos instance = new BasePermisosGrupos();

        try {
            instance.mezclarPermisos(permisos);
        } catch (UnknownColumnException e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        } catch (NullPointerException e) {
            // Se produce esta excepción ya que los objetos no están inicializados.
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        /*
         * A continuación se deben probar todos los tipos de permisos.
         */

        // Se verifica que 0 | 0 = 0
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.NO_ACCESS, BasePermisosGrupos.NO_ACCESS) == BasePermisosGrupos.NO_ACCESS);
        
        // Se verifica que 0 | 1 = 1
        assertTrue ("Error en el cálculo del permisos.", 
                myPruebaPermiso(BasePermisosGrupos.NO_ACCESS, BasePermisosGrupos.LECTURA) == BasePermisosGrupos.LECTURA);

        // Se verifica que 0 | 2 = 2
        assertTrue ("Error en el cálculo del permisos.", 
                myPruebaPermiso(BasePermisosGrupos.NO_ACCESS, BasePermisosGrupos.ESCRITURA) == BasePermisosGrupos.ESCRITURA);

        // Se verifica que 0 | 3 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.NO_ACCESS, BasePermisosGrupos.RW) == BasePermisosGrupos.RW);

        // Se verifica que 1 | 0 = 1
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.LECTURA, BasePermisosGrupos.NO_ACCESS) == BasePermisosGrupos.LECTURA);

        // Se verifica que 1 | 1 = 1
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.LECTURA, BasePermisosGrupos.LECTURA) == BasePermisosGrupos.LECTURA);

        // Se verifica que 1 | 2 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.LECTURA, BasePermisosGrupos.ESCRITURA) == BasePermisosGrupos.RW);

        // Se verifica que 1 | 3 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.LECTURA, BasePermisosGrupos.RW) == BasePermisosGrupos.RW);

        // Se verifica que 2 | 0 = 2
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.ESCRITURA, BasePermisosGrupos.NO_ACCESS) == BasePermisosGrupos.ESCRITURA);

        // Se verifica que 2 | 1 = 2
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.ESCRITURA, BasePermisosGrupos.LECTURA) == BasePermisosGrupos.RW);

        // Se verifica que 2 | 2 = 2
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.ESCRITURA, BasePermisosGrupos.ESCRITURA) == BasePermisosGrupos.ESCRITURA);

        // Se verifica que 2 | 3 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.ESCRITURA, BasePermisosGrupos.RW) == BasePermisosGrupos.RW);

        // Se verifica que 3 | 0 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.RW, BasePermisosGrupos.NO_ACCESS) == BasePermisosGrupos.RW);

        // Se verifica que 3 | 1 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.RW, BasePermisosGrupos.LECTURA) == BasePermisosGrupos.RW);

        // Se verifica que 3 | 2 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.RW, BasePermisosGrupos.ESCRITURA) == BasePermisosGrupos.RW);

        // Se verifica que 3 | 3 = 3
        assertTrue ("Error en el cálculo del permisos.",
                myPruebaPermiso(BasePermisosGrupos.RW, BasePermisosGrupos.RW) == BasePermisosGrupos.RW);
    }

    /**
     * Test of hashCode method, of class BasePermisosGrupos.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");

        // Se verifica que el has code de un objeto nullo es 0 y que no
        // Se producen excepciones.
        BasePermisosGrupos instance = new BasePermisosGrupos();
        int expResult = 0;
        try {
            int result = instance.hashCode();
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        instance.setUsuarios((byte) 10);
        instance.setFicheros((byte) 20);

        assertTrue("No se ha generado un hash correcto", instance.hashCode() > 0);

        BasePermisosGrupos other = new BasePermisosGrupos();
        other.setUsuarios((byte) 11);
        other.setUsuarios((byte) 20);

        assertTrue("Los Hash deberían ser distintos", instance.hashCode() != other.hashCode());
    }

    /**
     * Test of equals method, of class BasePermisosGrupos.
     */
    @Test
    public void testEquals() {

        System.out.println("equals");
        Object object = null;
        BasePermisosGrupos instance = new BasePermisosGrupos();
        boolean expResult = false;
        boolean result = instance.equals(object);
        instance.setFicheros((byte) 20);
        instance.setUsuarios((byte) 15);
        assertTrue("Cuando se compara un objeto con nullo se espera que el resultado sea que no son iguales.", result == expResult);

        assertTrue("Una instancia Comparada con sigo misma debe ser igual", instance.equals(instance) == true);

        BaseContratosPermisos otherType = new BaseContratosPermisos();
        otherType.setFicheros(1);
        otherType.setUsuarios(2);
        assertTrue("Una instancia Comparada con un objeto de distinto tipo debe ser diferente", instance.equals((Object) otherType) == false);

        BasePermisosGrupos other = new BasePermisosGrupos();
        other.setFicheros((byte) 20);
        other.setUsuarios((byte) 15);
        assertTrue("Una instancia diferente con el mismo contenido debe ser igual", instance.equals(other));
        other.setFicheros((byte) 1);
        assertTrue("Una instancia de diferente con diferente contenido debe ser diferente", instance.equals(other));
    }

    /**
     * Test of toString method, of class BasePermisosGrupos.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BasePermisosGrupos instance = new BasePermisosGrupos();
        String expResult = "";
        String result;
        // String del objeto nulo.
        try {
            result = instance.toString();
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }
    }

}