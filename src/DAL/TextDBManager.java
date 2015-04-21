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
}
