/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.Text;
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
    /**
     *@param Text ArrayList reads all the TextPresentations. 
     * @return txtList
     */

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
    /**
     * @param rs results of the query.
     * @return new Text
     */

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
    
    /**
     * @param title title of the Text is used to locate a specific TextPresentation. 
     * @return getOneText or null.
     */
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
     /**
     *@param id TextPresentation are read by ID.
     * @return  getOneText or null.
     * 
     */

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
     /**
      * @param safe Lists the TextPresentations by notSafe.
      * @return txtList
     **/

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
     /**
      * @param txt creates mew Text Presentations.
     * @return new Text
     */


    public Text createText(Text txt) throws SQLException
    {

        try (Connection con = cm.getConnection()) {

            String sql = "Begin TRANSACTION;\n"
                    + " Insert INTO Presentation\n"
                    + " VALUES (?, ?, ?, ?, ?, ?)\n"
                    + " Insert INTO [Text]\n"
                    + " VALUES (?,  SCOPE_IDENTITY())\n"
                    + "COMMIT";
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

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to add pres.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);  // first column in keys resultset

            return new Text(id, txt);

        }
    }
     /**
      * @param id deletes textPresentations by ID
     */

    public void delete(int id) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            String sql = "BEGIN Transaction;\n"
                    + "  DELETE FROM [Text] WHERE Text.PresentationId = ?\n"
                    + "  DELETE FROM Presentation WhERE Presentation.ID = ?\n"
                    + " COMMIT";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            ps.executeUpdate();

        }
    }
     /**
     * update, updates the text(title, start-end date, timer, notsafe) by the ID.
     */


    public void update(Text txt) throws SQLException
    {
        try (Connection con = cm.getConnection()) {
            String sql = " begin transaction\n"
                    + " update Text set Text = ? where PresentationId = ?\n"
                    + " update Presentation set  Title = ?, StartDate = ?, EndDate = ?, Timer = ?, NotSafe = ? where ID = ?\n"
                    + " Commit ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txt.getText());
            ps.setInt(2, txt.getId());
            
            ps.setString(3, txt.getTitle());
            
            ps.setDate(4, txt.getStartDate());
            ps.setDate(5, txt.getEndDate());
            ps.setDouble(6, txt.getTimer());

            ps.setBoolean(7, txt.isNotSafe());

            ps.setInt(8, txt.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to Update text.");
            }

        }

    }

}
