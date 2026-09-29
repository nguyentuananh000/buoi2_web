# Stage 1: Build file .war bằng Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
# Copy cấu hình và mã nguồn
COPY pom.xml .
COPY src ./src
# Build dự án (bỏ qua test)
RUN mvn clean package -DskipTests

# Stage 2: Chạy web bằng Tomcat 9 (dùng Tomcat 9 vì code bạn đang dùng javax.*)
FROM tomcat:9.0-jdk17
WORKDIR /usr/local/tomcat

# Xóa các app mặc định của Tomcat cho sạch
RUN rm -rf webapps/*

# Copy file .war từ Stage 1 sang đổi tên thành ROOT.war để chạy thẳng ở trang chủ (/)
COPY --from=build /app/target/*.war webapps/ROOT.war

# Mở cổng 8080
EXPOSE 8080

# Chạy Tomcat
CMD ["catalina.sh", "run"]