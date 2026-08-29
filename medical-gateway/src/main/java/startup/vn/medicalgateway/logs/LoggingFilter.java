package startup.vn.medicalgateway.logs;

import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var remoteAddress = exchange.getRequest().getRemoteAddress();
        var ipAddress = remoteAddress != null && remoteAddress.getAddress() != null
                ? remoteAddress.getAddress().getHostAddress()
                : "unknown";
        var path = exchange.getRequest().getPath().toString();
        var method = exchange.getRequest().getMethod() != null
                ? exchange.getRequest().getMethod().toString()
                : "UNKNOWN";
        System.out.println("[Medical Gateway Log]" +
                " Client IP: " + ipAddress +
                " | Method:  " + method +
                " | Path " + path);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
