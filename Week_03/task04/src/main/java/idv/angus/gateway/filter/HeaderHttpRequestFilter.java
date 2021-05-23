package idv.angus.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest) {
        fullRequest.headers().set("x-java", "proxy to backend");
    }
}
