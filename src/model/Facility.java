package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import initialize.MyDB;

public class Facility {
    private String name;
    private Time openTime, closeTime;
    private String explanation;

    public Facility(String name, Time openTime, Time closeTime, String explanation) {
        setName(name);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setExplanation(explanation);
    }

    //施設登録メソッド
    public static void insert(Facility facility){
        String res = "";
        MyDB.connectDB();

        try {
            String sql = "INSERT INTO db_reservation.facilities ( name, open_time, close_time, explanation ) VALUES ('";
            sql += facility.name + "','" +facility.openTime + "','" + facility.closeTime + "','" + facility.explanation + "');";

            int resInt = MyDB.sqlStmt.executeUpdate(sql);
            res = resInt + "行登録しました。";
        } catch (SQLException e) {
            res = "施設登録にエラーが発生しました。";
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }

    public static void selectAll(){
        MyDB.connectDB();
        String res = "";

        try {
            String sql = "SELECT * FROM db_reservation.facilities;";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String openTime = rs.getString("open_time");
                String closeTime = rs.getString("close_time");
                String explanation = rs.getString("explanation");
                res += name + " " + openTime + " ~ " + closeTime + explanation + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    private void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    private void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
