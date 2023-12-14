import client_system.controller.ReservationController;
import client_system.view.MainFrame;

public class ReservationSystem {
    public static void main(String[] args){
        ReservationController reservationController = new ReservationController();
        MainFrame mainFrame = new MainFrame(reservationController);
        mainFrame.setBounds(5, 5, 655, 455);
        mainFrame.setVisible(true);
    }
}
