package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float pressure;
    private float humidity;
    private float sea_level;
    private float grnd_level;

    // ゲッター
    public float getTemp() {
        return temp;
    }
    public float getFeels_like() {
        return feels_like;
    }
    public float getTemp_min() {
        return temp_min;
    }
    public float getTemp_max() {
        return temp_max;
    }
    public float getPressure() {
        return pressure;
    }
    public float getHumidity() {
        return humidity;
    }
    public float getSea_level() {
        return sea_level;
    }
    public float getGrnd_level() {
        return grnd_level;
    }

    // セッター
    public void setTemp( float temp ) {
        this.temp = temp;
    }
    public void setFeels_like( float feels_like ) {
        this.feels_like = feels_like;
    }
    public void setTemp_min( float temp_min ) {
        this.temp_min = temp_min;
    }
    public void setTemp_max( float temp_max ) {
        this.temp_max = temp_max;
    }
    public void setPressure( float pressure ) {
        this.pressure = pressure;
    }
    public void setHumidity( float humidity ) {
        this.humidity = humidity;
    }
    public void setSea_level( float sea_level ) {
        this.sea_level = sea_level;
    }
    public void setGrnd_level( float grnd_level ) {
        this.grnd_level = grnd_level;
    }
}
