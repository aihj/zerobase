package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import connect.DBconn;
import static data.historyDAO.search;

public class wifiDAO {
    public static Connection connection;
    public static ResultSet rs;
    public static PreparedStatement ps;

    public wifiDAO() {
    }
    public static int inputWifiData(JsonArray jsonArray) {
        connection = null;
        ps = null;
        rs = null;

        int count = 0;

        try {
            connection = DBconn.connect();
            
            String sql = " insert into public_wifi "
                    + " ( x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, "
                    + " x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, "
                    + " x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) "
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < jsonArray.size(); i++) {

                JsonObject data = (JsonObject) jsonArray.get(i).getAsJsonObject();

                ps.setString(1, data.get("X_SWIFI_MGR_NO").getAsString());
                ps.setString(2, data.get("X_SWIFI_WRDOFC").getAsString());
                ps.setString(3, data.get("X_SWIFI_MAIN_NM").getAsString());
                ps.setString(4, data.get("X_SWIFI_ADRES1").getAsString());
                ps.setString(5, data.get("X_SWIFI_ADRES2").getAsString());
                ps.setString(6, data.get("X_SWIFI_INSTL_FLOOR").getAsString());
                ps.setString(7, data.get("X_SWIFI_INSTL_TY").getAsString());
                ps.setString(8, data.get("X_SWIFI_INSTL_MBY").getAsString());
                ps.setString(9, data.get("X_SWIFI_SVC_SE").getAsString());
                ps.setString(10, data.get("X_SWIFI_CMCWR").getAsString());
                ps.setString(11, data.get("X_SWIFI_CNSTC_YEAR").getAsString());
                ps.setString(12, data.get("X_SWIFI_INOUT_DOOR").getAsString());
                ps.setString(13, data.get("X_SWIFI_REMARS3").getAsString());
                ps.setString(14, data.get("LAT").getAsString());
                ps.setString(15, data.get("LNT").getAsString());
                ps.setString(16, data.get("WORK_DTTM").getAsString());


                if ((i + 1) % 1000 == 0) {
                    int[] result = ps.executeBatch();
                    count += result.length;
                    connection.commit();
                }
            }

            int[] result = ps.executeBatch();
            count += result.length;
            connection.commit();

        } catch (SQLException e) {
             System.out.println(e.toString());
            
        } finally {
            DBconn.close(connection, ps, rs);
        }

