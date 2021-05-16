package idv.angus.java003.task06;

import idv.angus.java003.task06.exception.MyHttpException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class MyOkHttp {
    private static final OkHttpClient client = new OkHttpClient();

    public String get(String url) throws MyHttpException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            log.info("http status code: " + response.code());
            final String responseMessage = response.body().string();
            log.info("response: " + responseMessage);
            return responseMessage;
        } catch (IOException e) {
            log.error("something went wrong when http get to " + url, e);
            throw new MyHttpException("http get failed");
        }
    }
}
