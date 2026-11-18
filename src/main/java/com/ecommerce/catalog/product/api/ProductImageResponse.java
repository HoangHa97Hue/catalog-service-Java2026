package com.ecommerce.catalog.product.api;

import com.ecommerce.catalog.product.domain.ProductImage;

public record ProductImageResponse ( //only metadata, will add imageurl for FE displaying
    String originalFileName,
    String contentType,
    Long size
){
    //FE dont need object key for displaying
    public static ProductImageResponse from(
            ProductImage image
    ){
        if(image == null){
            return null;
        }

        return new ProductImageResponse(
                image.getOriginalFileName(),
                image.getContentType(),
                image.getSize()
        );
    }

    /*\ Thay vi
    ProductImageResponse response = new ProductImageResponse(
        image.getOriginalFileName(),
        image.getContentType(),
        image.getSize()
        );

        thi gio , tranh lap get set nhieu noi
        ProductImageResponse response =
        ProductImageResponse.from(image);
     */
}