        return count;
    }

    public List<wifiDTO> getWifiList(String lat, String lnt) {

        connection = null;
        ps = null;
        rs = null;

        List<wifiDTO> list = new ArrayList<>();

        try {

            connection = DBconn.connect();


            String sql = " SELECT *, " +
                    " round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) " +
                    " -radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    " AS distance " +
                    " FROM public_wifi " +
                    " ORDER BY distance " +
                    " LIMIT 20;";


            ps = connection.prepareStatement(sql);
            ps.setDouble(1, Double.parseDouble(lat));
            ps.setDouble(2, Double.parseDouble(lnt));
            ps.setDouble(3, Double.parseDouble(lat));

            rs = ps.execute();

            while (rs.next()) {
                wifiDTO wifiDTO = data.wifiDTO.builder()
                        .distance(rs.getDouble("distance"))
                        .xSwifiMgrNo(rs.getString("x_swifi_mgr_no"))
                        .xSwifiWrdofc(rs.getString("x_swifi_wrdofc"))
                        .xSwifiMainNm(rs.getString("x_swifi_main_nm"))
                        .xSwifiAdres1(rs.getString("x_swifi_adres1"))
                        .xSwifiAdres2(rs.getString("x_swifi_adres2"))
                        .xSwifiInstlFloor(rs.getString("x_swifi_instl_floor"))
                        .xSwifiInstlTy(rs.getString("x_swifi_instl_ty"))
                        .xSwifiInstlMby(rs.getString("x_swifi_instl_mby"))
                        .xSwifiSvcSe(rs.getString("x_swifi_svc_se"))
                        .xSwifiCmcwr(rs.getString("x_swifi_cmcwr"))
                        .xSwifiCnstcYear(rs.getString("x_swifi_cnstc_year"))
                        .xSwifiInoutDoor(rs.getString("x_swifi_inout_door"))
                        .xSwifiRemars3(rs.getString("x_swifi_remars3"))
                        .lat(rs.getString("lat"))
                        .lnt(rs.getString("lnt"))
                        .workDttm(String.valueOf(rs.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();

                list.add(wifiDTO);
            }

        } catch (SQLException e) {
             System.out.println(e.toString());
        } finally {
            DBconn.close(connection, ps, rs);
        }
        search(lat, lnt);

        return list;
    }

   public List<wifiDTO> listWifiList(String mgrNo, double distance) {

        connection = null;
        ps = null;
        rs = null;

        List<wifiDTO> list = new ArrayList<>();

        try {
            connection = DBconn.connect();
            String sql = " select * from public_wifi where x_swifi_mgr_no = ? ";

            ps = connection.prepareStatement(sql);
            ps.setString(1, mgrNo);

            rs = ps.execute();

            while (rs.next()) {
                wifiDTO wifiDTO = data.wifiDTO.builder()
                       .distance(distance)
                        .xSwifiMgrNo(rs.getString("X_SWIFI_MGR_NO"))
                        .xSwifiWrdofc(rs.getString("X_SWIFI_WRDOFC"))
                        .xSwifiMainNm(rs.getString("X_SWIFI_MAIN_NM"))
                        .xSwifiAdres1(rs.getString("X_SWIFI_ADRES1"))
                        .xSwifiAdres2(rs.getString("X_SWIFI_ADRES2"))
                        .xSwifiInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"))
                        .xSwifiInstlTy(rs.getString("X_SWIFI_INSTL_TY"))
                        .xSwifiInstlMby(rs.getString("X_SWIFI_INSTL_MBY"))
                        .xSwifiSvcSe(rs.getString("X_SWIFI_SVC_SE"))
                        .xSwifiCmcwr(rs.getString("X_SWIFI_CMCWR"))
                        .xSwifiCnstcYear(rs.getString("X_SWIFI_CNSTC_YEAR"))
                        .xSwifiInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"))
                        .xSwifiRemars3(rs.getString("X_SWIFI_REMARS3"))
                        .lat(rs.getString("LAT"))
                        .lnt(rs.getString("LNT"))
                        .workDttm(String.valueOf(rs.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();
                list.add(wifiDTO);
            }
        } catch (SQLException e) {
             System.out.println(e.toString());;
        } finally {
            DBconn.close(connection, ps, rs);
        }

       return list;
    }

    public wifiDTO listWifi(String mgrNo) {
        wifiDTO wifiDTO = new wifiDTO();

        connection = null;
        ps = null;
        rs = null;

        try {
            connection = DBconn.connect();
            String sql = " select * from public_wifi where x_swifi_mgr_no = ? ";
            ps = connection.prepareStatement(sql);

            ps.setString(1, mgrNo);
            rs = ps.execute();

            while (rs.next()) {
                wifiDTO.setXSwifiMgrNo(rs.getString("X_SWIFI_MGR_NO"));
                wifiDTO.setXSwifiWrdofc(rs.getString("X_SWIFI_WRDOFC"));
                wifiDTO.setXSwifiMainNm(rs.getString("X_SWIFI_MAIN_NM"));
                wifiDTO.setXSwifiAdres1(rs.getString("X_SWIFI_ADRES1"));
                wifiDTO.setXSwifiAdres2(rs.getString("X_SWIFI_ADRES2"));
                wifiDTO.setXSwifiInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifiDTO.setXSwifiInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
                wifiDTO.setXSwifiInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
                wifiDTO.setXSwifiSvcSe(rs.getString("X_SWIFI_SVC_SE"));
                wifiDTO.setXSwifiCmcwr(rs.getString("X_SWIFI_CMCWR"));
                wifiDTO.setXSwifiCnstcYear(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifiDTO.setXSwifiInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifiDTO.setXSwifiRemars3(rs.getString("X_SWIFI_REMARS3"));
                wifiDTO.setLat(rs.getString("LAT"));
                wifiDTO.setLnt(rs.getString("LNT"));
                wifiDTO.setWorkDttm(String.valueOf(rs.getTimestamp("work_dttm").toLocalDateTime()));
            }

        } catch (SQLException e) {
             System.out.println(e.toString());;
        } finally {
            DBconn.close(connection, ps, rs);
        }

        return wifiDTO;
    }
}
