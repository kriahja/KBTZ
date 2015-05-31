/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 **/

package BLL;
import BLL.Exceptions.BivExceptions;
import DAL.DisplayDBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Manages all display logic
 * @author a.tamas
 */
public class DisplayManager {
    private static DisplayManager instance = null;
    private static DisplayDBManager db;
   
    private DisplayManager() {
        try {
            db = DisplayDBManager.getInstance();
        } catch (IOException ex) {
            throw new BivExceptions("Unable to connect to display data");
        }
    }
    /**
     *
     * @return instance
     */
    public static DisplayManager getInstance() {
        if (instance == null) {
            instance = new DisplayManager();
        }
        return instance;
    }
    /**
     *
     * @return readAllDispName() from DisplayDBManager
     */
    public ArrayList<String> readAllPres() {
        try {
            return db.readAllDispName();
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to connect to display data");
        }
    }
}