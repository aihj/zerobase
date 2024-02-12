package connect;

import java.sql.*;

 public class DBconn {
     public static Connection connect() {

         String dbFilePath = "C:/project/newwifi.db";
         String url = "jdbc:sqlite:" + dbFilePath;

         Connection connection = null;

         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection(url);

        return connection;
     }

     public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {


         try {
             if ((rs != null && !rs.isClosed())
            |(ps != null && ! ps.isClosed())
            |(connection != null && ! connection.isClosed())){
                 rs.close();
             }
         } catch (SQLException e) {
              System.out.println(e.toString());;
         }

     }
}
