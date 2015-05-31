/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Zalan
 */
public class DisplayManagerTest {
    ArrayList<String> readAllPres;
    DisplayManager dm;
    /**
     *
     */
    public DisplayManagerTest() throws SQLException {
       dm = DisplayManager.getInstance();
       
    }
  
    /**
     * Test of readAllPres method, of class DisplayManager.
     */
    @Test
    public void testReadAllPres() throws SQLException, IOException {
        System.out.println("Test readAllPres");

        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = new ArrayList<>();
        actual = dm.readAllPres();

        expected.add("ProductionArea");
        expected.add("Lobby");
        expected.add("IT Department");
        expected.add("CEO");

        assertEquals(expected, actual);
    }

}
