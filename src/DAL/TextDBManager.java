/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.Text;
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
public class TextDBManager {

    private final DBConnectionManager cm;

    private static TextDBManager instance = null;

    private TextDBManager() throws IOException {
        cm = DBConnectionManager.getInstance();

    }

    public static TextDBManager getInstance() throws IOException {
        if (instance == null) {
            instance = new TextDBManager();
        }
        return instance;
    }

    public ArrayList<Text> readAll() {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "Select * from Text";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }
            return txtList;
        } catch (SQLException ex) {

            throw new BivExceptions("Unable to read all Text data.");
        }
    }

    private Text getOneText(ResultSet rs) throws SQLException {
        int id = rs.getInt("ID");
        String title = rs.getString("Title");
        String text = rs.getString("Text");
        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");
        int displayId = rs.getInt("DisplayId");
        boolean notSafe = rs.getBoolean("NotSafe");
        int priorityId = rs.getInt("PriorityId");

//        String depName = rs.getString("Name");
        return new Text(id, title, text, startDate, endDate, timer, displayId, notSafe, priorityId);
    }

    public void tamas() {

    }

    public Text readByTitle(String title) {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM Text WHERE Title = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getOneText(rs);
            }
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read Text name.");
        }
        return null;
    }

    public Text readById(int id) {
        try (Connection con = cm.getConnection()) {
            String sql = "SELECT * FROM Text WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getOneText(rs);
            }
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read Text id.");
        }
        return null;
    }

    public ArrayList<Text> readByPriorityId(int id) {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "SELECT * FROM Text WHERE PriorityId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }

            return txtList;
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read Text priority.");
        }
    }

    public ArrayList<Text> readByDisplayId(int id) {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "SELECT * FROM Text WHERE DisplayId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }

            return txtList;
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read Text displayid.");
        }
    }

    public ArrayList<Text> readByNotSafe(boolean safe) {
        try (Connection con = cm.getConnection()) {
            ArrayList<Text> txtList = new ArrayList<>();
            String sql = "SELECT * FROM Text WHERE NotSafe = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, safe);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Text txt = getOneText(rs);
                txtList.add(txt);
            }

            return txtList;
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to read Text safe");
        }
    }

    public Text createText(Text txt) throws SQLException {

        try (Connection con = cm.getConnection()) {
            
            String sql = "Insert into Text(Title, [Text], StartDate, EndDate, Timer, DisplayId, NotSafe, PriorityId)"
                    + "Values (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, txt.getTitle());
            ps.setString(2, txt.getText());
            ps.setDate(3, txt.getStartDate());
            ps.setDate(4, txt.getEndDate());
            ps.setDouble(5, txt.getTimer());
            ps.setInt(6, txt.getDisplayId());
            ps.setBoolean(7, txt.isNotSafe());
            ps.setInt(8, txt.getPriorityId());

             int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new SQLException("Unable to add text.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);  // first column in keys resultset

            return new Text(id, txt);

        }
    }
    

    public void delete(int id) {
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Text WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new BivExceptions("Unable to remove Text.");
        }
    }

    public void update(Text txt) {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Text SET Title = ?, Text = ?, StartDate = ?, EndDate = ?, Timer = ?, DisplayId, "
                    + " NotSafe = ? , PriorityId = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txt.getTitle());
            ps.setString(2, txt.getText());
            ps.setDate(3, txt.getStartDate());
            ps.setDate(4, txt.getEndDate());
            ps.setDouble(4, txt.getTimer());
            ps.setInt(5, txt.getDisplayId());
            ps.setBoolean(6, txt.isNotSafe());
            ps.setInt(7, txt.getPriorityId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new BivExceptions("Unable to perform advanced update to Text data.");
        }
    }
}
