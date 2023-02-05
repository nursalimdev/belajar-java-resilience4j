package nursalim.dev.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
public class RetryConfigTest {

    String hello(){
        log.info("call hello()");
        throw new IllegalArgumentException("Ups Error!!");
    }

    @Test
    void testRetryConfig(){
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(2))
                .retryExceptions(IllegalArgumentException.class)
                .ignoreExceptions(RuntimeException.class)
                .build();

        Retry retry = Retry.of("pzn", retryConfig);
        Supplier<String> supplier = Retry.decorateSupplier(retry, this::hello);
        supplier.get();

    }
}
