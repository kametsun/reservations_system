package initialize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDB {
    private static final String userID = "reservation_user";
    private static final String pass = "pass0004";

    public static Connection sqlCon;
    public static Statement sqlStmt;

    public static void connectDB(){
        String url = "jdbc:mysql://localhost?nseUnicode=true&characterEncoding=SJIS";

        try{
            sqlCon = DriverManager.getConnection(url, userID, pass);
            sqlStmt = sqlCon.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeDB(){
        try {
            sqlStmt.close();
            sqlCon.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
