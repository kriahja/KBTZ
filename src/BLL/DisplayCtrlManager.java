/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;
import BLL.Exceptions.BivExceptions;
import DAL.DisplayCtrlDBManager;
import Entities.DisplayCtrl;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Controls which Presentation goes to which Display. 
 * @author notandi
 */
public class DisplayCtrlManager {
    private static DisplayCtrlManager instance = null;
    private static DisplayCtrlDBManager db;
    private DisplayCtrlManager() {
        try {
            db = DisplayCtrlDBManager.getInstance();
        } catch (IOException ex) {
            throw new BivExceptions("Unable to connect to displayCtrl");
        }
    }
    /**
     *  
     * @return instance
     */
    public static DisplayCtrlManager getInstance() {
        if (instance == null) {
            instance = new DisplayCtrlManager();
        }
        return instance;
    }
    /**
     *
     * @return readAllPress() from DisplayCtrlDBManager
     */
    public ArrayList<DisplayCtrl> readAllPres() {
        try {
            return db.readAllPres();
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read all presentations");
        }
    }
    /**
     *
     * @return readAllEditPres() from DisplayCtrlDBManager
     */
    public ArrayList<DisplayCtrl> readAllEditPres() {
        try {
            return db.readAllEditPres();
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read all existing presentations");
        }
    }
    /**
     *
     * @return the current date from DisplayCtrlDBManager
     */
    public ArrayList<DisplayCtrl> runningPresentations() {
        try {
            ArrayList<DisplayCtrl> all = new ArrayList<>();
            ArrayList<DisplayCtrl> current = new ArrayList<>();
            all = db.readAllPres();
            Date now = new Date(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();
            for (int i = 0; i < all.size(); ++i) {
                if ((all.get(i).getStartDate().before(now) || all.get(i).getStartDate() == now)
                        && (all.get(i).getEndDate().after(now) || all.get(i).getEndDate() == now)) {
                    current.add(all.get(i));
                }
            }
            return current;
        } catch (SQLException ex) {
            throw new BivExceptions("unable to load currently running presentations");
        }
    }
    /**
     *
     * @param dc gets a specific DisplayCtrl
     */
    public void updateDisable(DisplayCtrl dc) {
        try {
            db.updateDisable(dc);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to update disable presentation");
        }
    }
    /**
     *
     * @param presId gets a specific Presentation id
     * @param dispId gets a specific Display id
     */
    public void create(int presId, int dispId) {
        try {
            db.create(presId, dispId);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to Create a presentation with the display");
        }
    }
    /**
     *
     * @param presId gets a specific Presentation id
     */
    public void delete(int presId) {
        try {
            db.delete(presId);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to Delete a presentation");
        }
    }
}