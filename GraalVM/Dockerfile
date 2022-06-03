FROM ghcr.io/graalvm/graalvm-ce:latest AS build-aot

RUN gu install native-image
WORKDIR /lambda
COPY ./target/custom-lambda-runtime-1.0-SNAPSHOT.jar ./
RUN native-image -jar custom-lambda-runtime-1.0-SNAPSHOT.jar --verbose --no-fallback

FROM debian:bullseye-slim
WORKDIR /lambda

COPY --from=build-aot /lambda/custom-lambda-runtime-1.0-SNAPSHOT ./

ADD GraalVM/bootstrap bootstrap

RUN chmod +x bootstrap
RUN chmod +x ./custom-lambda-runtime-1.0-SNAPSHOT


ENTRYPOINT ["./custom-lambda-runtime-1.0-SNAPSHOT"]