package com.lis.custom.lambda.handler;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 *
 * @author dalibor
 */
public class DependencyFactory {

    private DependencyFactory() {
    }

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
            //    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.EU_WEST_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}
