package nursalim.dev.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
public class RetryRegistryTest {
    void callMe(){
        log.info("Try call me");
        throw new IllegalArgumentException("Ups error!!");
    }

    @Test
    void testRetryRegistry(){
        RetryRegistry retryRegistry = RetryRegistry.ofDefaults();

        Retry retry1 = retryRegistry.retry("pzn");
        Retry retry2 = retryRegistry.retry("pzn");

        Assertions.assertSame(retry1, retry2);

        Runnable runnable = Retry.decorateRunnable(retry1, () -> callMe());
        runnable.run();

    }

    @Test
    void testRetryRegistryConfig(){
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        RetryRegistry retryRegistry = RetryRegistry.ofDefaults();
        retryRegistry.addConfiguration("config", retryConfig);

        Retry retry1 = retryRegistry.retry("pzn", "config");
        Retry retry2 = retryRegistry.retry("pzn", "config");

        Assertions.assertSame(retry1, retry2);

        Runnable runnable = Retry.decorateRunnable(retry1, () -> callMe());
        runnable.run();

    }
}
