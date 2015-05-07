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
public class Text extends Presentation
{

    private String text;

    /**
     *
     * @param id auto generated id for a specific text text presentation.
     * @param presTypeId this id determents the type of the text.
     * @param title title for a specific text presentation
     * @param startDate startDate for a specific text presentation
     * @param endDate endDate for a specific presentation
     * @param timer timer for a specific text presentation
     * @param notSafe mark for not safe(not costumerFriendly) for a specific
     * text presentation
     * @param text text for a specific text presentation
     */
    public Text(int id, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, String text)
    {
        super(id, presTypeId, title, startDate, endDate, timer, notSafe);
        this.text = text;
    }

    public Text(int id, int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, boolean disable, String text)
    {
        super(id, presTypeId, title, startDate, endDate, timer, notSafe, disable);
        this.text = text;
    }

    /**
     * @param id
     * @param txt
     */
    public Text(int id, Text txt)
    {
        this(id, txt.getPresTypeId(), txt.getTitle(), txt.getStartDate(), txt.getEndDate(), txt.getTimer(), txt.isNotSafe(), txt.getText());
    }

    /**
     *
     * @param presTypeId this id determents the type of the presentation.
     * @param title title for a specific presentation
     * @param startDate startDate for a specific presentation
     * @param endDate endDate for a specific presentation
     * @param timer timer for a specific presentation
     * @param notSafe mark for not safe(not costumerFriendly) for a specific
     * presentation
     * @param text text for a specific presentation
     */
    public Text(int presTypeId, String title, Date startDate, Date endDate, double timer, boolean notSafe, String text)
    {
        super(presTypeId, title, startDate, endDate, timer, notSafe);
        this.text = text;
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

}
