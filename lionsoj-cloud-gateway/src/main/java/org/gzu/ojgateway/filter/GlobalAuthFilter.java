package org.gzu.ojgateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Classname: GlobalAuthFilter
 * @Description: 网关请求过滤拦截器
 * @Author: lions
 * @Datetime: 1/11/2024 1:44 AM
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * @Description: 拦截内部服务接口调用
     * @param exchange 请求相关信息封装
     * @param chain 过滤器链
     * @Return: http响应，无权限
     * @Author: lions
     * @Datetime: 1/11/2024 1:57 AM
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (pathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer wrappedData = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(wrappedData));
        }
        // TODO 统一权限校验（Token）
        return chain.filter(exchange);
    }

    /**
     * @Description: 设置拦截过滤优先级
     * @Return: 0：最高优先级
     * @Author: lions
     * @Datetime: 1/11/2024 2:13 AM
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
