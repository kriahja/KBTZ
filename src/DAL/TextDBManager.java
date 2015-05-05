/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import BE.Text;
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
public class TextDBManager
{

    private final DBConnectionManager cm;

    private static TextDBManager instance = null;

    private TextDBManager() throws IOException
    {
        cm = DBConnectionManager.getInstance();

    }

    public static TextDBManager getInstance() throws IOException
    {
        if (instance == null) {
            instance = new TextDBManager();
        }
        return instance;
    }

    public ArrayList<Text> readAll() throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "Select Presentation.* , Text.Text from Presentation, Text"
                    + " where Presentation.ID = Text.PresentationId";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }
            return txtList;
        }
    }

    private Text getOneText(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("ID");
        int presTypeId = rs.getInt("PresTypeId");
        String title = rs.getString("Title");

        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");

        boolean notSafe = rs.getBoolean("NotSafe");

        String text = rs.getString("Text");

//        String depName = rs.getString("Name");
        return new Text(id, presTypeId, title, startDate, endDate, timer, notSafe, text);
    }

    public Text readByTitle(String title) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Title = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getOneText(rs);
            }

        }
        return null;
    }

    public Text readById(int id) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Presentation.ID = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getOneText(rs);
            }
        }
        return null;
    }

    public ArrayList<Text> readByNotSafe(boolean safe) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Presentation.NotSafe = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, safe);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }

            return txtList;

        }
    }
/*
    public Text createText(Text txt) throws SQLException
    {

        try (Connection con = cm.getConnection()) {

            String sql = "Begin TRANSACTION;"
                    + " Insert INTO Presentation VALUES (?, ?, ?, ?, ?, ?)"
                    + " INSERT INTO Text VALUES (? , ?)"
                    + " COMMIT";
//            String sql = "Insert into Presentation(PresTypeId, Title, StartDate, EndDate, Timer, NotSafe)"
            //                    + "Values (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, txt.getPresTypeId());
            ps.setString(2, txt.getTitle());
            ps.setDate(3, txt.getStartDate());
            ps.setDate(4, txt.getEndDate());
            ps.setDouble(5, txt.getTimer());
            ps.setBoolean(6, txt.isNotSafe());
            
            ps.setString(7, txt.getText());
            int idd = txt.getId();
            ps.setInt(8, idd);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to add text.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);  // first column in keys resultset

            return new Text(id, txt);

        }
    }
*/
    /*
    public void delete(int id)
    {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Text WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new BivExceptions("Unable to remove Text.");
        }
    }

    public void update(Text txt) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Text SET Title = ?, Text = ?, StartDate = ?, EndDate = ?, Timer = ?, DisplayId = ?, "
                    + " NotSafe = ?, PriorityId = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txt.getTitle());
            ps.setString(2, txt.getText());
            ps.setDate(3, txt.getStartDate());
            ps.setDate(4, txt.getEndDate());
            ps.setDouble(5, txt.getTimer());
            ps.setInt(6, txt.getDisplayId());
            ps.setBoolean(7, txt.isNotSafe());
            ps.setInt(8, txt.getPriorityId());
            ps.setInt(9, txt.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to Update text.");
            }

        }

    }
    */
}
