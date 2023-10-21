package initialize;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;


public class InitializeReservation {
    protected static void initialize() throws ParseException {
        String[]  testUserIDs = {
                "watanabe",
                "teikyo",
                "kametsun"
        };

        Time[] testStartTime = {
                Time.valueOf("09:00:00"),
                Time.valueOf("10:00:00"),
                Time.valueOf("11:00:00")
        };

        Time[] testEndTime = {
                Time.valueOf("12:00:00"),
                Time.valueOf("13:00:00"),
                Time.valueOf("14:00:00")
        };

        String[] testDateFormats = {
                "2023-12-24",
                "2023-12-25",
                "2024-01-01"
        };

        String[] testFacilityName = {
                "小会議室4",
                "大会議室1",
                "小ホール2"
        };

        clearReservationsTable();
        List<Reservation> reservations = new ArrayList<>();

        for(int i = 0; i < testUserIDs.length; i++){
            Reservation reservation = new Reservation(
                    testUserIDs[i],
                    testFacilityName[i],
                    Timestamp.valueOf(testDateFormats[i] + " " + testStartTime[i]),
                    testStartTime[i],
                    testEndTime[i]
            );
            reservations.add(reservation);
        }

        for (Reservation reservation : reservations){
            reservation.insertReservation(reservation);
        }
    }

    //予約テーブルクリアーメソッド
    private static void clearReservationsTable() {
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
