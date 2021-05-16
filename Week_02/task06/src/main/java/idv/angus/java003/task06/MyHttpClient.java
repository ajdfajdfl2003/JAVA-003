package idv.angus.java003.task06;

import idv.angus.java003.task06.exception.MyHttpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class MyHttpClient {
    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    public String get(String url) throws MyHttpException {
        HttpGet httpGet = new HttpGet("http://localhost:8801");
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            log.info("status line: " + response.getStatusLine());
            String responseMessage = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("response: " + responseMessage);
            return responseMessage;
        } catch (IOException e) {
            log.error("something went wrong when http get to " + url, e);
            throw new MyHttpException("http get failed");
        }
    }
}
