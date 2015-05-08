/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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

    public DisplayCtrl(int presId, int dispId)
    {
        this.presId = presId;
        this.dispId = dispId;
    }

    public DisplayCtrl(String presTitle, String presType, String screenName, boolean disable)
    {
        this.presTitle = presTitle;
        this.presType = presType;
        this.screenName = screenName;
        this.disable = disable;
        
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

}
