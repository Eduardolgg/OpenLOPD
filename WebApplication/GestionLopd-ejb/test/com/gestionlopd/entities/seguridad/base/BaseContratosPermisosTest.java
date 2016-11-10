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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests de la clase BaseContratosPermisosTest, se espera que se realice una perfecta integración
 * con la clase abstracta que extiende ya que existen muchos métodos que dependiendo de su orientación
 * utilizarán distintos tipos de datos, por este motivo y por las pruebas que se deben realizar al
 * añadir nuevos permisos se debe mantener un control riguroso sobre el funcionamiento de los permisos,
 * el aumento de los mismos (añadiendo nuevas propiedades), copia y comparación con clases similares.
 *
 * IMPORTANTE: se espera que el test de error si se incorporan nuevas propiedades y no se añaden
 * las pruebas ya que estas al ser nulas no pueden ser coparadas.
 * 
 * @author Eduardo L. García Glez.
 * @see com.openlopd.entities.seguridad.base.BaseContratosPermisos
 * @see com.openlopd.entities.seguridad.base.AbstractPermisos
 * @version 1.0.0
 * Fecha 27 de enero de 2011
 */
public class BaseContratosPermisosTest {

    public BaseContratosPermisosTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        // No se ha identificado nada que sea necesario realizar antes para que
        // las pruebas tengan éxito.
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // No se ha identificado nada que sea necesario realizar antes para que
        // las pruebas tengan éxito.
    }

    @Before
    public void setUp() {
        // No se ha identificado ninguna inicialización previa a el
        // lanzamiento de cada método que deba ser realizada.
    }

    @After
    public void tearDown() {
        // No se ha identificado nada que deba ser ejecutado después de realizar
        // cada una de las pruebas.
    }

    /**
     * Test of getUsuarios method, of class BaseContratosPermisos.
     */
    @Test
    public void testGetUsuarios() {
        System.out.println("getUsuarios");
        BaseContratosPermisos instance = new BaseContratosPermisos();
        Object expResult = null;
        Object result = instance.getUsuarios();
        assertEquals(expResult, result);
        // Se prueba que es posible trabajar con Integer como clase.
        try {
           instance.setUsuarios(new Integer(10));
           result = instance.getUsuarios();
           assertEquals(10, result);
           assertEquals(new Integer(10), result);
        } catch (Exception e) {
            fail("Se produjo una excepción: " + e.getMessage());
        }

        // Se prueba que es posible trabajar con int como tipo base.
        try {
           instance.setUsuarios(20);
           result = instance.getUsuarios();
           assertEquals(20, result);
           assertEquals(new Integer(20), result);
        } catch (Exception e) {
            fail("Se produjo una excepción: " + e.getMessage());
        }
    }

    /**
     * Test of setUsuarios method, of class BaseContratosPermisos.
     */
    @Test
    public void testSetUsuarios() {
        Object result;
        System.out.println("setUsuarios");
        BaseContratosPermisos instance = new BaseContratosPermisos();
    
        // Se prueba la asignación de un valor nulo.
        try {
            instance.setUsuarios(null);
            result = instance.getUsuarios();
            fail("Se esperaba la excepción java.lang.NullPointerException");
        } catch (java.lang.ClassCastException e) {
            fail("Esta excepción no se debió producir: [" + e.toString() + "] " + e.getMessage());
        } catch (java.lang.NullPointerException e) {
            // Se provoca esta excepción, no se puede asignar nulo al permiso.
        } catch (Exception e) {
            fail("Esta excepción no se debió producir: [" + e.toString() + "] " + e.getMessage());
        }

        // Se prueba la asignación de una clase diferente a la esperada.
        try {
            instance.setUsuarios(new Boolean(true));
            result = instance.getUsuarios();
            fail("Se esperaba la excepción java.lang.ClasCatException");
        } catch (java.lang.ClassCastException e) {
            // Se provoca esta excepción, no se puede asignar nulo al permiso.
        } catch (java.lang.NullPointerException e) {
            fail("Esta excepción no se debió producir: [" + e.toString() + "] " + e.getMessage());
        } catch (Exception e) {
            fail("Esta excepción no se debió producir: [" + e.toString() + "] " + e.getMessage());
        }

        // Se prueba que es posible trabajar valor máximo y mínimo.
        try {
           // Valor máximo
           instance.setUsuarios(Integer.MAX_VALUE);
           result = instance.getUsuarios();
           assertEquals(Integer.MAX_VALUE, result);

           // Valor mínimo
           instance.setUsuarios(Integer.MIN_VALUE);
           result = instance.getUsuarios();
           assertEquals(Integer.MIN_VALUE, result);
        } catch (Exception e) {
            fail("Se produjo una excepción: " + e.getMessage());
        }

        // Se prueba que es posible trabajar valor máximo y mínimo.
        try {
           // Valor máximo
           instance.setUsuarios(new Integer(Integer.MAX_VALUE));
           result = instance.getUsuarios();
           assertEquals(Integer.MAX_VALUE, result);

           // Valor mínimo
           instance.setUsuarios(new Integer(Integer.MIN_VALUE));
           result = instance.getUsuarios();
           assertEquals(Integer.MIN_VALUE, result);
        } catch (Exception e) {
            fail("Se produjo una excepción: " + e.getMessage());
        }
    }

    /**
     * Test of getFicheros method, of class BaseContratosPermisos.
     */
    @Test
    public void testGetFicheros() {
        System.out.println("getFicheros");
        BaseContratosPermisos instance = new BaseContratosPermisos();
        Object expResult = null;
        Object result = instance.getFicheros();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setFicheros method, of class BaseContratosPermisos.
     */
    @Test
    public void testSetFicheros() {
        System.out.println("setFicheros");
        Object ficheros = null;
        BaseContratosPermisos instance = new BaseContratosPermisos();
        //instance.setFicheros(ficheros);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of importaPermisos method, of class BaseContratosPermisos.
     */
    @Test
    public void testImportaPermisos() {
        System.out.println("importaPermisos");
        Object permisos = null;
        BaseContratosPermisos instance = new BaseContratosPermisos();

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
            instance.importaPermisos(new BaseContratosPermisos());
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
        BasePermisosGrupos otrosPermisos = new BasePermisosGrupos();
        try {            
            otrosPermisos.setFicheros(new Byte("1"));
            otrosPermisos.setUsuarios(new Byte("1"));
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
        BaseContratosPermisos nuevosPermisos = new BaseContratosPermisos();
        try {
            nuevosPermisos.setFicheros(10);
            nuevosPermisos.setUsuarios(20);
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
        BaseContratosPermisos nuevosPermisos2 = new BaseContratosPermisos();
        try {
            nuevosPermisos2.setFicheros(11);
            nuevosPermisos2.setUsuarios(20);
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
     * Test of compararPermisos method, of class BaseContratosPermisos.
     */
    @Test
    public void testCompararPermisos() {
        System.out.println("compararPermisos");
        Object permisos = null;
        BaseContratosPermisos instance = new BaseContratosPermisos();
        boolean expResult = false;
        boolean result;
        instance.setFicheros(10);
        instance.setUsuarios(50);
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
        BasePermisosGrupos bpg = new BasePermisosGrupos();        
        try {
            bpg.setFicheros(new Byte("1"));
            bpg.setUsuarios(new Byte("2"));
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
        BaseContratosPermisos bcp = new BaseContratosPermisos();
        try {
            bcp.setFicheros(10);
            bcp.setUsuarios(2);
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
        bcp = new BaseContratosPermisos();
        try {
            bcp.setFicheros(10);
            bcp.setUsuarios(50);
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

    /**
     * Test of hashCode method, of class BaseContratosPermisos.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
       
        // Se verifica que el has code de un objeto nullo es 0 y que no
        // Se producen excepciones.
        BaseContratosPermisos instance = new BaseContratosPermisos();
        int expResult = 0;
        try {
            int result = instance.hashCode();
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Se produjo una excepción inesperada: [" + e.toString() + "]" + e.getMessage());
        }

        instance.setUsuarios(10);
        instance.setFicheros(20);

        assertTrue("No se ha generado un hash correcto", instance.hashCode() > 0);

        BaseContratosPermisos other = new BaseContratosPermisos();
        other.setUsuarios(11);
        other.setUsuarios(20);

        assertTrue("Los Hash deberían ser distintos", instance.hashCode() != other.hashCode());
    }

    /**
     * Test of equals method, of class BaseContratosPermisos.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        BaseContratosPermisos instance = new BaseContratosPermisos();
        boolean expResult = false;
        boolean result = instance.equals(object);
        instance.setFicheros(20);
        instance.setUsuarios(15);
        assertTrue("Cuando se compara un objeto con nullo se espera que el resultado sea que no son iguales.", result == expResult);

        assertTrue("Una instancia Comparada con sigo misma debe ser igual", instance.equals(instance) == true);

        BasePermisosGrupos otherType = new BasePermisosGrupos();
        otherType.setFicheros(new Byte("1"));
        otherType.setUsuarios(new Byte("2"));
        assertTrue("Una instancia Comparada con un objeto de distinto tipo debe ser diferente", instance.equals((Object) otherType) == false);

        BaseContratosPermisos other = new BaseContratosPermisos();
        other.setFicheros(20);
        other.setUsuarios(15);
        assertTrue("Una instancia diferente con el mismo contenido debe ser igual", instance.equals(other));
        other.setFicheros(1);
        assertTrue("Una instancia de diferente con diferente contenido debe ser diferente", instance.equals(other));
    }

    /**
     * Test of toString method, of class BaseContratosPermisos.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BaseContratosPermisos instance = new BaseContratosPermisos();
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