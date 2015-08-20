package example.com.rxandroidexample;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class JsonConverter implements Converter {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        InputStream inputStream = null;
        JSONObject jsonObject = null;
        try {
            inputStream = body.in();
            jsonObject = new JSONObject(IOUtils.toString(inputStream, UTF_8));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }
}
