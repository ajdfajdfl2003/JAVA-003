package idv.angus.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {
    void filter(FullHttpRequest fullRequest);
}
