package example.com.rxandroidexample;

import org.json.JSONObject;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface WeatherApi {
    // http://api.openweathermap.org/data/2.5/weather?q=Tokyo,jp
    @GET("/data/2.5/weather")
    public Observable<JSONObject> get(@Query("q") String query);
}
