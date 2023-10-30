package weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Weather {
    Coord CoordObject;
    @JsonProperty("weather")
    ArrayList<Object> weather = new ArrayList<Object>();
    private String base;
    Main MainObject;
    private float visibility;
    Wind WindObject;
    Clouds CloudsObject;
    private float dt;
    Sys SysObject;
    private float timezone;
    private float id;
    private String name;
    private float cod;

    // ゲッター
    public Coord getCoord() {
        return CoordObject;
    }
    public String getBase() {
        return base;
    }
    public Main getMain() {
        return MainObject;
    }
    public float getVisibility() {
        return visibility;
    }
    public Wind getWind() {
        return WindObject;
    }
    public Clouds getClouds() {
        return CloudsObject;
    }
    public float getDt() {
        return dt;
    }
    public Sys getSys() {
        return SysObject;
    }
    public float getTimezone() {
        return timezone;
    }
    public float getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public float getCod() {
        return cod;
    }

    // セッター
    public void setCoord( Coord coordObject ) {
        this.CoordObject = coordObject;
    }
    public void setBase( String base ) {
        this.base = base;
    }
    public void setMain( Main mainObject ) {
        this.MainObject = mainObject;
    }
    public void setVisibility( float visibility ) {
        this.visibility = visibility;
    }
    public void setWind( Wind windObject ) {
        this.WindObject = windObject;
    }
    public void setClouds( Clouds cloudsObject ) {
        this.CloudsObject = cloudsObject;
    }
    public void setDt( float dt ) {
        this.dt = dt;
    }
    public void setSys( Sys sysObject ) {
        this.SysObject = sysObject;
    }
    public void setTimezone( float timezone ) {
        this.timezone = timezone;
    }
    public void setId( float id ) {
        this.id = id;
    }
    public void setName( String name ) {
        this.name = name;
    }
    public void setCod( float cod ) {
        this.cod = cod;
    }

    //天気予報を受け取るAPIキー
    private static final String apiKey = "eb8f88aedd3e31372198e1dc924fac91";

    public static String getWeather(){
        String apiURL = "https://api.openweathermap.org/data/2.5/weather?lat=34.983309150897824&lon=138.40685919693365&q=shizuoka&lang=ja&units=metric&appid=" + apiKey;

        try {
            URL url = new URL(apiURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            //リクエストメソッドをGETに設定
            connection.setRequestMethod("GET");

            //レスポンスを取得
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String res = "";
            while ((res = bufferedReader.readLine()) != null)
            {
                response.append(res);
            }
            bufferedReader.close();

            return response.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //JSON解析
    public static Weather parseJson(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Weather weather = objectMapper.readValue(json, Weather.class);
            return weather;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        Weather weather = parseJson(getWeather());
        System.out.println(weather.getName());
    }
}
