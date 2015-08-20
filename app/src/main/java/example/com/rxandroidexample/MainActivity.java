package example.com.rxandroidexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.json.JSONException;
import retrofit.RestAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static MainActivity self;
    private final String TAG = MainActivity.class.getSimpleName();

    @Bind (R.id.button)
    Button button;

    @Bind (R.id.result)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        self = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick (R.id.button)
    void onClick(@NonNull View view) {
        // API
        WeatherApi api = new RestAdapter.Builder().setConverter(new JsonConverter())
            .setEndpoint("http://api.openweathermap.org/")
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build()
            .create(WeatherApi.class);
        // 並列でAPIを呼び出す
        Observable.zip(api.get("Tokyo"), api.get("Kyoto"), api.get("Osaka"), api.get("Okinawa"), api.get("Hokkaido"),
            (jsonObject, jsonObject2, jsonObject3, jsonObject4, jsonObject5) -> {
                String newLine = System.getProperty("line.separator");
                StringBuilder sb = new StringBuilder();
                try {
                    sb.append(jsonObject.getString("id") + ":" + jsonObject.getString("name"));
                    sb.append(newLine);
                    sb.append(jsonObject2.getString("id") + ":" + jsonObject2.getString("name"));
                    sb.append(newLine);
                    sb.append(jsonObject3.getString("id") + ":" + jsonObject3.getString("name"));
                    sb.append(newLine);
                    sb.append(jsonObject4.getString("id") + ":" + jsonObject2.getString("name"));
                    sb.append(newLine);
                    sb.append(jsonObject5.getString("id") + ":" + jsonObject3.getString("name"));
                    sb.append(newLine);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return sb.toString();
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.self, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                result.setText(s);
            }
        });
    }
}
