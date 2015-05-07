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
     *@param Image ArrayList read all the ImagePresentations. 
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
     * @param rs results of the query.
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
        String path = rs.getString("Path");

//        String depName = rs.getString("Name");
        return new Image(id, presTypeId, title, startDate, endDate, timer, notSafe, path);
    }
    /**
     * @param title title of the image is used to locate a specific ImagePresentation. 
     * @return getOneImage or null.
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
     * @param id ImagePresentations are read by id.
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
     * @param safe Lists the ImagePresentations by notSafe.
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
     * @param img creates ImagesPresentation. 
     * @return Image
     */

    public Image createImage(Image img) throws SQLException
    {

        try (Connection con = cm.getConnection())
        {

            String sql = "Begin TRANSACTION;\n"
                    + " Insert INTO Presentation\n"
                    + " VALUES (?, ?, ?, ?, ?, ?)\n"
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

            ps.setString(7, img.getPath());

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
     * @param id deletes the image based on the imagePresentationId. 
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
      * @param img updates the imagePresentations based on the presentationID.
     */

    public void update(Image img) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = " begin transaction\n"
                    + " update Image set Path = ? where PresentationId = ?\n"
                    + " update Presentation set  Title = ?, StartDate = ?, EndDate = ?, Timer = ?, NotSafe = ? where ID = ?\n"
                    + " Commit ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, img.getPath());
            ps.setInt(2, img.getId());
            
            ps.setString(3, img.getTitle());
            
            ps.setDate(4, img.getStartDate());
            ps.setDate(5, img.getEndDate());
            ps.setDouble(6, img.getTimer());
            
            ps.setBoolean(7, img.isNotSafe());
            
            ps.setInt(8, img.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to Update image.");
            }

        }

    }

}
