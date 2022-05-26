
FROM debian:bullseye-slim as jre-17-build
ADD "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17%2B35/OpenJDK17-jdk_x64_linux_hotspot_17_35.tar.gz" "/opt/java/OpenJDK17-jdk_x64_linux_hotspot_17_35.tar.gz"


RUN ["apt-get", "update"]
RUN ["apt-get", "install", "-y", "binutils"]

RUN tar -xzf /opt/java/OpenJDK17-jdk_x64_linux_hotspot_17_35.tar.gz -C /opt/java/
RUN /opt/java/jdk-17+35/bin/jlink --add-modules java.base,java.net.http --output /opt/java/jdk-17_35-jre --strip-debug --no-man-pages --no-header-files --compress=2


FROM debian:bullseye-slim

RUN mkdir /lambda
WORKDIR /lambda

COPY --from=jre-17-build /opt/java/jdk-17_35-jre /opt/java/jre-17
COPY /target/custom-lambda-runtime-1.0-SNAPSHOT.jar /lambda/

ADD bootstrap bootstrap


ENTRYPOINT /opt/java/jre-17/bin/java -jar custom-lambda-runtime-1.0-SNAPSHOT.jar