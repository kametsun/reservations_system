package client_system.initialize;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import client_system.DB.MyDB;
import client_system.model.Facility;

public class InitializeFacility {
    protected static void initialize(){
        String[] types = {"小ホール", "大会議室", "小会議室"};
        Time[] openTimes = {Time.valueOf("09:00:00"), Time.valueOf("10:00:00")};
        Time[] closeTimes = {Time.valueOf("20:00:00"), Time.valueOf("21:00:00")};
        String[] explanations = {
                " 客席数: 500 利用可能時間: 10時 から 21時", //小ホール
                " 客席数: 80 利用可能時間: 9時 から 20時",   //大会議室
                " 客席数: 20 利用可能時間: 9時 から 20時"    //小会議室
        };

        clearTable();

        List<Facility> facilities = new ArrayList<>();
        facilities.add(new Facility(types[0], openTimes[1], closeTimes[1], types[0] + explanations[0]));
        facilities.add(new Facility(types[1] + "1", openTimes[0], closeTimes[0], types[1] + "1" + explanations[1]));
        facilities.add(new Facility(types[1] + "2", openTimes[0], closeTimes[0], types[1] + "2" + explanations[1]));

        for (int i = 1; i < 7; i++){
            facilities.add(new Facility(types[2] + i, openTimes[0], closeTimes[0], types[2] + i + explanations[2]));
        }
        for(Facility facility : facilities){
            facility.insert(facility);
        }
    }

    //施設テーブルクリアーメソッド
    private static void clearTable() {
        String sql = "delete from db_reservation.facilities;";
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
