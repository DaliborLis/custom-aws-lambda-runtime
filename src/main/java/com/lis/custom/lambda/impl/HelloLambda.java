package com.lis.custom.lambda.impl;

/**
 *
 * @author dalibor
 */
public class HelloLambda implements LambdaCode {

    @Override
    public String handler(String event) throws Exception {
        return "Hello from Lambda: " + event;
    }
}
