package com.ecommerce.catalog.product.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private UUID id;
    private String name;
    private String description;
    @Column(precision = 19, scale = 2)
    private BigDecimal price;
    @Embedded
    private ProductImage image;

    @ElementCollection
    @CollectionTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();;

    //jpa can no-arg cóntructor de íntantiate entity
    protected Product() {   // de protected vi business code không nên tùy tiện tạo Product rỗng.
    }

    public Product(
            UUID id,
            String name,
            String description,
            BigDecimal price,
            ProductImage image,
            List<String> categories
    ) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Product ID cannot be null"
            );
        }
        this.image = image;
        this.categories = normalizeCategories(categories);
        //bussiness rule
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (price == null ||
                price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Price must be greater than zero"
            );
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<String> getCategories() { return categories;
    }

    public ProductImage getImage() {
        return image;
    }

    public void changeImage(ProductImage newImage){
        if(newImage == null){
            throw new IllegalArgumentException("Product image can not be null");
        }
        this.image = newImage;
    }

    private static List<String> normalizeCategories(List<String> categories){
        if(categories == null){
            return new ArrayList<>();
        }

        return new ArrayList<>(
          categories.stream()
                  .map(String::trim)
                  .filter(category -> !category.isBlank())
                  .distinct()
                  .toList()
        );
    }

}