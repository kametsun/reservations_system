package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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

    public static Facility NEW(){
        return new  Facility("", Time.valueOf(""), Time.valueOf(""), "");
    }

    public static Facility getFacilityByName(String facilityName){
        Facility facility = Facility.NEW();
        MyDB.connectDB();

        try {
            String sql = "SELECT * FROM db_reservation.facilities WHERE name='" + facilityName + "';";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);

            if (rs.next()){
                String name = rs.getString("name");
                String openTime = rs.getString("open_time");
                String closeTime = rs.getString("close_time");
                String explanation = rs.getString("explanation");
                facility = new Facility(name, Time.valueOf(openTime), Time.valueOf(closeTime), explanation);
            } else {
                return facility;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        return facility;
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
                res += name + " " + openTime + " ~ " + closeTime + " " + explanation + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }

    //施設説明を取得
    public static String getExplanation(String facilityName){
        String res = "";
        try {
            MyDB.connectDB();
            String sql = "SELECT explanation FROM db_reservation.facilities WHERE name = '" + facilityName + "';";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            if (rs.next()) {
                res = rs.getString("explanation");
            } else {
                res = "施設が見つかりませんでした。";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static List<String> getAllFacilityNames(){
        List<String> facilityNames = new ArrayList<>();
        MyDB.connectDB();

        try {
            String sql = "SELECT name FROM db_reservation.facilities;";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                facilityNames.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }

        return facilityNames;
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
