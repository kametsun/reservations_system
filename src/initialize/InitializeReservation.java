package initialize;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;

public class InitializeReservation {
    protected static void initialize() throws ParseException {
        //テストデータ
        String[]  testUserIDs = {
                "watanabe",
                "teikyo",
                "kametsun",
                "mondoujou"
        };

        Time[] testStartTime = {
                Time.valueOf("09:00:00"),
                Time.valueOf("10:00:00"),
                Time.valueOf("11:00:00"),
                Time.valueOf("15:00:00")
        };

        Time[] testEndTime = {
                Time.valueOf("12:00:00"),
                Time.valueOf("13:00:00"),
                Time.valueOf("14:00:00"),
                Time.valueOf("17:00:00")
        };

        String[] testDate = {
                "2023-12-24",
                "2023-12-25",
                "2024-01-01",
                "2024-01-01"
        };

        String[] testFacilityName = {
                "小会議室4",
                "大会議室1",
                "小ホール",
                "小ホール"
        };

        clearTable();
        List<Reservation> reservations = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for(int i = 0; i < testUserIDs.length; i++){
            Reservation reservation = new Reservation(
                    testUserIDs[i],
                    testFacilityName[i],
                    Date.valueOf(testDate[i]),
                    testStartTime[i],
                    testEndTime[i]
            );
            reservations.add(reservation);
        }

        for (Reservation reservation : reservations){
            reservation.insert(reservation);
        }
    }

    //予約テーブルクリア
    private static void clearTable() {
        String sql = "delete from db_reservation.reservations;";
        MyDB.connectDB();

        try {
            int deleteRecord = MyDB.sqlStmt.executeUpdate(sql);
            System.out.println(deleteRecord + "件削除しました。");
        } catch (SQLException e) {
            System.out.println("エラーが発生し削除できませんでした。");
            throw new RuntimeException(e);
        } finally {
            MyDB.closeDB();
        }
    }
}
