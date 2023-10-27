package client_system.view.component;

import java.awt.*;

public class ChoiceHour extends Choice {
    ChoiceHour(){
        //時刻の範囲の初期値は9 ~ 21時とする
        resetRange(9, 21);
    }

    public void resetRange(int start, int end){
        //範囲に含まれる時刻の数を求める
        int number = end - start + 1;

        //選択ボックスの内容をクリア
        removeAll();

        //指定できる時刻を設定
        while (start <= end) {
            String h = String.valueOf(start);

            //１桁の場合0を付与
            if (h.length() == 1) {
                h = "0" + h;
            }
            //選択ボックスに追加
            add(h);
            start++;
        }
    }

    //最後に設定されている時刻を返す
    public String getLast(){
        return getItem(getItemCount() - 1);
    }
}
