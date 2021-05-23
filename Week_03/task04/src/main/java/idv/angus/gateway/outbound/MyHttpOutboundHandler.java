package idv.angus.gateway.outbound;

import idv.angus.client.MyHttpClient;
import idv.angus.gateway.filter.HeaderHttpRequestFilter;
import idv.angus.gateway.filter.HttpRequestFilter;
import idv.angus.gateway.router.RandomHttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class MyHttpOutboundHandler {
    private final ExecutorService proxy;
    private final List<String> backends;
    private final MyHttpClient httpClient;
    private final HttpRequestFilter requestFilter;
    private final RandomHttpEndpointRouter router;

    public MyHttpOutboundHandler(List<String> backends) {
        this.backends = backends;
        requestFilter = new HeaderHttpRequestFilter();
        router = new RandomHttpEndpointRouter();
        proxy = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        httpClient = new MyHttpClient();
    }

    public void handle(FullHttpRequest inbound, ChannelHandlerContext ctx) {
        requestFilter.filter(inbound);
        final String url = router.route(backends) + inbound.uri();
        proxy.submit(() -> httpClient.get(inbound, ctx, url));
    }
}
