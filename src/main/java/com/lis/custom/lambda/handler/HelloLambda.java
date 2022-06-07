package com.lis.custom.lambda.handler;

import java.io.ByteArrayOutputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

/**
 *
 * @author dalibor
 */
public class HelloLambda implements LambdaHandler {

    private static final S3Client s3Client = DependencyFactory.s3Client();

    @Override
    public String handle(String event) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        try ( var inputStream = s3Client.getObject(createGetObjectRequest())) {
            while ((length = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        }

        return "Size of object: " + baos.size();
    }

    private GetObjectRequest createGetObjectRequest() {
        return GetObjectRequest.builder().bucket("test-bucket-delete-free")
                .key("image-jellyfish.jpg")
                .build();
    }
}
