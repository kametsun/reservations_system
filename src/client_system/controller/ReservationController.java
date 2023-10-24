package client_system.controller;

import initialize.MyDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationController {

    public String getFacilityExplanation(String facilityName) {
        String res = "";

        String sql = "SELECT explanation from db_reservation.facilities WHERE name = '" + facilityName + "';";
        MyDB.connectDB();

        try {
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            while (rs.next()) {
                res = "[施設説明]\n\n" + rs.getString("explanation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }

        return res;
    }

    public String getReservationOn(String facilityName, String ryer_str, String rmonth_str, String rday_str){
        String res = "";

        //数字かチェック
        try {
            int ryear = Integer.parseInt(ryer_str);
            int rmonth = Integer.parseInt(rmonth_str);
            int rday = Integer.parseInt(rday_str);
        } catch (NumberFormatException e){
            res = "年月日には数字を指定してください。";
            return res;
        }

        res = facilityName + "  " + "予約状況\n\n";

        if (rmonth_str.length() == 1) {
            rmonth_str = "0" + rmonth_str;
        }
        if (rday_str.length() == 1) {
            rday_str = "0" + rday_str;
        }

        //SQLで検索するためフォーマット作成
        String rdate = ryer_str + "-" + rmonth_str + "-" + rday_str;

        MyDB.connectDB();

        try {
            String sql = "SELECT * FROM db_reservation.reservations WHERE date ='" + rdate + "' AND facility_name = '" + facilityName + "' ORDER BY start_time;";
            ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
            boolean isExist = false;
            while (rs.next()){
                String start = rs.getString("start_time");
                String end = rs.getString("end_time");
                res += "   " + start + " -- " + end + "\n";
                isExist = true;
            }
            if (!isExist) {
                res = "予約はありません";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }

        return res;
    }
}
