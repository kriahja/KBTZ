/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.ImageTable;

import Entities.Image;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author notandi
 */
public class ImageTableModel extends AbstractTableModel
{

    public static final int NAME_COLUMN = 0;
    public static final int FOLDER_COLUMN = 1;
    

    private final String[] headers =
    {
        "Title", "Folder"
    };

    private final Class[] columnTypes =
    {
        String.class, Integer.class
    };
    
    private ArrayList<Image> images;
   
    
    public ImageTableModel(ArrayList<Image> initialImages)
    {
        images = initialImages;

    }

    public ImageTableModel()
    {
        images = new ArrayList<>();
    }

    @Override
    public int getRowCount()
    {
        return images.size();
    }

    @Override
    public int getColumnCount()
    {
        return headers.length;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        Image t = images.get(row);
        switch (col)
        {
            case NAME_COLUMN:
                return t.getTitle();
            case FOLDER_COLUMN:
                return t.getPath();
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

    public Image getImageByRow(int row)
    {
        return images.get(row);
    }

    public void setImageList(ArrayList<Image> txtList)
    {
        images = txtList;
        fireTableDataChanged();
    } 
}
