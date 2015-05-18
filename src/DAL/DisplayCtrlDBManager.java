/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BLL.Exceptions.BivExceptions;
import Entities.DisplayCtrl;
import Entities.Presentation;
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
 * @author notandi
 */
public class DisplayCtrlDBManager
{

    private final DBConnectionManager cm;

    private static DisplayCtrlDBManager instance = null;

    private DisplayCtrlDBManager() throws IOException
    {
        cm = DBConnectionManager.getInstance();

    }

    public static DisplayCtrlDBManager getInstance() throws IOException
    {
        if (instance == null)
        {
            instance = new DisplayCtrlDBManager();
        }
        return instance;
    }

    /**
     * @param rs results of the query.
     * @return new Text
     */
//    private Text getOneText(ResultSet rs) throws SQLException
//    {
//        int id = rs.getInt("ID");
//        int presTypeId = rs.getInt("PresTypeId");
//        String title = rs.getString("Title");
//
//        Date startDate = rs.getDate("StartDate");
//        Date endDate = rs.getDate("EndDate");
//        Double timer = rs.getDouble("Timer");
//
//        boolean notSafe = rs.getBoolean("NotSafe");
//        boolean disable = rs.getBoolean("Disable");
//
//        String PresName = rs.getString("ScreenName");
//
////        String depName = rs.getString("Name");
//        return new Text(id, presTypeId, title, startDate, endDate, timer, notSafe);
//
//    }
    public DisplayCtrl getOnedc(ResultSet rs) throws SQLException
    {
        String presTitle = rs.getString("Title");
        String presType = rs.getString("Type");
        String screenName = rs.getString("ScreenName");
        boolean disable = rs.getBoolean("Disable");
        int presId = rs.getInt("PresentationId");
        int dispId = rs.getInt("DisplayId");

        DisplayCtrl dc = new DisplayCtrl(presTitle, presType, screenName, disable, presId, dispId);

        return dc;
    }

    public DisplayCtrl getOnedcEditPres(ResultSet rs) throws SQLException
    {
        String presTitle = rs.getString("Title");
        String presType = rs.getString("Type");
        String screenName = rs.getString("ScreenName");
        Date startDate = rs.getDate("StartDate");
        Date endDate = rs.getDate("EndDate");
        Double timer = rs.getDouble("Timer");
        int presId = rs.getInt("PresentationId");
        int dispId = rs.getInt("DisplayId");

        DisplayCtrl dc = new DisplayCtrl(presTitle, presType, screenName, startDate, endDate, timer, presId, dispId);

        return dc;
    }

    public ArrayList<DisplayCtrl> readAllPres() throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<DisplayCtrl> dcList = new ArrayList<>();
            String sql = "Select Presentation.Title, PresType.[Type], DisplayCtrl.[Disable], Display.ScreenName , DisplayCtrl.PresentationId , DisplayCtrl.DisplayId\n"
                    + "                    from DisplayCtrl\n"
                    + "                    Inner JOIN Presentation ON DisplayCtrl.PresentationId = Presentation.ID\n"
                    + "                    Inner JOIN PresType ON Presentation.PresTypeId = PresType.ID\n"
                    + "                    Inner JOIN Display ON DisplayCtrl.DisplayId = Display.ID";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                DisplayCtrl dc = getOnedc(rs);
                dcList.add(dc);
            }
            return dcList;
        }
    }

    public ArrayList<DisplayCtrl> readAllEditPres() throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            ArrayList<DisplayCtrl> dcList = new ArrayList<>();
            String sql = "Select Presentation.Title, PresType.[Type], Display.ScreenName, Presentation.StartDate, Presentation.EndDate, Presentation.Timer, DisplayCtrl.PresentationId, DisplayCtrl.DisplayId\n"
                    + "                    from DisplayCtrl\n"
                    + "                    Inner JOIN Presentation ON DisplayCtrl.PresentationId = Presentation.ID\n"
                    + "                    Inner JOIN PresType ON Presentation.PresTypeId = PresType.ID\n"
                    + "                    Inner JOIN Display ON DisplayCtrl.DisplayId = Display.ID";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                DisplayCtrl dc = getOnedcEditPres(rs);
                dcList.add(dc);
            }
            return dcList;
        }
    }

    /**
     * @param pres disables or enables the selected Presentations
     * @throws java.sql.SQLException
     */
    public void updateDisable(DisplayCtrl dc) throws SQLException
    {
        try (Connection con = cm.getConnection())
        {
            System.out.println(dc.isDisable() + "" + dc.getPresId());
            String sql = "update DisplayCtrl set Disable = ? where PresentationId = ? and DisplayId = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setBoolean(1, dc.isDisable());
            ps.setInt(2, dc.getPresId());
            ps.setInt(3, dc.getDispId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0)
            {
                throw new BivExceptions("Unable to Update Dispctrl.");
            }

        }
    }
}
