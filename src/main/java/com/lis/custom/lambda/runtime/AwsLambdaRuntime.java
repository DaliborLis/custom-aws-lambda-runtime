package com.lis.custom.lambda.runtime;

import com.lis.custom.lambda.runtime.model.InvocationResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.lis.custom.lambda.handler.LambdaHandler;

/**
 *
 * @author dalibor
 */
public class AwsLambdaRuntime {

    private static final String REQUEST_ID_HEADER = "lambda-runtime-aws-request-id";
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private final LambdaHandler lambdaCode;

    public AwsLambdaRuntime(LambdaHandler lambdaCode) {
        this.lambdaCode = lambdaCode;
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            String endpoint = System.getenv("AWS_LAMBDA_RUNTIME_API");
            var invocation = getNextInvocation(endpoint);

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(lambdaCode.handle(invocation.event())))
                        .uri(URI.create(String.format("http://%s/2018-06-01/runtime/invocation/%s/response", endpoint, invocation.requestId())))
                        .build();

                HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception ex) {
                handleInvocationError(endpoint, invocation, ex);
                Logger.getLogger(AwsLambdaRuntime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected InvocationResponse getNextInvocation(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("http://%s/2018-06-01/runtime/invocation/next", endpoint)))
                .build();

        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        String requestId = response.headers().firstValue(REQUEST_ID_HEADER).orElseThrow();
        return new InvocationResponse(requestId, response.body());
    }

    protected void handleInvocationError(String endpoint, InvocationResponse invocation, Exception exception) throws IOException, InterruptedException {
        // Post to Lambda error endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(exception.getMessage()))
                .uri(URI.create(String.format("http://%s/2018-06-01/runtime/invocation/%s/error", endpoint, invocation.requestId())))
                .build();
        HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
