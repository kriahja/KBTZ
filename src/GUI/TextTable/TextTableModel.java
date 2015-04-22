/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.TextTable;

import Entities.Text;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author notandi
 */
public class TextTableModel extends AbstractTableModel
{

    public static final int NAME_COLUMN = 0;
    public static final int DISPLAY_COLUMN = 1;
    

    private final String[] headers =
    {
        "Title", "Display"
    };

    private final Class[] columnTypes =
    {
        String.class, Integer.class
    };
    
    private ArrayList<Text> texts;
   
    
    public TextTableModel(ArrayList<Text> initialTexts)
    {
        texts = initialTexts;

    }

    public TextTableModel()
    {
        texts = new ArrayList<>();
    }

    @Override
    public int getRowCount()
    {
        return texts.size();
    }

    @Override
    public int getColumnCount()
    {
        return headers.length;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        Text t = texts.get(row);
        switch (col)
        {
            case NAME_COLUMN:
                return t.getTitle();
            case DISPLAY_COLUMN:
                return t.getDisplayId();
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

    public Text getText(int row)
    {
        return texts.get(row);
    }

    public void setTextList(ArrayList<Text> txtList)
    {
        texts = txtList;
        fireTableDataChanged();
    } 
}
