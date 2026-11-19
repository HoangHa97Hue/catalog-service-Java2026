package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.common.ProductViewTrackingState;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductViewCounterServiceTest {

    @Test
    void increment_shouldRemainCorrectWithConcurrentThreads() throws InterruptedException {
        ProductViewTrackingState trackingState = new ProductViewTrackingState();
        ProductViewCounterService service = new ProductViewCounterService(trackingState);
        UUID productId = UUID.randomUUID();

        int threadCount = 10;
        int incrementsPerThread = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    start.await();
                    for (int j = 0; j < incrementsPerThread; j++) {
                        service.increment(productId);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        start.countDown();
        assertTrue(done.await(5, TimeUnit.SECONDS));
        executor.shutdownNow();

        assertThat(service.getCount(productId))
                .isEqualTo(threadCount * incrementsPerThread);
    }

    @Test
    void increment_shouldDoNothingWhenTrackingIsDisabled() {
        ProductViewTrackingState trackingState = new ProductViewTrackingState();
        ProductViewCounterService service = new ProductViewCounterService(trackingState);
        UUID productId = UUID.randomUUID();

        trackingState.disable();
        service.increment(productId);

        assertThat(service.getCount(productId)).isZero();
    }
}
