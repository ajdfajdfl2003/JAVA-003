package idv.angus.gateway;

import idv.angus.gateway.inbound.MyHttpInboundServer;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class NettyServerApplication {
    public static final int gatewayPort = 8888;
    // run HttpServer01,HttpServer02, HttpServer03 as backend service
    private static final String backendUrls = "http://localhost:8801,http://localhost:8802,http://localhost:8803";

    public static void main(String[] args) {
        log.info("My Netty API Gateway starting...");
        final List<String> backends = Arrays.stream(backendUrls.split(",")).collect(Collectors.toList());
        final MyHttpInboundServer httpInboundServer = new MyHttpInboundServer(gatewayPort, backends);
        log.info("My Netty API Gateway start at: http://localhost:{}", gatewayPort);
        try {
            httpInboundServer.run();
        } catch (InterruptedException e) {
            log.error("My Netty API Gateway start failed.", e);
        }
    }
}
