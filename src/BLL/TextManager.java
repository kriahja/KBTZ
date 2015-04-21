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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author notandi
 */
public class TextManager
{

    private static TextManager instance = null;

    private static TextDBManager db;
    private List<Text> cars;

    private TextManager()
    {
        cars = new ArrayList<>();
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
        return db.readAll();
    }

    public Text getById(int id)
    {
        return db.readById(id);
    }

    public ArrayList<Text> getByPriorityId(int priId)
    {
        return db.readByPriorityId(priId);
    }

    public ArrayList<Text> getByDisplayId(int dispId)
    {
        return db.readByDisplayId(dispId);
    }

    public ArrayList<Text> getBySafe()
    {
        return db.readByNotSafe(false);
    }

    public Text getByTitle(String title)
    {
        return db.readByTitle(title);
    }

}
