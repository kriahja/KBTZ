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
public class TextDBManager {

    private final DBConnectionManager cm;

    private static TextDBManager instance = null;

    public static TextDBManager getInstance() throws IOException {
        if (instance == null) {
            instance = new TextDBManager();
        }
        return instance;
    }

    private TextDBManager() throws IOException {
        cm = DBConnectionManager.getInstance();
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
}
