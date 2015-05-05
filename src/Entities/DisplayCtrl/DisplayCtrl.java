/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE.DisplayCtrl;

/**
 *
 * @author notandi
 */
public class DisplayCtrl implements IDisplayCtrl
{
    private int presId;
    private int dispId;

    @Override
    public int getPresentationId()
    {
        return presId;
    }

    @Override
    public void setPresentationId(int presId)
    {
        this.presId = presId;
    }

    @Override
    public int getDisplayId()
    {
        return dispId;
    }

    @Override
    public void setDisplayId(int dispId)
    {
        this.dispId = dispId;
    }
    
}
