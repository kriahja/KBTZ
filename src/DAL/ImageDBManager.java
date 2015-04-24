/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.Image;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ArrayList<Image> readAll() throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "Select * from Image";
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

    private Image getOneImage(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("ID");
        String title = rs.getString("Title");
        String image = rs.getString("Path");
        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");
        int displayId = rs.getInt("DisplayId");
        boolean notSafe = rs.getBoolean("NotSafe");
        int priorityId = rs.getInt("PriorityId");

//        String depName = rs.getString("Name");
        return new Image(id, title, image, startDate, endDate, timer, displayId, notSafe, priorityId);
    }

    public Image readByTitle(String title)
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Image WHERE Title = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneImage(rs);
            }
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image name.");
        }
        return null;
    }
    
        public Image readByPath(String path)
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Image WHERE Path = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, path);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneImage(rs);
            }
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image name.");
        }
        return null;
    }

    public Image readById(int id)
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM Image WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneImage(rs);
            }
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image id.");
        }
        return null;
    }

    public ArrayList<Image> readByPriorityId(int id)
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "SELECT * FROM Image WHERE PriorityId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Image img = getOneImage(rs);
                imgList.add(img);
            }

            return imgList;
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image priority.");
        }
    }

    public ArrayList<Image> readByDisplayId(int id)
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "SELECT * FROM Image WHERE DisplayId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Image img = getOneImage(rs);
                imgList.add(img);
            }

            return imgList;
        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image displayid.");
        }
    }

    public ArrayList<Image> readByNotSafe(boolean safe)
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Image> imgList = new ArrayList<>();
            String sql = "SELECT * FROM Image WHERE NotSafe = ?";
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
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to read Image safe");
        }
    }

    public Image createImage(Image img) throws SQLException
    {

        try (Connection con = cm.getConnection())
        {

            String sql = "Insert into Image(Title, Path, StartDate, EndDate, Timer, DisplayId, NotSafe, PriorityId)"
                    + "Values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, img.getTitle());
            ps.setString(2, img.getPath());
            ps.setDate(3, img.getStartDate());
            ps.setDate(4, img.getEndDate());
            ps.setDouble(5, img.getTimer());
            ps.setInt(6, img.getDisplayId());
            ps.setBoolean(7, img.isNotSafe());
            ps.setInt(8, img.getPriorityId());

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

    public void delete(int id)
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM Image WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        }
        catch (SQLException ex)
        {
            throw new BivExceptions("Unable to remove Image.");
        }
    }

    public void update(Image img) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "UPDATE Image SET Title = ?, Path = ?, StartDate = ?, EndDate = ?, Timer = ?, DisplayId = ?, "
                    + " NotSafe = ?, PriorityId = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, img.getTitle());
            ps.setString(2, img.getPath());
            ps.setDate(3, img.getStartDate());
            ps.setDate(4, img.getEndDate());
            ps.setDouble(5, img.getTimer());
            ps.setInt(6, img.getDisplayId());
            ps.setBoolean(7, img.isNotSafe());
            ps.setInt(8, img.getPriorityId());
            ps.setInt(9, img.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to Update image.");
            }

        }

    }
}
