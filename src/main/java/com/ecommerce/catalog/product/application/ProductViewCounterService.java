package com.ecommerce.catalog.product.application;

import com.ecommerce.catalog.common.ProductViewTrackingState;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductViewCounterService {
    private final ConcurrentHashMap<UUID, AtomicInteger> viewCount = new ConcurrentHashMap<>(); //nó được thiết kế để nhiều thread truy cập map an toàn mà không cần bạn tự khóa toàn bộ map bằng syncronized
    private final ProductViewTrackingState trackingState;

    public ProductViewCounterService(ProductViewTrackingState trackingState) {
        this.trackingState = trackingState;
    }

//// khong an toan neu lock contention. => nhieu thread cung tranh 1 lock
////    private final Map<UUID, Integer> viewCount = new HashMap<>();
////
////    public void increment(UUID productId) {
////        int current = viewCount.getOrDefault(productId, 0);
////        viewCount.put(productId, current + 1);
////    }


    public void increment(UUID productId) {

        if (!trackingState.isEnabled()) {
            return;
        }

        viewCount
                .computeIfAbsent(
                        productId,
                        id -> new AtomicInteger(0)   //cho phép tăng counter atomically: tuc la thao tac 1 khoi, khong the bi chen ngang boi thread khac
                )
                .incrementAndGet();
    }


    public int getCount(UUID productId){
        AtomicInteger counter =  viewCount.get(productId);   //atomicinteger for thread safe
        return counter == null ? 0 : counter.get();
    }
}
