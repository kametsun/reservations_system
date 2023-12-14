package client_system.initialize;

import java.text.ParseException;
import client_system.model.Facility;
import client_system.model.Reservation;
import client_system.model.User;

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
