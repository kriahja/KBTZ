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
public abstract class Presentation
{

    private PresType pType;

    private int id;
    private int presTypeId;
    private String title;
    private Date startDate;
    private Date endDate;
    private double timer;
    private boolean notSafe;
    private boolean disable;   
    /**
     *
     * @param id auto generated id for a specific  
     * @param presTypeId this determents the type of a specific presentation
     * @param title title for the specific presentation
     * @param startDate start Date for a specific presentation
     * @param endDate end Date for a specific presentation
     * @param timer timer for a specific presentation
     * @param notSafe mark for not safe(not costumerFriendly) for a specific text presentation
     */
 

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

    /**
     *
     * @param pType
     */
    public Presentation(PresType pType)
    {
        this.pType = pType;
        presTypeId = pType.getId();
    }

    /**
* @param presTypeId this determents the type of a specific presentation
     * @param title title for the specific presentation
     * @param startDate start Date for a specific presentation
     * @param endDate end Date for a specific presentation
     * @param timer timer for a specific presentation
     * @param notSafe mark for not safe(not costumerFriendly) for a specific text presentation
     */
    public Presentation(int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe)
    {

        this.presTypeId = presTypeId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.notSafe = notSafe;
    }

    public Presentation(int id, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, boolean disable)
    {
        this.id = id;
        this.presTypeId = presTypeId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
        this.notSafe = notSafe;
        this.disable = disable;
    }

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

    /**
     * @return the disable
     */
    public boolean isDisable()
    {
        return disable;
    }

    /**
     * @param disable the disable to set
     */
    public void setDisable(boolean disable)
    {
        this.disable = disable;
    }
}
