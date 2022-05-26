package com.lis.custom.lambda.handler;

/**
 *
 * @author dalibor
 */
public interface LambdaHandler {

    String handle(String event) throws Exception;

}
