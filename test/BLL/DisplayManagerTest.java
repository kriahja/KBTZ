/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BLL.Exceptions.BivExceptions;
import DAL.DisplayDBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Zalan
 */
public class DisplayManagerTest {
    ArrayList<String> readAllPres;
    DisplayDBManager db;
    /**
     *
     */
    public DisplayManagerTest() throws SQLException {
       
       
    }
  

//    @Before
//    public void setUp() throws Exception {
//       
////        readAllPres.add("a");
////        readAllPres.add("b");
////        readAllPres.add("c");
//    }

//    @After
//    public void tearDown() throws Exception {
//    }

 
    /**
     * Test of readAllPres method, of class DisplayManager.
     */
    @Test
    public void testReadAllPres() throws SQLException, IOException {
        System.out.println("Test readAllPres");
//      
//        ArrayList<String> expected = new ArrayList<>();
//        expected.add("a");
//        expected.add("b");
//        expected.add("c");         
//        assertEquals(expected,readAllPres);
        ArrayList<String> expected = db.readAllDispName();
        Iterator itr = expected.iterator();
        assertTrue(itr.hasNext()); 
//        ArrayList<String> result = DisplayDBManager.getInstance().readAllDispName();
//        assertEquals(expected, db.readAllDispName());
    }

    /**
     * Test of getInstance method, of class DisplayManager.
     */
//    @Test
//    public void testGetInstance() {
//        System.out.println("getInstance");
//        DisplayManager expResult = null;
//        DisplayManager result = DisplayManager.getInstance();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
    
}
