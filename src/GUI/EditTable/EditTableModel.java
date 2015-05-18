/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.EditTable;

import BLL.DisplayCtrlManager;
import Entities.DisplayCtrl;
import Entities.Presentation;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author notandi
 */
public class EditTableModel extends AbstractTableModel
{

    public static final int TITLE_COLUMN = 0;
    public static final int TYPE_COLUMN = 1;
    public static final int DISPLAY_COLUMN = 2;
    public static final int START_COLUMN = 3;
    public static final int END_COLUMN = 4;
    public static final int TIMER_COLUMN = 5;

    DisplayCtrlManager dcMgr;
    Presentation present;

    private final String[] headers =
    {
        "Title", "Type", "Display", "Start date", "End date", "Minutes"
    };

    private final Class[] columnTypes =
    {
        String.class, String.class, String.class, Date.class, Date.class, Double.class
    };

    private ArrayList<DisplayCtrl> pres;

    public EditTableModel(ArrayList<DisplayCtrl> initialPresentations)
    {
        pres = initialPresentations;

    }

    public EditTableModel()
    {
        pres = new ArrayList<>();
        dcMgr = DisplayCtrlManager.getInstance();

    }

    @Override
    public int getRowCount()
    {
        return pres.size();
    }

    @Override
    public int getColumnCount()
    {
        return headers.length;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        DisplayCtrl dc = pres.get(row);

        switch (col)
        {
            case TITLE_COLUMN:
                return dc.getPresTitle();
            case TYPE_COLUMN:
                return dc.getPresType();
            case DISPLAY_COLUMN:
                return dc.getScreenName();
            case START_COLUMN:
                return dc.getStartDate();
            case END_COLUMN:
                return dc.getEndDate();
            case TIMER_COLUMN:
                return dc.getTimer();
        }
        return null;
    }

    @Override
    public String getColumnName(int col)
    {
        return headers[col];
    }

    @Override
    public Class<?> getColumnClass(int col)
    {
        return columnTypes[col];
    }

    public DisplayCtrl getDisplayCtrlByRow(int row)
    {
        return pres.get(row);
    }

    public void setDisplayCtrlList(ArrayList<DisplayCtrl> txtList)
    {
        pres = txtList;
        fireTableDataChanged();
    }

}
