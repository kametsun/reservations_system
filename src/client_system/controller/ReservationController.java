package client_system.controller;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import client_system.view.MainFrame;
import client_system.view.component.CancelDialog;
import client_system.view.component.LoginDialog;
import client_system.view.component.ReservationDialog;
import client_system.DB.MyDB;
import client_system.model.Facility;
import client_system.model.Reservation;
import client_system.model.User;

public class ReservationController {
    String reserverID;
    private boolean isLogin;
    private User loginUser;

    public ReservationController(){
        isLogin = false;
    }

    public String getReservationOfUser(){
        String res = "[予約状況]\n\n";
        if (isLogin) {
            String sql = "SELECT * FROM db_reservation.reservations WHERE reserver_id = '" + loginUser.getUserID() +"'" + "ORDER BY date;";
            try {
                MyDB.connectDB();
                ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
                int cnt = 1;
                boolean hasReservation = false;
                while (rs.next()) {
                    hasReservation = true;
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

    //予約日の期間の条件をチェックするメソッド
    private boolean checkReservationDate(int yyyy, int MM, int dd){
        //予約日
        Calendar dateR = Calendar.getInstance();
        dateR.set(yyyy, MM - 1, dd);     //月から1引かなければならないことに注意

        //今日の1日後
        Calendar date1 = Calendar.getInstance();
        date1.add(Calendar.DATE, 1);

        //今日の3ヶ月後(90日後)
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DATE, 90);

        if (dateR.after(date1) && dateR.before(date2)){
            return true;
        }
        return false;
    }

    public String cancelReservation(MainFrame frame){
        List<Reservation> reservations;
        int cancelReservationCnt = 0;
        if (isLogin) {
            reservations = Reservation.getReservationListByUser(loginUser.getUserID());
            //キャンセルダイアログ
            CancelDialog cancelDialog = new CancelDialog(frame, reservations);
            cancelDialog.setVisible(true);


            for (int i = 0; i < cancelDialog.checkboxNumber; i++)
            {
                Checkbox checkbox = (Checkbox) cancelDialog.panelMid.getComponent(i);
                if ( checkbox.getState() )
                {
                    int reservationID = reservations.get(i).getReservationID();
                    MyDB.connectDB();
                    //idで予約を削除するSQL
                    String sql = "DELETE FROM db_reservation.reservations WHERE reservation_id = '" + reservationID + "';";
                    try {
                        int rsInt = MyDB.sqlStmt.executeUpdate(sql);
                        if (rsInt > 0)
                        {
                            cancelReservationCnt++; //キャンセル数インクリメント
                        } else {
                            return "予約をキャンセルできませんでした。";
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        MyDB.closeDB();
                    }
                }
            }
        } else {
            return "ログインしてください。";
        }

        if (cancelReservationCnt > 0)
        {
            return cancelReservationCnt + " 件の予約をキャンセルしました。";
        } else {
            return "選択された予約はキャンセルできませんでした。";
        }
    }

    public String makeReservation(MainFrame frame){
        String res = "";

        if (isLogin){
            //新規予約画面作成
            ReservationDialog rd = new ReservationDialog(frame);

            //新規予約画面の予約日にメイン画面に設定されている年月日を設定する
            rd.tfYear.setText(frame.tfYear.getText());
            rd.tfMonth.setText(frame.tfMonth.getText());
            rd.tfDay.setText(frame.tfDay.getText());

            //新規予約画面を可視化
            rd.setVisible(true);
            if (rd.canceled){
                return res;
            }

            try {
                //新規予約画面から年月日を取得
                String strYear = rd.tfYear.getText();
                String strMonth = rd.tfMonth.getText();
                String strDay = rd.tfDay.getText();

                //年月日が数字かどうかチェック
                int intYear = Integer.parseInt(strYear);
                int intMonth = Integer.parseInt(strMonth);
                int intDay = Integer.parseInt(strDay);

                if (checkReservationDate(intYear, intMonth, intDay)){
                    //新規予約画面から施設名、開始時刻、終了時刻を取得
                    String facility = rd.choiceFacility.getSelectedItem();
                    String st = rd.startHour.getSelectedItem() + ":" + rd.startMinute.getSelectedItem() + ":00";
                    String et = rd.endHour.getSelectedItem() + ":" + rd.endMinute.getSelectedItem() + ":00";

                    //開始時刻と終了時刻が同じ
                    if (st.equals(et)){
                        res = "開始時刻と終了時刻が同じです";
                    } else {
                      MyDB.connectDB();

                      try {
                          //月と日が一桁だったら、前に0をつける処理
                          if (strMonth.length() == 1){
                              strMonth = "0" + strMonth;
                          }
                          if (strDay.length() == 1){
                              strDay = "0" + strDay;
                          }

                          String strDate = strYear + "-" + strMonth + "-" + strDay;
                          //指定した施設の指定した予約日の予約情報を取得するクエリ
                          String sql = "SELECT * FROM db_reservation.reservations WHERE facility_name = '" + facility + "' AND date = '" + strDate + "';";
                          ResultSet rs = MyDB.sqlStmt.executeQuery(sql);

                          //重なりチェックの結果の初期値 (重なっていない=false)
                          boolean ng = false;

                          //取得したレコードに対して確認
                          while (rs.next()){
                              String start = rs.getString("start_time");
                              String end = rs.getString("end_time");

                              if ( (start.compareTo(st) < 0 && st.compareTo(end) < 0) || (st.compareTo(start) < 0 && start.compareTo(et) < 0) )
                              {
                                  //重複有りの場合にngをtrueに設定
                                  ng = true;
                                  break;
                              }
                          }
                          // 重なりチェック終了

                          //重なっていない場合
                          if (!ng) {
                              //予約挿入
                              sql = "INSERT INTO db_reservation.reservations ( date, start_time, end_time, reserver_id, facility_name ) VALUES ( '";
                              sql += strDate + "', '" + st + "','" + et + "','" + reserverID + "','" + facility + "' );";

                              int rsInt = MyDB.sqlStmt.executeUpdate(sql);
                              return "予約されました";
                          } else {
                              return "既にある予約と重なっています";
                          }
                      } catch (SQLException e) {
                          throw new RuntimeException(e);
                      } finally {
                          MyDB.closeDB();
                      }
                    }
                } else {
                    return "予約日が無効です";
                }
            } catch (NumberFormatException e) {
                return "予約日には数字を指定してください。";
            }
        } else {
            return "ログインしてください。";
        }

        return res;
    }

    //ログイン・ログアウトボタンの処理
    public String loginLogout(MainFrame mainFrame){
        String res = "";

        if (isLogin){
            isLogin = false;
            mainFrame.btLog.setLabel("ログイン");
            mainFrame.taLoginUser.setText("ゲストユーザー");
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

            try {
                MyDB.connectDB();
                String sql = "SELECT * FROM db_reservation.users WHERE user_id ='" + reserverID + "';";
                ResultSet rs = MyDB.sqlStmt.executeQuery(sql);
                if (rs.next()){
                    String passwordFromDB = rs.getString("password");
                    if (passwordFromDB.equals(password)){
                        //認証成功時: DBのIDとパスワードに一致
                        isLogin = true;
                        mainFrame.btLog.setLabel("ログアウト");
                        res = "ログイン成功しました。";
                        loginUser = User.select(reserverID);
                        mainFrame.taLoginUser.setText(loginUser.getName());
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

    public String getFacilityExplanation(String facilityName) { return Facility.getExplanation(facilityName); }

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

        try {
            MyDB.connectDB();
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

    public User getLoginUser() {
        return loginUser;
    }
}
