package model;

import initialize.MyDB;

import java.sql.SQLException;
import java.sql.Time;

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
    public void insertFacility(Facility facility){
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

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Time getOpenTime() {
        return openTime;
    }

    private void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    private void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getExplanation() {
        return explanation;
    }

    private void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
