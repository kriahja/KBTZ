/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BLL.Exceptions.BivExceptions;
import DAL.ImageDBManager;
import Entities.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author notandi manages information from DAL(ImageDBManager) to the GUI
 */
public class ImageManager
{

    private static ImageManager instance = null;

    private static ImageDBManager db;

    private ImageManager()
    {

        try
        {
            db = ImageDBManager.getInstance();
        }
        catch (IOException ex)
        {
            throw new BivExceptions("Unable to connect to Image database");
        }
    }

    public static ImageManager getInstance()
    {
        if (instance == null)
        {
            instance = new ImageManager();
        }
        return instance;
    }

    /**
     * @param Image Reads the Information in the ReadAll method in
     * ImageDBManager.
     */
    public ArrayList<Image> readAll()
    {
        try
        {
            return db.readAll();
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to readAll Image data");
        }
    }

    /**
     * @param id Reads the Information in the getById method in ImageDBManager.
     */

    public Image getById(int id)
    {
        try
        {
            return db.readById(id);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to get Images by ID");
        }
    }

    /**
     * @param Image Reads the Information in the getByNotSafe method in
     * ImageDBManager.
     */

    public ArrayList<Image> getBySafe()
    {
        try
        {
            return db.readByNotSafe(false);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to get Iformation by not safe");
        }
    }

    /**
     * @param title Reads the Information in the getByTitle method in
     * ImageDBManager.
     */

    public Image getByTitle(String title)
    {
        try
        {
            return db.readByTitle(title);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to get Information by Title");
        }
    }

//    public Image getByPath(String path)
//    {
//        return db.readByPath(path);
//    }
    /**
     * @param img Reads the Information in the createImage method in
     * ImageDBManager.
     */
    public void createImage(Image img)
    {
        try
        {
            db.createImage(img);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * @param id Reads the Information in the delete method in ImageDBManager.
     */
    public void deleteImage(int id)
    {
        try
        {
            db.delete(id);
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to delte ImagePresentation ");
        }
    }

    /**
     * @param img Reads the Information in the UpdateImage method in
     * ImageDBManager.
     */
    public void updateImage(Image img)
    {
        try
        {
            db.update(img);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }

//    public void updateDisable(Image img)
//    {
//        try
//        {
//            db.updateDisable(img);
//        }
//        catch (SQLException ex)
//        {
//            throw new BivExceptions("Unable to Update the Image");
//        }
//    }

//    public void guiCreateImage(Image text)
//    {
//        String text = textModel.getImage(textTable.getSelectedRow());
//
//    }
}
