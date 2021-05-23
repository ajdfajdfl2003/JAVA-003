package idv.angus.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;


public class MyHttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    private final List<String> backends;

    public MyHttpInboundInitializer(List<String> backends) {
        this.backends = backends;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        // 加了 HttpServerCodec and HttpObjectAggregator
        // 才不會跳 自定義的 InboundHandler 拋 cannot cas PooledUnsafeDirectByteBuf to FullHttpRequest
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));

        pipeline.addLast(new MyHttpInboundHandler(backends));
    }
}
