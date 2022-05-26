package com.lis.custom.lambda.handler;

/**
 *
 * @author dalibor
 */
public class HelloLambda implements LambdaHandler {

    @Override
    public String handle(String event) throws Exception {
        return "Hello from Lambda: " + event;
    }
}
