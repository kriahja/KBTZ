/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Entities.DisplayCtrl.DisplayCtrl;
import java.sql.Date;

/**
 *
 * @author notandi
 */
public abstract class Presentation extends DisplayCtrl
{

    private PresType pType;

    private int id;
    private int presTypeId;
    private String title;
    private Date startDate;
    private Date endDate;
    private double timer;
    private boolean notSafe;

    public Presentation(int id, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe)
    {
        this.id = id;
        this.presTypeId = presTypeId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.notSafe = notSafe;
    }
    // without DisplayId.
//    public Presentation(int id, Presentation pres)
//    {
//        this(id, pres.getPresTypeId(), pres.getTitle(), pres.getStartDate(), pres.getEndDate(), pres.getTimer(), pres.isNotSafe());
//    }

    public Presentation(PresType pType)
    {
        this.pType = pType;
        presTypeId = pType.getId();
    }

    public Presentation(int id, int dispId, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe)
    {
        this.id = id;
        setDisplayId(dispId);
        this.presTypeId = presTypeId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.notSafe = notSafe;
    }
    //With displayId.
    public Presentation(int id, Presentation pres)
    {
        this(id, pres.getDisplayId(), pres.getPresTypeId(), pres.getTitle(), pres.getStartDate(), pres.getEndDate(), pres.getTimer(), pres.isNotSafe());
    }
    

    abstract protected void doShow();
    
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
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
     * @return the endDate
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
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
     * @return the presTypeId
     */
    public int getPresTypeId()
    {
        return presTypeId;
    }

    /**
     * @param presTypeId
     */
    public void setPresTypeId(int presTypeId)
    {
        this.presTypeId = presTypeId;
    }
}
