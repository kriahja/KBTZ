/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BLL.Exceptions.BivExceptions;
import DAL.DisplayCtrlDBManager;
import Entities.DisplayCtrl;
import Entities.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author notandi
 */
public class DisplayCtrlManager
{

    private static DisplayCtrlManager instance = null;

    private static DisplayCtrlDBManager db;

    private DisplayCtrlManager()
    {

        try
        {
            db = DisplayCtrlDBManager.getInstance();
        }
        catch (IOException ex)
        {
            throw new BivExceptions("Unable to connect to displayCtrl");
        }
    }

    public static DisplayCtrlManager getInstance()
    {
        if (instance == null)
        {
            instance = new DisplayCtrlManager();
        }
        return instance;
    }

    public ArrayList<DisplayCtrl> readAllPres()
    {
        try
        {
            return db.readAllPres();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DisplayCtrlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
