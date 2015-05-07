/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BLL.Exceptions.BivExceptions;
import DAL.TextDBManager;
import Entities.Text;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author notandi
 * manages information from DAL(TextDBManager) to the GUI
 */
public class TextManager
{

    private static TextManager instance = null;

    private static TextDBManager db;

    private TextManager()
    {

       
        try
        {
            db = TextDBManager.getInstance();
        }
        catch (IOException ex)
        {
            throw new BivExceptions("Unable to connect to Text database");
        }
    }

    public static TextManager getInstance()
    {
        if (instance == null)
        {
            instance = new TextManager();
        }
        return instance;
    }
     
    /**
     *@param Text gets the Information in readAll method from TextDBManager
     */
    public ArrayList<Text> readAll()
    {
        try
        {
            return db.readAll();
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to readAll Text data");
        }
    }
        /**
     *@param id gets the Information in readById method from TextDBManager
     */


    public Text getById(int id)
    {
        try
        {
            return db.readById(id);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to Read By Id");
        }
    }
    /**
     *@param getBySafe reads all the information in ReadByNotSafe method in TextDBManager. 
     */

    public ArrayList<Text> getBySafe()
    {
        try
        {
            return db.readByNotSafe(false);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to Read By NotSafe");
        }
    }
    /**
     *@param title  reads all the information in the getByTitle method in  TextDBManager.
     */

    public Text getByTitle(String title)
    {
        try
        {
            return db.readByTitle(title);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to Read By Title");
        }
    }
    /**
     *@param text reads all the information in createText Method in  TextDBManager.
     */

    public void createText(Text text)
    {
        try
        {
            db.createText(text);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to Create Text");
        }
    }
    /**
     *@param id  Deletes the text in the TextDBManager.
     */

    public void deleteText(int id)
    {
        try {
            db.delete(id);
        } catch (SQLException ex) {
            Logger.getLogger(TextManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *@param txt updates the TextPresentations in the  TextDBManager
     */

    public void updateText(Text txt)
    {
        try
        {
            db.update(txt);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to Update the Text"); 
        }

    }

//    public void guiCreateText(Text text)
//    {
//        String text = textModel.getText(textTable.getSelectedRow());
//
//    }
}
