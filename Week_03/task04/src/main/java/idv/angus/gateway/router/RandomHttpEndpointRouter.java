package idv.angus.gateway.router;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomHttpEndpointRouter {
    public String route(List<String> backends) {
        return backends.get(ThreadLocalRandom.current().nextInt(backends.size()));
    }
}
