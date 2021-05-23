package idv.angus.gateway.inbound;

import idv.angus.gateway.outbound.MyHttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class MyHttpInboundHandler extends ChannelInboundHandlerAdapter {
    private final MyHttpOutboundHandler outBoundHandler;

    public MyHttpInboundHandler(String backendUrl) {
        this.outBoundHandler = new MyHttpOutboundHandler(backendUrl);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            final FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            outBoundHandler.handle(fullHttpRequest, ctx);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
