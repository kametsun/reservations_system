package initialize;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class InitializeUser {
    //初期データ挿入メソッド
    protected static void initialize(){
        //初期化
        clearTable();

        List<User> users = new ArrayList<>();
        users.add(new User("watanabe", "渡辺博芳", "宇都宮市豊郷台1-1", "0286277111", "hiro@ics.teikyo-u.ac.jp", "pass0001"));
        users.add(new User("teikyo", "帝京太郎", "東京都板橋区加賀2-11-1", "0339641211", "teikyo@teikyo-u.ac.jp", "pass0002"));
        users.add(new User("kametsun", "亀窪翼", "静岡県静岡市清水区123-4", "08012345678", "tsubasa@teikyo-u.ac.jp", "pass0003"));
        users.add(new User("mondoujou", "モンどう", "静岡県浜松市123-4", "09012341234", "mondoujou@teikyo-u.ac.jp", "pass0004"));

        for (User user : users){
            user.insert(user);
        }
    }

    //ユーザテーブルクリアーメソッド
    private static void clearTable() {
        String sql = "delete from db_reservation.users;";
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
