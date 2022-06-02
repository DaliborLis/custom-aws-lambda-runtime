FROM ghcr.io/graalvm/graalvm-ce:latest

RUN gu install native-image
WORKDIR /lambda
COPY ./target/custom-lambda-runtime-1.0-SNAPSHOT.jar ./
RUN native-image -jar custom-lambda-runtime-1.0-SNAPSHOT.jar --verbose --no-fallback
#RUN  microdnf install zip

ADD bootstrap bootstrap

RUN chmod +x bootstrap
RUN chmod +x ./custom-lambda-runtime-1.0-SNAPSHOT

#RUN zip -j function.zip bootstrap app


ENTRYPOINT ["./custom-lambda-runtime-1.0-SNAPSHOT"]