package idv.angus.gateway;

import idv.angus.gateway.inbound.MyHttpInboundServer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyServerApplication {
    public static final int gatewayPort = 8888;
    // run HttpServer01,HttpServer02, HttpServer03 as backend service
    private static final String backendUrl = "http://localhost:8801,http://localhost:8802,http://localhost:8803";

    public static void main(String[] args) {
        log.info("My Netty API Gateway starting...");
        final MyHttpInboundServer httpInboundServer = new MyHttpInboundServer(gatewayPort, backendUrl);
        log.info("My Netty API Gateway start at: http://localhost:{}", gatewayPort);
        try {
            httpInboundServer.run();
        } catch (InterruptedException e) {
            log.error("My Netty API Gateway start failed.", e);
        }
    }
}
