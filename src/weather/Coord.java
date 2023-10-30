package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coord {
    private float lon;
    private float lat;

    // ゲッター
    public float getLon() {
        return lon;
    }
    public float getLat() {
        return lat;
    }

    // セッター
    public void setLon( float lon ) {
        this.lon = lon;
    }
    public void setLat( float lat ) {
        this.lat = lat;
    }
}
