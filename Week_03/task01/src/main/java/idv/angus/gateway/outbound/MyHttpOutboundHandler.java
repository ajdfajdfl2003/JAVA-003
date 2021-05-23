package idv.angus.gateway.outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Log4j2
public class MyHttpOutboundHandler {
    private final CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
    private final ExecutorService proxy;
    private final String backendUrl;

    public MyHttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
        proxy = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void handle(FullHttpRequest inbound, ChannelHandlerContext ctx) {
        proxy.submit(() -> fetchGet(inbound, ctx, backendUrl + inbound.uri()));
    }

    private void fetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        final HttpGet httpGet = new HttpGet(url);

        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                handleResponse(inbound, ctx, endpointResponse);
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                log.error("fetch: " + url + " failed", ex);
            }

            @Override
            public void cancelled() {
                httpGet.abort();
                log.info("fetch: " + url + " has been cancelled");
            }
        });
    }

    private void handleResponse(final FullHttpRequest inbound, ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (IOException e) {
            log.error("handle response failed", e);
        } finally {
            if (inbound != null) {
                if (!HttpUtil.isKeepAlive(inbound)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }
}
