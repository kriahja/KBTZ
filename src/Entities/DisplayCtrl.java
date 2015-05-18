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
public class DisplayCtrl
{

    private Presentation pres;
    private Display disp;

    private int presId;
    private int dispId;

    private String presTitle;
    private String presType;
    private String screenName;
    private boolean disable;
    
    private Date startDate;
    private Date endDate;
    
    private Double timer;

    public DisplayCtrl(int presId, int dispId)
    {
        this.presId = presId;
        this.dispId = dispId;
    }

    public DisplayCtrl(String presTitle, String presType, String screenName, boolean disable, int presId, int dispId)
    {
        this.presTitle = presTitle;
        this.presType = presType;
        this.screenName = screenName;
        this.disable = disable;
        this.presId = presId;
        this.dispId = dispId;
        
    }
    
    public DisplayCtrl(Date startDate, Date endDate, Double timer)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.timer = timer;
    }

    /**
     * @return the presId
     */
    public int getPresId()
    {
        return presId;
    }

    /**
     * @return the dispId
     */
    public int getDispId()
    {
        return dispId;
    }

    /**
     * @return the screenName
     */
    public String getScreenName()
    {
        return screenName;
    }

    /**
     * @param screenName the screenName to set
     */
    public void setScreenName(String screenName)
    {
        this.screenName = screenName;
    }

    /**
     * @return the presTitle
     */
    public String getPresTitle()
    {
        return presTitle;
    }

    /**
     * @param presTitle the presTitle to set
     */
    public void setPresTitle(String presTitle)
    {
        this.presTitle = presTitle;
    }

    /**
     * @return the presType
     */
    public String getPresType()
    {
        return presType;
    }

    /**
     * @param presType the presType to set
     */
    public void setPresType(String presType)
    {
        this.presType = presType;
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
     * @return the pres
     */
    public Presentation getPres()
    {
        return pres;
    }

    /**
     * @param pres the pres to set
     */
    public void setPres(Presentation pres)
    {
        this.pres = pres;
    }

    /**
     * @return the timer
     */
    public Double getTimer()
    {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(Double timer)
    {
        this.timer = timer;
    }

}
