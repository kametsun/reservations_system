package model;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import initialize.MyDB;
public class Reservation {
    private int reservationID;
    private String reserverID, facilityName;
    private Timestamp date;
    private Time startTime, endTime;

    public Reservation(String reserverID, String facilityName, Timestamp date, Time startTime, Time endTime) {
        setReserverID(reserverID);
        setFacilityName(facilityName);
        setDate(date);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    //予約登録メソッド
    public void insertReservation(Reservation reservation){
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

    public int getReservationID() {
        return reservationID;
    }

    private void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getReserverID() {
        return reserverID;
    }

    private void setReserverID(String reserverID) {
        this.reserverID = reserverID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    private void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public Date getDate() {
        return date;
    }

    private void setDate(Timestamp date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    private void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    private void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
