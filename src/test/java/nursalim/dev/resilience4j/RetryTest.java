package nursalim.dev.resilience4j;

import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

@Slf4j
public class RetryTest {
    void callMe(){
        log.info("Try call me");
        throw new IllegalArgumentException("Ups error!!");
    }

    @Test
    void testRetry(){
        // create retry object
        Retry retry = Retry.ofDefaults("pzn");

        // wrap method with runnable if the return is void
        Runnable runnable = Retry.decorateRunnable(retry, () -> callMe());

        // execute runnable
        runnable.run();

//        callMe();
    }

    String hello(){
        log.info("Call say hello");
        throw new IllegalArgumentException("Ups error!!");
    }

    @Test
    void createRetrySupplier(){
        Retry retry = Retry.ofDefaults("pzn");
        Supplier<String> supplier = Retry.decorateSupplier(retry, () -> hello());
        supplier.get();

    }
}
