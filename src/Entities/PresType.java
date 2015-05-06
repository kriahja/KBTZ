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
public class PresType
{
    private int id;
    private String type;

    public PresType(int id, String type)
    {
        this.id = id;
        this.type = type;
    }
    
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
