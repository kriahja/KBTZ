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
public class ImageManager {

    private static ImageManager instance = null;

    private static ImageDBManager db;

    private ImageManager() {

        try {
            db = ImageDBManager.getInstance();
        } catch (IOException ex) {
            throw new BivExceptions("Unable to connect to Image database");
        }
    }

    /**
     *
     * @return instance 
     */
    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    /**
     * @return 
     */
    public ArrayList<Image> readAll() {
        try {
            return db.readAll();
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to readAll Image data");
        }
    }

    /**
     * @param id Reads the Information in the getById method in ImageDBManager.
     * @return a specific id from ImageDBManager.
     */
    public Image getById(int id) {
        try {
            return db.readById(id);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to get Images by ID");
        }
    }

    /**
     * @return 
     */
    public ArrayList<Image> getBySafe() {
        try {
            return db.readByNotSafe(false);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to get Iformation by not safe");
        }
    }

    /**
     * @param title Reads the Information in the getByTitle method in
     * ImageDBManager.
     * @return a specific title from ImageDBManager.
     */
    public Image getByTitle(String title) {
        try {
            return db.readByTitle(title);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to get Information by Title");
        }
    }

    /**
     * @param img Reads the Information in the createImage method in
     * ImageDBManager.
     */
    public void createImage(Image img) {
        try {
            db.createImage(img);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param id Reads the Information in the delete method in ImageDBManager.
     */
    public void deleteImage(int id) {
        try {
            db.delete(id);
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to delte ImagePresentation ");
        }
    }

    /**
     * @param img Reads the Information in the UpdateImage method in
     * ImageDBManager.
     */
    public void updateImage(Image img) {
        try {
            db.update(img);
        } catch (SQLException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
