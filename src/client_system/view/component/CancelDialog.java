package client_system.view.component;

import model.Reservation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class CancelDialog extends Dialog implements ActionListener, WindowListener {

    //上部・中央・下部パネル
    Panel panelNorth;
    public Panel panelMid;
    Panel panelSouth;

    //スクロールパネル
    ScrollPane scrollPane;

    //チェックボックスの数
    public int checkboxNumber;

    //キャンセル実行・キャンセルのキャンセルボタン
    Button btOK, btCancel;

    //キャンセルの実行がキャンセルされたらtrue
    boolean canceled;

    private static String reservationToString(Reservation reservation){
        String res = "";
        res += reservation.getDate() + "  " + reservation.getFacilityName() + "  " + reservation.getStartTime() + " ～ " + reservation.getEndTime();
        return res;
    }

    public CancelDialog(Frame owner, List<Reservation> reservations){
        //引数には予約レコードが入っている
        super(owner, "予約の取り消し", true);

        //キャンセルは初期値でtrueとする
        canceled = true;

        //ボタン生成
        btOK = new Button("キャンセル実行");
        btCancel = new Button("キャンセル");

        //上部パネルにラベル設定
        panelNorth = new Panel();
        panelNorth.add(new Label("予約の取り消し"), FlowLayout.LEFT);


        //文字列resの戦闘には予約状況\n\nがあるため、最初の2行はレコードではない
        //レコードはreservation[2]からreservation[n - 1]二設定
        //縦を予約のレコードの数にしたグリッドレイアウトに設定
        panelMid = new Panel(new GridLayout(reservations.size(), 1));

        //レコードをチェックボックスにしてパネルに追加
        for (int i = 0; i < reservations.size(); i++)
        {
            panelMid.add(new Checkbox(i+1 + reservationToString(reservations.get(i))));
        }

        //チェックボックスの数を設定
        checkboxNumber = reservations.size();

        //中央パネルはスクロールペイン
        scrollPane = new ScrollPane();
        //スクロールペインにチェックボックスを配置したpanelMidを追加
        scrollPane.add(panelMid);

        //下部パネルにボタンを追加
        panelSouth = new Panel();
        panelSouth.add(btCancel);
        panelSouth.add(new Label("        "));
        panelSouth.add(btOK);

        //Dialogに3つのパネルを追加
        setLayout(new BorderLayout());
        add(panelNorth, BorderLayout.NORTH);
        add(panelMid, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        //イベントリスナを追加
        addWindowListener(this);
        btOK.addActionListener(this);
        btCancel.addActionListener(this);

        //ウィンドウの大きさを設定
        this.setBounds(100, 100, 500, 220);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btCancel)
        {
            setVisible(false);
            dispose();
        }
        else if (e.getSource() == btOK)
        {
            canceled = false;
            setVisible(false);
            dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
