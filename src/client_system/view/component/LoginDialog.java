package client_system.view.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class LoginDialog extends Dialog implements ActionListener, WindowListener {
    public boolean canceled;   //キャンセルのときはtrue OK押したらfalse

    //テキストフィールド
    public TextField tfUserID; //ユーザIDを入力するテキストフィールド
    public TextField tfPassword;   //パスワードを入力する

    //ボタン
    Button btOK, btCancel;

    //パネル
    Panel panelNorth;   //上部
    Panel panelMid; //中央
    Panel panelSouth;   //下部

    public LoginDialog(Frame args){
        //親クラスのコンストラクタを呼び出す
        super(args, "Log in", true);

        //キャンセルは初期値ではtrueとする
        canceled = true;

        //テキストフィールドの生成
        tfUserID = new TextField("", 10);
        tfPassword = new TextField("", 10);
        //パスワード入力時「*」になるようにする
        tfPassword.setEchoChar('*');

        //ボタンの生成
        btOK = new Button("OK");
        btCancel = new Button("キャンセル");

        //パネルの生成
        panelNorth = new Panel();
        panelMid = new Panel();
        panelSouth = new Panel();

        panelNorth.add(new Label("ユーザID"));
        panelNorth.add(tfUserID);

        panelMid.add(new Label("パスワード"));
        panelMid.add(tfPassword);

        panelSouth.add(btCancel);
        panelSouth.add(btOK);

        setLayout(new BorderLayout());
        add(panelNorth, BorderLayout.NORTH);
        add(panelMid, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);

        addWindowListener(this);
        btOK.addActionListener(this);
        btCancel.addActionListener(this);

        this.setBounds(100, 100, 350, 150);
        setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btCancel){
            canceled = true;
        } else if (e.getSource() == btOK){
            canceled = false;
        }
        setVisible(false);
        dispose();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        setVisible(false);
        canceled = true;
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
