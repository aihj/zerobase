package data;

import connect.DBconn;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;


public class historyDAO {
    public static Connection conn;
    public static PreparedStatement ps;
    public static ResultSet rs;

    public static void search(String lat, String lnt) {

        conn = null;
        ps = null;
        rs = null;

        try {
            conn = DBconn.connect();
            String sql = " insert into search_wifi "
                    + " (lat, lnt, search_dttm) "
                    + " values ( ?, ?, ? )";

            ps = conn.prepareStatement(sql);

            DateFormatSymbols dfs = new DateFormatSymbols(Locale.KOREAN);
            dfs.setWeekdays(new String[]{
                    "null",  "일", "월", "화", "수", "목", "금", "토"
            });
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd '('E')' HH:mm:ss", dfs);
            String strDate = sdf.format(new Date());

            ps.setString(1, lat);
            ps.setString(2, lnt);
            ps.setString(3, strDate.toString());
            ps.executeUpdate();

            System.out.println("데이터 저장 완료");

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            DBconn.close(conn, ps, rs);
        }
    }

    public List<historyDTO> searchList() {
        List<historyDTO> list = new ArrayList<>();

        conn = null;
        ps = null;
        rs = null;

        conn = DBconn.connect();
        String sql = " select * "
                + " from search_wifi "
                + " order by id desc ";

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            historyDTO historyDTO = new historyDTO(
                    rs.getInt("id")
                    , rs.getString("lat")
                    , rs.getString("lnt")
                    , rs.getString("search_dttm")
            );
            list.add(historyDTO);
            
        DBconn.close(conn, ps, rs);
        }

        return list;
    }

    public void delList(String id) {

        conn = null;
        ps = null;
        rs = null;

        conn = DBconn.connect();
        String sql = "delete from search_wifi where id = ? ";

        ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();

        DBconn.close(conn, ps, rs);
    }
}
