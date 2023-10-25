package client_system.controller;

import client_system.view.MainFrame;
import client_system.view.component.LoginDialog;
import initialize.MyDB;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationController {
    String reserverID;
    private boolean isLogin;

    public ReservationController(){
        isLogin = false;
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
