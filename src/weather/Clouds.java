package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    private float all;

    // ゲッター
    public float getAll() {
        return all;
    }
    // セッター
    public void setAll( float all ) {
        this.all = all;
    }
}
