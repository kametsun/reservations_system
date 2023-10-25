package client_system.controller;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import client_system.view.MainFrame;
import client_system.view.component.LoginDialog;
import initialize.MyDB;
import model.Facility;

public class ReservationController {
    String reserverID;
    private boolean isLogin;

    public ReservationController(){
        isLogin = false;
    }

    public String getReservationOfUser(){
        String res = "[予約状況]\n\n";
        if (isLogin) {
            String sql = "SELECT * FROM db_reservation.reservations WHERE reserver_id = '" + reserverID +"'" + "ORDER BY date;";
            try {
                MyDB.connectDB();
                ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
                int cnt = 1;
                boolean hasReservation = false;
                while (rs.next()) {
                    hasReservation = true;
                    String id = rs.getString("reservation_id");
                    String startTime = rs.getString("start_time");
                    String endTime = rs.getString("end_time");
                    String date = rs.getString("date");
                    String facilityName = rs.getString("facility_name");
                    String explanation = Facility.getExplanation(facilityName);
                    res += "[" + cnt + "]  " + date + " " + facilityName + " " + startTime + " ～ " + endTime + "\n\n[施設概要]\n\t" + explanation + "\n\n";
                    cnt++;
                }

                if (!hasReservation) {
                    res = "予約はありません。";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                MyDB.closeDB();
            }
            return res;
        } else {
            res = "ログインしてください。";
        }
        return res;
    }

    //ログイン・ログアウトボタンの処理
    public String loginLogout(MainFrame mainFrame){
        String res = "";

        if (isLogin){
            isLogin = false;
            mainFrame.btLog.setLabel("ログイン");
        } else {
            //ログインダイアログの生成と表示
            LoginDialog ld = new LoginDialog(mainFrame);
            ld.setVisible(true);
            ld.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

            //IDとパスワードの入力がキャンセルされたら、空文字列を結果として修了
            if (ld.canceled) {
                return "";
            }

            //ユーザIDとパスワードが入力された場合の処理
            //ユーザIDは他の機能のときに使用するためメンバ変数に代入
            reserverID = ld.tfUserID.getText();
            String password = ld.tfPassword.getText();

            MyDB.connectDB();

            try {
                String sql = "SELECT * FROM db_reservation.users WHERE user_id ='" + reserverID + "';";
                ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
                if (rs.next()){
                    String passwordFromDB = rs.getString("password");
                    if (passwordFromDB.equals(password)){
                        //認証成功時: DBのIDとパスワードに一致
                        isLogin = true;
                        mainFrame.btLog.setLabel("ログアウト");
                        res = "ログイン成功しました。";
                    } else {
                        res = "ログインできません。ID・パスワードが違います。";
                    }
                } else {
                    //認証失敗: DBにユーザIDが存在しない
                    res = "ログインできません。ID・パスワードが違います。";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                MyDB.closeDB();
            }
        }
        return res;
    }

    public String getFacilityExplanation(String facilityName) {
        return Facility.getExplanation(facilityName);
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
