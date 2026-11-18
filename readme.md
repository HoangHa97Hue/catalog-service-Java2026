| Kiến thức | Apply ở đâu |
|---|---|
| OOP, encapsulation | `Product`, `ProductImage` |
| Interface, composition, SOLID/DIP | `ImageStorage` → `S3ImageStorage` |
| Record | `CreateProductRequest`, `ProductResponse`, `PageResponse<T>` |
| List / Set / Map | categories, image content types, view counter |
| HashMap / ConcurrentHashMap | view counter + learning test |
| equals/hashCode | `ProductImage` value object + tests |
| Generics | `PageResponse<T>` |
| Comparator | Top viewed products |
| Functional Interface/Lambda | `computeIfAbsent`, `orElseThrow`, Stream |
| Stream API | categories normalization, Product → Response |
| Optional | `findById()` |
| String | product data, immutable |
| StringBuilder | catalog text report |
| StringBuffer | concurrency learning test, **không production** |
| Exception | custom exception + `@RestControllerAdvice` |
| synchronized | learning version của counter |
| AtomicInteger | production view counter |
| volatile | `ProductViewTrackingState` |
| deadlock | test/demo cách tránh |
| Thread / Executor / Virtual Thread | learning tests |
| Transaction | Create/Update Product |
| Isolation / locking | JPA `@Version` |
| JDBC | reporting/statistics read model |
| File I/O | CSV export + try-with-resources |
| JVM/Heap/GC | Spring Actuator metrics |
| JUnit | domain/unit tests |
| Mockito | service tests |
| Fake/Stub/Mock | `FakeImageStorage` + Mockito |
| TDD | image validation feature |
| Integration Testing | PostgreSQL Testcontainers |
| IoC/DI | constructor injection hiện tại |
| AOP | execution-time logging |