/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;




/**
 *
 * @author notandi
 * 
 */
public class Display
{
    private int id;
    private String screenName;
    
    
    /**
     *@param id for the display
     * @param screenName for the Display.
     */
    public Display(int id, String screenName)
    {
        this.id = id;
        this.screenName = screenName;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
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
}
