/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.DisplayCtrl;
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
 * @author notandi
 */
public class DisplayCtrlDBManager {

    private final DBConnectionManager cm;

    private static DisplayCtrlDBManager instance = null;

    private DisplayCtrlDBManager() throws IOException {
        cm = DBConnectionManager.getInstance();

    }

    /**
     *@return instance
     * @throws IOException
     */
    public static DisplayCtrlDBManager getInstance() throws IOException {
        if (instance == null) {
            instance = new DisplayCtrlDBManager();
        }
        return instance;
    }

    /**
     *
     * @param rs
     * @return dc
     * @throws SQLException
     */
    public DisplayCtrl getOnedc(ResultSet rs) throws SQLException {
        String presTitle = rs.getString("Title");
        String presType = rs.getString("Type");
        String screenName = rs.getString("ScreenName");
        boolean disable = rs.getBoolean("Disable");
        int presId = rs.getInt("PresentationId");
        int dispId = rs.getInt("DisplayId");

        DisplayCtrl dc = new DisplayCtrl(presTitle, presType, screenName, disable, presId, dispId);

        return dc;
    }

    /**
     *
     * @param rs
     * @return dc
     * @throws SQLException
     */
    public DisplayCtrl getOnedcEditPres(ResultSet rs) throws SQLException {
        String presTitle = rs.getString("Title");
        String presType = rs.getString("Type");
        String screenName = rs.getString("ScreenName");
        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");
        int presId = rs.getInt("PresentationId");
        int dispId = rs.getInt("DisplayId");
        boolean disable = rs.getBoolean("Disable");

        DisplayCtrl dc = new DisplayCtrl(presTitle, presType, screenName, startDate, endDate, timer, presId, dispId, disable);

        return dc;
    }

    /**
     * @return dcList 
     * @throws SQLException
     */
    public ArrayList<DisplayCtrl> readAllPres() throws SQLException {
        try (Connection con = cm.getConnection()) {
            ArrayList<DisplayCtrl> dcList = new ArrayList<>();
            String sql = "Select Presentation.Title, PresType.[Type], DisplayCtrl.[Disable], Display.ScreenName , DisplayCtrl.PresentationId , DisplayCtrl.DisplayId\n"
                    + "                    from DisplayCtrl\n"
                    + "                    Inner JOIN Presentation ON DisplayCtrl.PresentationId = Presentation.ID\n"
                    + "                    Inner JOIN PresType ON Presentation.PresTypeId = PresType.ID\n"
                    + "                    Inner JOIN Display ON DisplayCtrl.DisplayId = Display.ID";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                DisplayCtrl dc = getOnedc(rs);
                dcList.add(dc);
            }
            return dcList;
        }
    }

    /**
     *@return dcList
     * @throws SQLException
     */
    public ArrayList<DisplayCtrl> readAllEditPres() throws SQLException {
        try (Connection con = cm.getConnection()) {
            ArrayList<DisplayCtrl> dcList = new ArrayList<>();
            String sql = "Select Presentation.Title, PresType.[Type], Display.ScreenName, Presentation.StartDate, "
                    + "Presentation.EndDate, Presentation.Timer, DisplayCtrl.PresentationId, DisplayCtrl.DisplayId, DisplayCtrl.[Disable]\n"
                    + "                    from DisplayCtrl\n"
                    + "                    Inner JOIN Presentation ON DisplayCtrl.PresentationId = Presentation.ID\n"
                    + "                    Inner JOIN PresType ON Presentation.PresTypeId = PresType.ID\n"
                    + "                    Inner JOIN Display ON DisplayCtrl.DisplayId = Display.ID";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                DisplayCtrl dc = getOnedcEditPres(rs);
                dcList.add(dc);
            }
            return dcList;
        }
    }

    /**
     * @param dc
     * @throws java.sql.SQLException
     */
    public void updateDisable(DisplayCtrl dc) throws SQLException {
        try (Connection con = cm.getConnection()) {
            System.out.println(dc.isDisable() + "" + dc.getPresId());
            String sql = "update DisplayCtrl set Disable = ? where PresentationId = ? and DisplayId = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, dc.isDisable());
            ps.setInt(2, dc.getPresId());
            ps.setInt(3, dc.getDispId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to Update Dispctrl.");
            }

        }
    }

    /**
     *
     * @param presId
     * @param dispId
     * @throws SQLException
     */
    public void create(int presId, int dispId) throws SQLException {

        try (Connection con = cm.getConnection()) {

            String sql = "insert into DisplayCtrl (DisplayId, PresentationId, Disable) values (?, ?, 'false') ";

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dispId);
            ps.setInt(2, presId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new BivExceptions("Unable to add dispctrl.");
            }

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            new DisplayCtrl(presId, dispId);

        }

    }

    /**
     *
     * @param presId
     * @throws SQLException
     */
    public void delete(int presId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = " DELETE FROM DisplayCtrl where PresentationId = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, presId);

            ps.executeUpdate();

        }
    }

    /**
     *
     * @param presId
     * @return id
     * @throws SQLException
     */
    public int[] readId(int presId) throws SQLException {
        try (Connection con = cm.getConnection()) {
            int[] id = null;
            String sql = "Select DisplayId from DisplayCtrl where PresentationId = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, presId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DisplayCtrl dc = getOnedc(rs);

            }
            return id;
        }
    }
}
