/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author notandi
 */
public class Image extends Presentation {

    private String path;

    /**
     *
     * @param id
     * @param presTypeId
     * @param title
     * @param startDate
     * @param endDate
     * @param timer
     * @param notSafe
     * @param path
     */
    public Image(int id, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, String path) {
        super(id, presTypeId, title, startDate, endDate, timer, notSafe);
        this.path = path;
    }

    /**
     *
     * @param presTypeId
     * @param title
     * @param startDate
     * @param endDate
     * @param timer
     * @param notSafe
     * @param path
     */
    public Image(int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, String path) {
        super(presTypeId, title, startDate, endDate, timer, notSafe);
        this.path = path;
    }

    /**
     *
     * @param id
     * @param img
     */
    public Image(int id, Image img) {
        this(id, img.getPresTypeId(), img.getTitle(), img.getStartDate(), img.getEndDate(), img.getTimer(), img.isNotSafe(), img.getPath());
    }

    /**
     * @return the path to get
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
