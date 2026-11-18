package com.ecommerce.catalog.product.infrastructure.storage;

import com.ecommerce.catalog.product.application.port.ImageStorage;
import com.ecommerce.catalog.product.application.port.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
public class S3ImageStorage implements ImageStorage {

    private final S3Client s3Client;
    private final String bucketName;

    public S3ImageStorage(
            S3Client s3Client,
            @Value("${aws.s3.bucket}") String bucketName
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public String upload(
            String objectKey,
            String contentType,
            byte[] content
    ) {

        try {

            s3Client.putObject(
                    request -> request
                            .bucket(bucketName)
                            .key(objectKey)
                            .contentType(contentType),
                    RequestBody.fromBytes(content)
            );

            return objectKey;

        } catch (S3Exception e) {

            throw new ImageStorageException(
                    "Failed to upload image to S3: " + objectKey,
                    e
            );

        } catch (SdkClientException e) {

            throw new ImageStorageException(
                    "Could not communicate with AWS S3",
                    e
            );
        }
    }
}