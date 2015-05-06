/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author notandi
 * this is the Presintation Type. Here we can get/set the Id and Type. 
 */
public class PresType
{
    private int id;
    private String type;
    /**
     * This is the constructor. 
     */
    public PresType(int id, String type)
    {
        this.id = id;
        this.type = type;
    }
    /**
     * This is a helper constructor. 
     */
    public PresType(int id, PresType pType)
    {
        this(id, pType.getType());
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

}
