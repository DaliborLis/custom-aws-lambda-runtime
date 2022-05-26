package com.lis.custom.lambda;

import com.lis.custom.lambda.handler.HelloLambda;
import com.lis.custom.lambda.runtime.AwsLambdaRuntime;
import java.io.IOException;

/**
 *
 * @author dalibor
 */
public class LambdaHandler {
    
    public static void main(String[] args) throws IOException, InterruptedException {
       new AwsLambdaRuntime(new HelloLambda()).run();
    }
    
}
