package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {
    private String country;
    private float sunrise, sunset;

    //ゲッター
    public String getCountry() {
        return country;
    }
    public float getSunrise() {
        return sunrise;
    }
    public float getSunset() {
        return sunset;
    }

    // セッター
    public void setCountry( String country ) {
        this.country = country;
    }
    public void setSunrise( float sunrise ) {
        this.sunrise = sunrise;
    }
    public void setSunset( float sunset ) {
        this.sunset = sunset;
    }
}
