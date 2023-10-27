package client_system.view.component;

import java.awt.*;

public class ChoiceMinute extends Choice {
    public ChoiceMinute() {
        //00分と30分のみを登録
        add("00");
        add("30");
    }

    //選択されている分を整数で返す
    public int getMinute(int index) {
        if (index == 0) {
            return 0;
        } else {
            return 30;
        }
    }
}
