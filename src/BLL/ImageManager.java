/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BLL.Exceptions.BivExceptions;
import DAL.ImageDBManager;
import Entities.Image;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author notandi
 */
public class ImageManager
{

    private static ImageManager instance = null;

    private static ImageDBManager db;

    private ImageManager()
    {

        try {
            db = ImageDBManager.getInstance();
        } catch (IOException ex) {
            throw new BivExceptions("Unable to connect to Image database");
        }
    }

    public static ImageManager getInstance()
    {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    public ArrayList<Image> readAll()
    {
        try {
            return db.readAll();
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to readAll Image data");
        }
    }

    public Image getById(int id)
    {
        return db.readById(id);
    }

    public ArrayList<Image> getByPriorityId(int priId)
    {
        return db.readByPriorityId(priId);
    }

    public ArrayList<Image> getByDisplayId(int dispId)
    {
        return db.readByDisplayId(dispId);
    }

    public ArrayList<Image> getBySafe()
    {
        return db.readByNotSafe(false);
    }

    public Image getByTitle(String title)
    {
        return db.readByTitle(title);
    }
    
    public Image getByPath(String path)
    {
        return db.readByPath(path);
    }

    public void createImage(Image text)
    {
        try {
            db.createImage(text);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteImage(int id)
    {
        db.delete(id);
    }
    
    

    public void updateImage(Image txt)
    {
        try {
            db.update(txt);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    


//    public void guiCreateImage(Image text)
//    {
//        String text = textModel.getImage(textTable.getSelectedRow());
//
//    }
}
