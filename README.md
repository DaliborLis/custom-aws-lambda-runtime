# custom-aws-lambda-runtime
Custom implementation of the AWS Lambda runtime in Java

The main goal of the project is to play a little bit with custom implementation of [AWS Lambda runtime API](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-api.html).

Another step is compilation to native image by using GraalVM.

### Docker build
```bash
docker build -t custom-lambda-runtime-graal -f GraalVM/Dockerfile .
```

### Docker build to native image by using GraalVM

```bash
docker build -t custom-lambda-runtime-graal -f GraalVM/Dockerfile .
```
