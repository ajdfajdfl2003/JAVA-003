package idv.angus.java003.task06;

import idv.angus.java003.task06.exception.MyHttpException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyHttpClientTest {
    @Test
    void http_get() throws MyHttpException {
        MyHttpClient client = new MyHttpClient();
        assertEquals("hello, nio1", client.get("http://localhost:8801"));
    }
}