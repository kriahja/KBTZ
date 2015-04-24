/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author Agnar
 */
public class Image
{

    private Priority pri;
    private Display disp;

    private int id;
    private String title;
    private String path;
    private Date startDate;
    private Date endDate;
    private double timer;
    private int displayId;
    private boolean notSafe;
    private int priorityId;

    public Image(int id, String title, String path, Date startDate, Date endDate, double timer, int displayId, boolean notSafe, int priorityId)
    {
        this.id = id;
        this.title = title;
        this.path = path;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.displayId = displayId;
        this.notSafe = notSafe;
        this.priorityId = priorityId;

    }

    public Image(String title, String path, Date startDate, Date endDate, double timer, int displayId, boolean notSafe, int priorityId)
    {
        this.title = title;
        this.path = path;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.displayId = displayId;
        this.notSafe = notSafe;
        this.priorityId = priorityId;

    }

    public Image(Priority pri, Display disp)
    {
        this.pri = pri;
        priorityId = pri.getId();
        this.disp = disp;
        displayId = disp.getId();
    }

    public Image(int id, Image img)
    {
        this(id, img.getTitle(), img.getPath(), img.getStartDate(), img.getEndDate(), img.getTimer(), img.getDisplayId(), img.isNotSafe(), img.getPriorityId());
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the displayId
     */
    public int getDisplayId()
    {
        return displayId;
    }

    /**
     * @return the priorityId
     */
    public int getPriorityId()
    {
        return priorityId;
    }

    /**
     * @return the timer
     */
    public double getTimer()
    {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(double timer)
    {
        this.timer = timer;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return the enddate
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param enddate the enddate to set
     */
    public void setEnddate(Date endDate)
    {
        this.setEndDate(endDate);
    }

    /**
     * @return the notSafe
     */
    public boolean isNotSafe()
    {
        return notSafe;
    }

    /**
     * @param notSafe the notSafe to set
     */
    public void setNotSafe(boolean notSafe)
    {
        this.notSafe = notSafe;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the pri
     */
    public Priority getPri()
    {
        return pri;
    }

    /**
     * @return the disp
     */
    public Display getDisp()
    {
        return disp;
    }

    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @param displayId the displayId to set
     */
    public void setDisplayId(int displayId)
    {
        this.displayId = displayId;
    }

    /**
     * @param priorityId the priorityId to set
     */
    public void setPriorityId(int priorityId)
    {
        this.priorityId = priorityId;
    }
}