package client_system.view;

import java.awt.*;
import java.awt.event.*;
import client_system.controller.ReservationController;
import client_system.view.component.ChoiceFacility;
//import weather.Weather;

public class MainFrame extends Frame implements ActionListener, WindowListener, KeyListener {
    ReservationController rc;

    //パネル
    Panel panelNorth, panelNorthSub1, panelNorthSub2, panelNorthSub3;   //上部パネル, 上・中央・下
    Panel panelMid; //中央パネル
    Panel panelSouth;   //下部パネル

    public Button btLog;
    Button btExplanation;   //ログイン・ログアウトボタン, 説明ボタン
    Button btVacancy, btReservation, btConfirm, btCancel;   //空き確認, 新規予約, 予約確認, キャンセルボタン

    ChoiceFacility choiceFacility;  //施設選択ボックス
    public TextField tfYear;
    public TextField tfMonth;
    public TextField tfDay;   //年月日入力欄
    TextArea taMessage; //結果表示エリア
    public TextArea taLoginUser;    // ログインしてるユーザ名
    //天気情報を格納する
    //public Weather weather;

    public MainFrame(ReservationController rc){
        this.rc = rc;

        //ボタン生成
        btLog = new Button("ログイン");
        btExplanation = new Button("施設概要");
        btVacancy = new Button("空き状況確認");
        btReservation = new Button("新規予約");
        btConfirm = new Button("予約の確認");
        btCancel = new Button("予約のキャンセル");

        //チョイスボックスの生成
        choiceFacility = new ChoiceFacility();
        tfYear = new TextField("", 4);
        tfMonth = new TextField("", 2);
        tfDay = new TextField("", 2);

        //上中下のパネルを使うため、レイアウトマネージャーにBorderLayoutを設定
        setLayout( new BorderLayout());

        //ログインユーザ名表示テキストエリア
        taLoginUser = new TextArea("",1, 15, TextArea.SCROLLBARS_NONE);
        taLoginUser.setText("ゲストユーザー");
        taLoginUser.setEditable(false);

        //上部パネルの上パネルに || 予約システム || というラベルと || ログイン ||　ボタンを追加
        panelNorthSub1 = new Panel();
        //weather = Weather.parseJson(Weather.getWeather());
        //panelNorthSub1.add(new Label( weather.getName() + " " + weather.getMain().getTemp() + "℃     "));
        panelNorthSub1.add(new Label("施設予約システム                  "));
        panelNorthSub1.add(taLoginUser);    //ログインしてるユーザ表示テキストエリア
        panelNorthSub1.add(btLog);

        //上部パネルの中央パネルに|||施設||[施設名選択]チョイス [概要説明]ボタンを追加
        panelNorthSub2 = new Panel();
        panelNorthSub2.add(new Label("施設  "));
        panelNorthSub2.add(choiceFacility);
        panelNorthSub2.add(new Label("   "));
        panelNorthSub2.add(btExplanation);

        //上部パネルの下パネルに年月日入力欄と|空き状況確認ボタンを追加
        panelNorthSub3 = new Panel();
        panelNorthSub3.add(new Label("  "));
        panelNorthSub3.add(tfYear);
        panelNorthSub3.add(new Label("年"));
        panelNorthSub3.add(tfMonth);
        panelNorthSub3.add(new Label("月"));
        panelNorthSub3.add(tfDay);
        panelNorthSub3.add(new Label("日"));
        panelNorthSub3.add(btVacancy);

        //上部パネルに3つパネルを追加
        panelNorth = new Panel(new BorderLayout());
        panelNorth.add(panelNorthSub1, BorderLayout.NORTH);
        panelNorth.add(panelNorthSub2, BorderLayout.CENTER);
        panelNorth.add(panelNorthSub3, BorderLayout.SOUTH);
        //メイン画面に上部パネル追加
        add(panelNorth, BorderLayout.NORTH);

        //中央パネルにテキストメッセージ欄を設定
        panelMid = new Panel();
        taMessage = new TextArea(20, 80);
        taMessage.setText("Welcome to 予約システム!!");

        taMessage.setEditable(false);
        panelMid.add(taMessage);
        //メイン画面に中央パネルを追加
        add(panelMid, BorderLayout.CENTER);

        //下部パネルにボタン設定
        panelSouth = new Panel();
        panelSouth.add(btReservation);
        panelSouth.add(new Label("   "));
        panelSouth.add(btConfirm);
        panelSouth.add(new Label("   "));
        panelSouth.add(btConfirm);
        panelSouth.add(new Label("   "));
        panelSouth.add(btCancel);
        //メイン画面に下部パネル追加
        add(panelSouth, BorderLayout.SOUTH);

        //ボタンのアクションリスナの追加
        btLog.addActionListener(this);
        btExplanation.addActionListener(this);
        btVacancy.addActionListener(this);
        btReservation.addActionListener(this);
        btConfirm.addActionListener(this);
        btCancel.addActionListener(this);

        addWindowListener(this);
        addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String result = "";
        taMessage.setText("");
        if (e.getSource() == btVacancy) {
            result = rc.getReservationOn( choiceFacility.getSelectedItem(), tfYear.getText(), tfMonth.getText(), tfDay.getText());
        } else if (e.getSource() == btExplanation) {
            result = rc.getFacilityExplanation(choiceFacility.getSelectedItem());
        } else if (e.getSource() == btLog) {
            result = rc.loginLogout(this);
        } else if (e.getSource() == btConfirm) {
            result = rc.getReservationOfUser();
        } else if (e.getSource() == btReservation) {    //新規予約ボタン
            result = rc.makeReservation(this);
        } else if (e.getSource() == btCancel) {
            result = rc.cancelReservation(this);
        }
        taMessage.setText(result);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
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
