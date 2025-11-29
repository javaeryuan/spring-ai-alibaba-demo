# 使用OpenJDK 21作为基础镜像
FROM openjdk:21-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制Maven构建的JAR文件到镜像中
COPY target/spring-ai-alibaba-demo-1.0-SNAPSHOT.jar app.jar

# 暴露应用端口
EXPOSE 8080

# 设置环境变量
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]