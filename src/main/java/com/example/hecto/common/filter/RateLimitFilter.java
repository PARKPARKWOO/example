package com.example.hecto.common.filter;

import com.example.hecto.common.annotation.RateLimiter;
import com.example.hecto.common.config.RateLimiterConfig;
import io.github.bucket4j.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

@Component
@Slf4j
public class RateLimitFilter implements WebFilter {
    private final ConcurrentMap<String, Bucket> bucketMap = new ConcurrentHashMap<>();
    private final static String RATE_LIMIT_HEADER = "X-Rate-Limit-Remaining";
    private final static String RATE_LIMIT_RESET_HEADER = "X-Rate-Limit-Reset";
    private final static String RATE_LIMIT_LIMIT_HEADER = "X-Rate-Limit-Limit";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return getHandlerMethod(exchange)
                .flatMap(handlerMethod -> {
                    if (handlerMethod != null && handlerMethod.isAnnotationPresent(RateLimiter.class)) {
                        RateLimiter rateLimiter = handlerMethod.getAnnotation(RateLimiter.class);
                        String key = exchange.getRequest().getHeaders().get(rateLimiter.key()).get(0);

                        Bucket bucket = bucketMap.computeIfAbsent(key, newBucket(rateLimiter, key));

                        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
                        long remainingTokens = probe.getRemainingTokens();
                        if (probe.isConsumed()) {
                            log.info("Remaining limit - {}", remainingTokens);
                            exchange.getResponse().getHeaders().add(RATE_LIMIT_HEADER, String.valueOf(probe.getRemainingTokens()));
                            exchange.getResponse().getHeaders().add(RATE_LIMIT_LIMIT_HEADER, String.valueOf(rateLimiter.quota()));
                            exchange.getResponse().getHeaders().add(RATE_LIMIT_RESET_HEADER, String.valueOf(bucket.getAvailableTokens()));
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                            return exchange.getResponse().setComplete();
                        }
                    }
                    return chain.filter(exchange);
                });
    }

    private Function<? super String, ? extends Bucket> newBucket(RateLimiter rateLimiter, String key) {
        return k -> {
            RateLimiterConfig limiter = RateLimiterConfig.custom()
                    .time(rateLimiter.time())
                    .key(key)
                    .timeUnit(rateLimiter.timeUnit())
                    .quota(rateLimiter.quota())
                    .build();
            return Bucket.builder()
                    .addLimit(BandwidthBuilder.builder()
                            .capacity(limiter.getQuota())
                            .refillIntervally(limiter.getQuota(),
                                    Duration.of(limiter.getTime(),
                                            limiter.getTimeUnit().toChronoUnit())).build()
                    ).build();
        };
    }

    private Mono<Method> getHandlerMethod(ServerWebExchange exchange) {
        return Mono.defer(() -> {
            try {
                RequestMappingHandlerMapping requestMappingHandlerMapping = exchange.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
                return requestMappingHandlerMapping.getHandler(exchange)
                        .mapNotNull(handler -> {
                            if (handler instanceof HandlerMethod) {
                                return ((HandlerMethod) handler).getMethod();
                            }
                            return null;
                        });
            } catch (Exception e) {
                log.error("Failed to get handler method", e);
                return Mono.empty();
            }
        });
    }
}
