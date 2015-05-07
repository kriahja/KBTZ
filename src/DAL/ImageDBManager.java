/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author a.tamas
 */
public class ImageDBManager
{

    private final DBConnectionManager cm;

    private static ImageDBManager instance = null;

    private ImageDBManager() throws IOException
    {
        cm = DBConnectionManager.getInstance();

    }

    public static ImageDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new ImageDBManager();
        }
        return instance;
    }
     /**
     * readAll Reads all the ImagePresentation 
     * @return imgList
     */

    public ArrayList<Image> readAll() throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "Select Presentation.* , Image.Path from Presentation, Image"
                    + " where Presentation.ID = Image.PresentationId";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                Image img = getOneImage(rs);
                imgList.add(img);
            }
            return imgList;
        }
    }
    /**
     * getOneImage selects one text and gets the information about the length, start-end time, etc..
     * @return imgList
     */

    private Image getOneImage(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("ID");
        int presTypeId = rs.getInt("PresTypeId");
        String title = rs.getString("Title");

        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");
        boolean notSafe = rs.getBoolean("NotSafe");
        boolean disable = rs.getBoolean("Disable");
        
        String path = rs.getString("Path");

//        String depName = rs.getString("Name");
        return new Image(id, presTypeId, title, startDate, endDate, timer, notSafe, path);
    }
    /**
     * readByTitle Reads all the ImagePresentation based on the Title.
     * 
     */

    public Image readByTitle(String title) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT Presentation.* , Image.Path FROM Presentation, Image "
                    + "WHERE Title = ? and Presentation.ID = Image.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneImage(rs);
            }
        }

        return null;
    }

//    public Image readByPath(String path)
//    {
//        try (Connection con = cm.getConnection())
//        {
//            String sql = "SELECT * FROM Image WHERE Path = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, path);
//
//            ResultSet rs = ps.executeQuery();
//            if (rs.next())
//            {
//                return getOneImage(rs);
//            }
//        }
//        catch (SQLException ex)
//        {
//            throw new BivExceptions("Unable to read Image name.");
//        }
//        return null;
//    }
    /**
     * readById Reads all the ImagePresentation based on the Id.
     * @return null
     */
    public Image readById(int id) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT Presentation.* , Image.Path FROM Presentation, Image "
                    + "WHERE Presentation.ID = ? and Presentation.ID = Image.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneImage(rs);
            }
        }

        return null;
    }
    /**
     * readByNotSafe Reads all the ImagesPresentation based on if they are marked NotSafe.
     * @return imgList.
     */

    public ArrayList<Image> readByNotSafe(boolean safe) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "SELECT Presentation.* , Image.Path FROM Presentation, Image "
                    + "WHERE Presentation.NotSafe = ? and Presentation.ID = Image.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, safe);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Image img = getOneImage(rs);
                imgList.add(img);
            }

            return imgList;
        }

    }
       /**
     * createImage creates the ImagePresentation based on what the user puts in about  timer, end-start time etc.
     * @return Image.
     */

    public Image createImage(Image img) throws SQLException
    {

        try (Connection con = cm.getConnection())
        {

            String sql = "Begin TRANSACTION;\n"
                    + " Insert INTO Presentation\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)\n"
                    + " Insert INTO Image\n"
                    + " VALUES (?,  SCOPE_IDENTITY())\n"
                    + "COMMIT";

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, img.getPresTypeId());
            ps.setString(2, img.getTitle());
          
            ps.setDate(3, img.getStartDate());
            ps.setDate(4, img.getEndDate());
            ps.setDouble(5, img.getTimer());
            ps.setBoolean(6, img.isNotSafe());
            ps.setBoolean(7, img.isDisable());

            ps.setString(8, img.getPath());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to add image.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);  // first column in keys resultset

            return new Image(id, img);

        }
    }
     /**
     * delete deletes the Image based on the imagepresentationId or the presentationId is something specific.
     */

    public void delete(int id) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "BEGIN Transaction;\n"
                    + "  DELETE FROM Image WHERE Image.PresentationId = ?\n"
                    + "  DELETE FROM Presentation WHERE Presentation.ID = ?\n"
                    + " COMMIT";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            

            ps.executeUpdate();

        }
    }
     /**
     * update, updates the ImagePresentation based on the presentationID and updates what the user wants to change.
     */

    public void update(Image img) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = " begin transaction\n"
                    + " update Image set Path = ? where PresentationId = ?\n"
                    + " update Presentation set  Title = ?, StartDate = ?, EndDate = ?, Timer = ?, NotSafe = ?, Disable = ? where ID = ?\n"
                    + " Commit ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, img.getPath());
            ps.setInt(2, img.getId());
            
            ps.setString(3, img.getTitle());
            
            ps.setDate(4, img.getStartDate());
            ps.setDate(5, img.getEndDate());
            ps.setDouble(6, img.getTimer());
            
            ps.setBoolean(7, img.isNotSafe());
            ps.setBoolean(8, img.isDisable());
            
            ps.setInt(9, img.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to Update image.");
            }

        }

    }

}
