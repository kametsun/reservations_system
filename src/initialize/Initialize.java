package initialize;

import java.text.ParseException;

public class Initialize {
    public static void main(String[] args) throws ParseException {
        InitializeUser.initialize();
        InitializeFacility.initialize();
        InitializeReservation.initialize();
        System.out.println("初期データの挿入完了しました。");
    }
}
