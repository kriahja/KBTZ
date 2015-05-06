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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author notandi
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

    public Text getById(int id)
    {
        try
        {
            return db.readById(id);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("");
        }
    }

    public ArrayList<Text> getBySafe()
    {
        try
        {
            return db.readByNotSafe(false);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("");
        }
    }

    public Text getByTitle(String title)
    {
        try
        {
            return db.readByTitle(title);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("");
        }
    }

    public void createText(Text text)
    {
        try
        {
            db.createText(text);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("");
        }
    }

    public void deleteText(int id)
    {
        db.delete(id);
    }

    public void updateText(Text txt)
    {
        try
        {
            db.update(txt);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions(""); 
        }

    }

//    public void guiCreateText(Text text)
//    {
//        String text = textModel.getText(textTable.getSelectedRow());
//
//    }
}
