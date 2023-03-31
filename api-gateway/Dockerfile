FROM ghcr.io/graalvm/graalvm-ce:latest

# Sao chép mã nguồn của ứng dụng vào thư mục /app trong container
COPY . /app

# Di chuyển đến thư mục /app trong container
WORKDIR /app

# Cài đặt các phụ thuộc và công cụ cần thiết để sử dụng GraalVM
RUN gu install native-image

# Biên dịch ứng dụng thành một tệp thực thi bằng ngôn ngữ máy và thiết lập lệnh ENTRYPOINT để chạy tệp thực thi
ENTRYPOINT ["native-image", "-jar", "/app/target/app.jar"]