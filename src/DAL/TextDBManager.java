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
        if (instance == null)
        {
            instance = new TextDBManager();
        }
        return instance;
    }

    /**
     * readAll Selects all text from the Presentation and uses the
     * text.presentationID, gets the information about the length, start-end
     * time, etc.
     *
     * @return txtList.
     *
     */
    public ArrayList<Text> readAll() throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "Select Presentation.* , Text.Text from Presentation, Text"
                    + " where Presentation.ID = Text.PresentationId";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }
            return txtList;
        }
    }

    /**
     * getOneText selects one text and gets the information about the length,
     * start-end time, etc..
     *
     * @returns new text.
     *
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
        boolean disable = rs.getBoolean("Disable");

        String text = rs.getString("Text");

//        String depName = rs.getString("Name");
        return new Text(id, presTypeId, title, startDate, endDate, timer, notSafe, text);
    }

    /**
     * readByTitle Reads all the TextPresentation based on the Title.
     *
     */
    public Text readByTitle(String title) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Title = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneText(rs);
            }

        }
        return null;
    }

    /**
     * readById Reads all the TextPresentation by ID.
     *
     * @return null.
     *
     */
    public Text readById(int id) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Presentation.ID = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getOneText(rs);
            }
        }
        return null;
    }

    /**
     * readByNotSafe Reads all the TextPresentation that are marked notSafe.
     *
     * @return txtList
     *
     */
    public ArrayList<Text> readByNotSafe(boolean safe) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "SELECT Presentation.* , Text.Text FROM Presentation, Text "
                    + "WHERE Presentation.NotSafe = ? and Presentation.ID = Text.PresentationId";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, safe);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }

            return txtList;

        }
    }

    /**
     * createText, creates the Text presentation. puts in the presTypeId, Title,
     * Start-End Date, Timer and NotSafe.
     *
     * @return new Text
     */
    public Text createText(Text txt) throws SQLException
    {

        try (Connection con = cm.getConnection())
        {

            String sql = "Begin TRANSACTION;\n"
                    + " Insert INTO Presentation\n"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)\n"
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
            ps.setBoolean(7, txt.isDisable());

            ps.setString(8, txt.getText());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to add pres.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);  // first column in keys resultset

            return new Text(id, txt);

        }
    }

    /**
     * delete, deletes the text presentation by ID
     */
    public void delete(int id) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
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
     * update, updates the text(title, start-end date, timer, notsafe) by the
     * ID.
     */
    public void update(Text txt) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            String sql = " begin transaction\n"
                    + " update Text set Text = ? where PresentationId = ?\n"
                    + " update Presentation set  Title = ?, StartDate = ?, EndDate = ?, Timer = ?, NotSafe = ?, Disable = ? where ID = ?\n"
                    + " Commit ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txt.getText());
            ps.setInt(2, txt.getId());

            ps.setString(3, txt.getTitle());

            ps.setDate(4, txt.getStartDate());
            ps.setDate(5, txt.getEndDate());
            ps.setDouble(6, txt.getTimer());

            ps.setBoolean(7, txt.isNotSafe());
            ps.setBoolean(8, txt.isDisable());

            ps.setInt(9, txt.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to Update text.");
            }

        }

    }

}
