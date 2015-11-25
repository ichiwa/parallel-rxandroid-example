package example.com.rxandroidexample;

import org.json.JSONObject;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherApi {
    // http://api.openweathermap.org/data/2.5/weather?q=Tokyo,jp
    @GET("/data/2.5/weather?APPID=d0ca227fdc9e831a32c172faae08b6d6")
    public Observable<JSONObject> get(@Query("q") String query);
}
