package client_system.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import client_system.DB.MyDB;
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

    public Reservation(String reserverID, String facilityName, String strDate, String strStartTime, String strEndTime){
        setReserverID(reserverID);
        setFacilityName(facilityName);
        setDate(Date.valueOf(strDate));
        setStartTime(Time.valueOf(strStartTime));
        setEndTime(Time.valueOf(strEndTime));
    }

    public Reservation(String id, String userID, String facilityName, String date, String startTime, String endTime) {
        setReservationID(Integer.parseInt(id));
        setReserverID(userID);
        setFacilityName(facilityName);
        setDate(Date.valueOf(date));
        setStartTime(Time.valueOf(startTime));
        setEndTime(Time.valueOf(endTime));
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

    public static List<Reservation> getReservationListByUser(String userID){
        List<Reservation> reservations = new ArrayList<>();
        MyDB.connectDB();

        try {
            String sql = "SELECT * FROM db_reservation.reservations WHERE reserver_id = ?";
            PreparedStatement preparedStatement = MyDB.sqlCon.prepareStatement(sql);
            preparedStatement.setString(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("reservation_id");
                String date = rs.getString("date");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String facilityName = rs.getString("facility_name");

                Reservation reservation = new Reservation(id, userID, facilityName, date, startTime, endTime);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
          MyDB.closeDB();
        }

        return reservations;
    }


    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public String getReserverID() {
        return reserverID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
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
