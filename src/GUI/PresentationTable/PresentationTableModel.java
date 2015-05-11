/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.PresentationTable;

import BLL.DisplayCtrlManager;
import Entities.DisplayCtrl;
import Entities.Presentation;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author notandi
 */
public class PresentationTableModel extends AbstractTableModel
{

    public static final int TITLE_COLUMN = 0;
    public static final int TYPE_COLUMN = 1;
    public static final int DISAPLY_COLUMN = 2;
    public static final int DISABLE_COLUMN = 3;

    DisplayCtrlManager dcMgr;
    Presentation present;

    private final String[] headers =
    {
        "Title", "Type", "Display", "Disable / Enable"
    };

    private final Class[] columnTypes =
    {
        String.class, String.class, String.class, Boolean.class
    };

    private ArrayList<DisplayCtrl> pres;

    public PresentationTableModel(ArrayList<DisplayCtrl> initialPresentations)
    {
        pres = initialPresentations;

    }

    public PresentationTableModel()
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
            case DISAPLY_COLUMN:
                return dc.getScreenName();
            case DISABLE_COLUMN:
                return dc.isDisable();
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

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return (col != TITLE_COLUMN && col != TYPE_COLUMN && col != DISAPLY_COLUMN);
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        DisplayCtrl dc = pres.get(row);
        
        switch (col)
        {

            case DISABLE_COLUMN:
                dc.setDisable(Boolean.valueOf(value.toString()));
                break;
        }
        // Fire this event to trigger the tableModelListener.
//        fireTableRowsUpdated(row, row);
        fireTableCellUpdated(row, col);

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
