package client_system.view.component;

import java.awt.*;
import java.awt.event.*;

public class ReservationDialog extends Dialog implements ActionListener, WindowListener, ItemListener {
    public boolean canceled;   //キャンセルされたらtrue, 予約実行ボタンのときはfalse

    //パネル
    Panel panelNorth, panelMid, panelSouth;

    //入力用コンポーネント
    public ChoiceFacility choiceFacility;  //施設選択用ボックス
    public TextField tfYear, tfMonth, tfDay;
    public ChoiceHour startHour, endHour;
    public ChoiceMinute startMinute, endMinute;

    //ボタン
    Button btOK, btCancel;

    public ReservationDialog(Frame owner) {
        super(owner, "新規予約", true);

        canceled = true;

        //施設選択ボックス
        choiceFacility = new ChoiceFacility();
        tfYear = new TextField("", 4);
        tfMonth = new TextField("", 2);
        tfDay = new TextField("", 2);

        //開始時刻
        startHour = new ChoiceHour();
        startMinute = new ChoiceMinute();
        //終了時刻
        endHour = new ChoiceHour();
        endMinute = new ChoiceMinute();

        //ボタンの生成
        btOK = new Button("予約実行");
        btCancel = new Button("キャンセル");

        //パネルの生成
        panelNorth = new Panel();
        panelMid = new Panel();
        panelSouth = new Panel();

        //上部パネルに施設選択ボックス、年月日入力欄を追加
        panelNorth.add(new Label("施設 "));
        panelNorth.add(choiceFacility);
        panelNorth.add(new Label("予約日 "));
        panelNorth.add(tfYear);
        panelNorth.add(new Label("年"));
        panelNorth.add(tfMonth);
        panelNorth.add(new Label("月"));
        panelNorth.add(tfDay);
        panelNorth.add(new Label("日 "));

        //中央パネルに予約、開始時刻、終了時刻入力用選択ボックスを追加
        panelMid.add(new Label("予約時間 "));
        panelMid.add(startHour);
        panelMid.add(new Label("時"));
        panelMid.add(startMinute);
        panelMid.add(new Label("分　～ "));
        panelMid.add(endHour);
        panelMid.add(new Label("時"));
        panelMid.add(endMinute);
        panelMid.add(new Label("分"));

        //下部パネルに2つのボタンを追加
        panelSouth.add(btCancel);
        panelSouth.add(new Label("    "));
        panelSouth.add(btOK);

        //ReservationDialogをBorderLayoutに設定し、3つのパネルを追加
        setLayout(new BorderLayout());
        add(panelNorth, BorderLayout.NORTH);
        add(panelMid, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        addWindowListener(this);
        btOK.addActionListener(this);
        btCancel.addActionListener(this);
        choiceFacility.addItemListener(this);
        startHour.addItemListener(this);
        startMinute.addItemListener(this);
        endHour.addItemListener(this);
        endMinute.addItemListener(this);

        //選択されている施設によって、時刻の範囲を設定する
        resetTimeRange();

        this.setBounds(150, 150, 500, 150);
        setResizable(false);
    }

    private void resetTimeRange(){
        if (choiceFacility.getSelectedIndex() == 0) {
            //最初の施設(小ホールのときの設定)
            startHour.resetRange(10, 20);
            endHour.resetRange(10, 21);
        } else {
            startHour.resetRange(9, 19);
            endHour.resetRange(9, 20);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btCancel) {
            setVisible(false);
            dispose();
        } else if (e.getSource() == btOK) {
            canceled = false;
            setVisible(false);
            dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == choiceFacility){   //施設が変更されたら、施設に応じた範囲を設定
            resetTimeRange();
        } else if (e.getSource() == startHour){

            //開始時刻が変更されたら、終了時刻入力欄の時を開始時刻に合わせる
            int start = Integer.parseInt(startHour.getSelectedItem());
            endHour.resetRange(start, Integer.parseInt(endHour.getLast()));

        } else if (e.getSource() == endHour){
            //終了時刻が変更され、最後の時刻の場合は分を00分に設定
            if (endHour.getSelectedIndex() == endHour.getItemCount() - 1){
                endMinute.select(0);
            }
        } else if (e.getSource() == endMinute){
            //終了時刻(分)が変更され、時が最後の場合、分は00分に設定
            if (endHour.getSelectedIndex() == endHour.getItemCount() - 1) {
                endMinute.select(0);
            }
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
