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
public class Text
{

    Priority pri;
    Display disp;

    private int id;
    private int displayId;
    private int priorityId;
    private double timer;
    private Date startDate;
    private Date endDate;
    private boolean notSafe;
    private String text;
    private String title;

    public Text(int id, String title, String text, Date startDate, Date endDate, double timer, int displayId, boolean notSafe, int priorityId)
    {
        this.id = id;
        this.displayId = displayId;
        this.priorityId = priorityId;
        this.timer = timer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notSafe = notSafe;
        this.text = text;
        this.title = title;
    }

    public Text(String title, String text, Date startDate, Date endDate, double timer, int displayId, boolean notSafe, int priorityId)
    {

        this.displayId = displayId;
        this.priorityId = priorityId;
        this.timer = timer;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Text(int id, Text txt)
    {
        this(id, txt.getTitle(), txt.getText(), txt.getStartDate(), txt.getEndDate(), txt.getTimer(), txt.getDisplayId(), txt.isNotSafe(), txt.getPriorityId());
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
        this.endDate = endDate;
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
