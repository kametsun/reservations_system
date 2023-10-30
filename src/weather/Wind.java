package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private float speed;
    private float deg;
    private float gust;

    // ゲッター
    public float getSpeed() {
        return speed;
    }
    public float getDeg() {
        return deg;
    }
    public float getGust() {
        return gust;
    }
    // セッター
    public void setSpeed( float speed ) {
        this.speed = speed;
    }
    public void setDeg( float deg ) {
        this.deg = deg;
    }
    public void setGust( float gust ) {
        this.gust = gust;
    }
}
