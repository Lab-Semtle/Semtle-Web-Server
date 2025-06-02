FROM openjdk:17-jdk-slim
# 타임존 설정
ENV TZ=Asia/Seoul
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]