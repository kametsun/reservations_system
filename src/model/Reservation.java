package model;

import java.sql.*;
import initialize.MyDB;
public class Reservation {
    private int reservationID;
    private String reserverID, facilityName;
    private Date date;
    private Time startTime, endTime;

    public Reservation(String reserverID, String facilityName, Date date, Time startTime, Time endTime) {
        setReserverID(reserverID);
        setFacilityName(facilityName);
        setDate(date);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    //予約登録
    public static void insert(Reservation reservation){
        String res = "";
        MyDB.connectDB();

        try {
            String sql = "INSERT INTO db_reservation.reservations ( reserver_id, start_time, end_time, date, facility_name ) VALUES ('";
            sql += reservation.reserverID + "','" + reservation.startTime + "','" + reservation.endTime + "','" + reservation.date + "','" + reservation.facilityName + "');";

            int resInt = MyDB.sqlStmt.executeUpdate(sql);
            res = resInt + "行登録しました。";
        } catch (SQLException e) {
            res = "予約登録にエラーが発生しました。";
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }

    //全件取得
    public static void selectAll(){
        MyDB.connectDB();
        String res = "";

        try {
            String sql = "SELECT * FROM db_reservation.reservations;";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            while (rs.next()) {
                String date = rs.getString("date");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String userID = rs.getString("reserver_id");
                String facilityName = rs.getString("facility_name");
                res += date + " " + startTime + " ~ " + endTime + " " + userID + " " + facilityName + "\n";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
        System.out.println(res);
    }


    private void setReserverID(String reserverID) {
        this.reserverID = reserverID;
    }


    private void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }


    private void setDate(Date date) {
        this.date = date;
    }


    private void setStartTime(Time startTime) {
        this.startTime = startTime;
    }


    private void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
