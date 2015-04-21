/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author notandi
 */
public class Text
{
    Priority pri;
    Display disp;
    
    private int id; 
    private int displayId;
    private int priorityId;
    private double timer;
    private Date startDate; 
    private Date enddate;
    private boolean notSafe;
    private String text;
    private String title;

    public Text(int id, int displayId, int priorityId, double timer, Date startDate, Date enddate, boolean notSafe, String text, String title)
    {
        this.id = id;
        this.displayId = displayId;
        this.priorityId = priorityId;
        this.timer = timer;
        this.startDate = startDate;
        this.enddate = enddate;
        this.notSafe = notSafe;
        this.text = text;
        this.title = title;
    }
    
    public Text(Priority pri, Display disp)
    {
        this.pri = pri;
        priorityId = pri.getId();
        this.disp = disp;
        displayId = disp.getId();
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
    public Date getEnddate()
    {
        return enddate;
    }

    /**
     * @param enddate the enddate to set
     */
    public void setEnddate(Date enddate)
    {
        this.enddate = enddate;
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
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text)
    {
        this.text = text;
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
}
