1)Java record là gì?
    Record rất phù hợp cho DTO immutable.
    Thay vì viết:
    public class CreateProductRequest {

    private String name;
    private String description;

    public String getName() {
    return name;
    }

    ...
    }

ta viết:
public record CreateProductRequest(
String name,
String description,
BigDecimal price
) {
}

Java tự sinh:
constructor
accessors
equals
hashCode
toString

Access như:
request.name()
request.price()

không phải:
request.getName()