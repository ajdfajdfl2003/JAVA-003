package idv.angus.client;

import idv.angus.gateway.filter.HeaderHttpResponseFilter;
import idv.angus.gateway.filter.HttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Log4j2
public class MyHttpClient {
    public final CloseableHttpAsyncClient httpclient;
    private final HttpResponseFilter responseFilter;

    public MyHttpClient() {
        responseFilter = new HeaderHttpResponseFilter();
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setRcvBufSize(32 * 1024)
                .build();
        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        httpclient.start();
    }

    public void get(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
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

    private void handleResponse(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            // 只有 Http status code = 200 才有回傳內容
            // 其餘 後端 API 是什麼回傳，Proxy 就怎麼回傳
            if (200 == endpointResponse.getStatusLine().getStatusCode()) {
                byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
                response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            } else {
                response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(endpointResponse.getStatusLine().getStatusCode()));
                response.headers().setInt("Content-Length", 0);
            }
            response.headers().set("Content-Type", "application/json");
            responseFilter.filter(response);
        } catch (IOException e) {
            log.error("handle response failed", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
        } finally {
            if (inbound != null) {
                if (!HttpUtil.isKeepAlive(inbound)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }
}
