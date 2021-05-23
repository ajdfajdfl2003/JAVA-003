package idv.angus.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;


public class MyHttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    private final String backendUrl;

    public MyHttpInboundInitializer(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));

        pipeline.addLast(new MyHttpInboundHandler(backendUrl));
    }
}
