package idv.angus.java003.task06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyHttpClientTest {
    @Test
    void http_get() throws MyHttpClient.MyHttpClientException {
        MyHttpClient client = new MyHttpClient();
        assertEquals("hello, nio1", client.get("http://localhost:8801"));
    }
}