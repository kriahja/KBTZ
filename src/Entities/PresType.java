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
public class PresType {

    private int id;
    private String type;

    /**
     * This is the constructor.
     *
     * @param id for the specific presentation type
     * @param type for the presType
     */
    public PresType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * This is a helper constructor.
     *
     * @param id for a specific presentation type
     * @param pType presentation type
     */
    public PresType(int id, PresType pType) {
        this(id, pType.getType());
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
