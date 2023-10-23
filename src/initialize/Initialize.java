package initialize;

import java.text.ParseException;
import model.Facility;
import model.Reservation;
import model.User;

public class Initialize {
    public static void main(String[] args) throws ParseException {
        InitializeUser.initialize();
        InitializeFacility.initialize();
        InitializeReservation.initialize();

        System.out.println("初期データの挿入完了しました。");

        User.selectAll();
        Facility.selectAll();
        Reservation.selectAll();
    }
}
