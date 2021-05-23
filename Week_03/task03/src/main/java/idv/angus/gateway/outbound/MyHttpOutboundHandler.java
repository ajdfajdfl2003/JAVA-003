package idv.angus.gateway.outbound;

import idv.angus.client.MyHttpClient;
import idv.angus.gateway.filter.HeaderHttpRequestFilter;
import idv.angus.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class MyHttpOutboundHandler {
    private final ExecutorService proxy;
    private final String backendUrl;
    private final MyHttpClient httpClient;
    private final HttpRequestFilter requestFilter;

    public MyHttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
        requestFilter = new HeaderHttpRequestFilter();
        proxy = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        httpClient = new MyHttpClient();
    }

    public void handle(FullHttpRequest inbound, ChannelHandlerContext ctx) {
        requestFilter.filter(inbound);
        proxy.submit(() -> httpClient.get(inbound, ctx, backendUrl + inbound.uri()));
    }
}
