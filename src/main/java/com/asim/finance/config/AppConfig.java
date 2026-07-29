package com.asim.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
/**
 * project ke  root folder me docker file banate hai
 * # Stage 1: Build application
 * FROM maven:3.9.9-eclipse-temurin-17 AS build
 *
 * WORKDIR /app
 *
 * COPY pom.xml .
 *
 * RUN mvn dependency:go-offline
 *
 * COPY src ./src
 *
 * RUN mvn clean package -DskipTests
 *
 *
 * # Stage 2: Run application
 * FROM eclipse-temurin:17-jdk
 *
 * WORKDIR /app
 *
 * COPY --from=build /app/target/*.jar app.jar
 *
 *
 * EXPOSE 8080
 *
 *
 * ENTRYPOINT ["java","-jar","app.jar"]
 * or fir  root folder  mai hi compose .yaml file banate hai
 * services:
 *
 *   mysql-db:
 *
 *     image: mysql:8.0
 *
 *     container_name: finance-mysql
 *
 *     restart: always
 *
 *     environment:
 *       MYSQL_ROOT_PASSWORD: root
 *       MYSQL_DATABASE: finance_db
 *
 *
 *     ports:
 *       - "3307:3306"
 *
 *
 *     volumes:
 *       - mysql_data:/var/lib/mysql
 *
 *
 *
 *   finance-app:
 *
 *     build: .
 *
 *     container_name: finance-management-app
 *
 *
 *     depends_on:
 *       - mysql-db
 *
 *
 *     ports:
 *       - "8080:8080"
 *
 *
 *     environment:
 *
 *       SPRING_DATASOURCE_URL: jdbc:mysql://mysql-db:3306/finance_db
 *
 *       SPRING_DATASOURCE_USERNAME: root
 *
 *       SPRING_DATASOURCE_PASSWORD: root
 *
 *
 *
 *     restart: always
 *
 *
 *
 * volumes:
 *
 *   mysql_data:
 *
 *   agar project ko docker kre bina run krna haia to  application .properties  class ye line  honi chahiye
 *   spring.datasource.url=jdbc:mysql://localhost:3307/finance_db
 *   or agar docker mai hi run krna hai to  application.properties class ye honi chahiye
 *   spring.application.name=finance-management-system
 *
 *
 * server.port=8080
 *
 *
 * # MYSQL DOCKER CONNECTION
 *
 * spring.datasource.url=jdbc:mysql://mysql-db:3306/finance_db
 *
 * spring.datasource.username=root
 *
 * spring.datasource.password=root
 *
 * spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 *
 *
 *
 * # JPA
 *
 * spring.jpa.hibernate.ddl-auto=update
 *
 * spring.jpa.show-sql=true
 *
 * spring.jpa.properties.hibernate.format_sql=true
 *
 *
 *
 * # THYMELEAF
 *
 * spring.thymeleaf.cache=false
 *
 *
 *
 * # MAIL
 *
 * spring.mail.host=smtp.gmail.com
 *
 * spring.mail.port=587
 *
 * spring.mail.username=your-email@gmail.com
 *
 * spring.mail.password=your-app-password
 *
 * spring.mail.properties.mail.smtp.auth=true
 *
 * spring.mail.properties.mail.smtp.starttls.enable=true
 * docker run kaise karna hai
 * intellij mai
 *
 *   Example:
 *
 * D:\intellijidea\projectjuly\finance-management-system
 *
 * Command:
 *
 * 1. Image build
 * docker compose build
 * 2. Container start
 * docker compose up
 *
 * Ya background me:
 *
 * docker compose up -d
 *
 * Check containers:
 *
 * docker ps
 *
 * Output:
 *
 * CONTAINER ID   NAME
 * xxxxxxxx       finance-management-app
 * xxxxxxxx       finance-mysql
 * Application open:
 *
 * Browser:
 *
 * http://localhost:8080
 * MySQL Docker ke andar check karna:
 * docker exec -it finance-mysql mysql -uroot -proot
 *
 * Database:
 *
 * show databases;
 *
 * use finance_db;
 *
 * show tables;
 * Stop karna:
 * docker compose down
 * Aapke project ke liye important change
 *
 * Abhi aapke log me:
 *
 * Database JDBC URL [jdbc:mysql://localhost:3307/finance_db]
 *
 * aa raha hai.
 *
 * Docker ke andar ye hona chahiye:
 *
 * jdbc:mysql://mysql-db:3306/finance_db
 *
 * kyunki compose me service ka naam:
 *
 * mysql-db:
 *
 * hai.
 *
 *
 * Abhi aapka flow kuch aisa hai:
 *
 * User
 *  |
 * Browser
 *  |
 * Spring Boot Application (localhost:8080)
 *  |
 * MySQL Database
 *
 * Kubernetes ke baad:
 *
 *                  Kubernetes Cluster
 *
 *                  ┌───────────────┐
 * User  ─────────> │ Service        │
 *                  │ (LoadBalancer)│
 *                  └───────┬───────┘
 *                          |
 *                  ┌───────┴────────┐
 *                  │ Spring Boot Pod │
 *                  │ Finance App     │
 *                  └───────┬────────┘
 *                          |
 *                  ┌───────┴────────┐
 *                  │ MySQL Pod       │
 *                  │ Database        │
 *                  └────────────────┘
 * 1. Pehle Docker image banani hogi
 *
 * Aapke project ke root me:
 *
 * finance-management-system
 * |
 * |-- src
 * |-- pom.xml
 * |-- Dockerfile
 * |-- compose.yaml
 * 2. Dockerfile
 *
 * Project ke andar Dockerfile banaye:
 *
 * FROM eclipse-temurin:17-jdk
 *
 * WORKDIR /app
 *
 * COPY target/finance-management-system.jar app.jar
 *
 * EXPOSE 8080
 *
 * ENTRYPOINT ["java","-jar","app.jar"]
 * 3. Spring Boot jar banaye
 *
 * Terminal me:
 *
 * mvn clean package
 *
 * Output:
 *
 * target/
 *    finance-management-system.jar
 * 4. Docker image build kare
 * docker build -t finance-app .
 *
 * Check:
 *
 * docker images
 *
 * Output:
 *
 * finance-app
 * Kubernetes Configuration
 *
 * Ab hum Kubernetes files banayenge.
 *
 * Folder:
 *
 * k8s
 *  |
 *  |-- finance-deployment.yaml
 *  |-- finance-service.yaml
 *  |-- mysql-deployment.yaml
 *  |-- mysql-service.yaml
 *  |-- secret.yaml
 * 1. MySQL Secret
 *
 * secret.yaml
 *
 * apiVersion: v1
 * kind: Secret
 *
 * metadata:
 *   name: mysql-secret
 *
 * type: Opaque
 *
 * data:
 *
 *   username: cm9vdA==
 *
 *   password: cm9vdA==
 *
 * root ka base64 hai.
 *
 * 2. MySQL Deployment
 *
 * mysql-deployment.yaml
 *
 * apiVersion: apps/v1
 * kind: Deployment
 *
 * metadata:
 *   name: mysql
 *
 *
 * spec:
 *
 *   replicas: 1
 *
 *   selector:
 *     matchLabels:
 *       app: mysql
 *
 *
 *   template:
 *
 *     metadata:
 *       labels:
 *         app: mysql
 *
 *
 *     spec:
 *
 *       containers:
 *
 *       - name: mysql
 *
 *         image: mysql:8
 *
 *         ports:
 *
 *         - containerPort: 3306
 *
 *
 *         env:
 *
 *         - name: MYSQL_DATABASE
 *           value: finance_db
 *
 *
 *         - name: MYSQL_ROOT_PASSWORD
 *           valueFrom:
 *
 *             secretKeyRef:
 *
 *               name: mysql-secret
 *
 *               key: password
 * 3. MySQL Service
 *
 * mysql-service.yaml
 *
 * apiVersion: v1
 *
 * kind: Service
 *
 *
 * metadata:
 *
 *  name: mysql-db
 *
 *
 *
 * spec:
 *
 *  selector:
 *
 *   app: mysql
 *
 *
 *  ports:
 *
 *  - port:3306
 *
 *    targetPort:3306
 * 4. Finance Application Deployment
 *
 * finance-deployment.yaml
 *
 * apiVersion: apps/v1
 *
 * kind: Deployment
 *
 *
 * metadata:
 *
 *  name: finance-app
 *
 *
 *
 * spec:
 *
 *  replicas: 2
 *
 *
 *  selector:
 *
 *   matchLabels:
 *
 *    app: finance
 *
 *
 *
 * template:
 *
 *  metadata:
 *
 *   labels:
 *
 *    app: finance
 *
 *
 *
 *  spec:
 *
 *
 *   containers:
 *
 *
 *   - name: finance
 *
 *
 *     image: finance-app
 *
 *
 *     ports:
 *
 *
 *     - containerPort:8080
 *
 *
 *
 *     env:
 *
 *
 *     - name: SPRING_DATASOURCE_URL
 *
 *       value:
 *
 *        jdbc:mysql://mysql-db:3306/finance_db
 *
 *
 *
 *     - name: SPRING_DATASOURCE_USERNAME
 *
 *       value:
 *
 *        root
 *
 *
 *
 *     - name: SPRING_DATASOURCE_PASSWORD
 *
 *       value:
 *
 *        root
 * 5. Finance Service
 *
 * finance-service.yaml
 *
 * apiVersion: v1
 *
 * kind: Service
 *
 *
 * metadata:
 *
 *  name: finance-service
 *
 *
 *
 * spec:
 *
 *
 *  type: NodePort
 *
 *
 *  selector:
 *
 *
 *   app: finance
 *
 *
 *
 *  ports:
 *
 *
 *  - port:8080
 *
 *    targetPort:8080
 *
 *    nodePort:30080
 * Kubernetes run karna
 *
 * Agar aapke system me Minikube hai:
 *
 * Start:
 *
 * minikube start
 *
 * Check:
 *
 * kubectl get nodes
 *
 * Deployment:
 *
 * kubectl apply -f k8s/
 *
 * Check pods:
 *
 * kubectl get pods
 *
 * Output:
 *
 * mysql-xxxxx       Running
 * finance-app-xxx   Running
 * finance-app-yyy   Running
 *
 * Service check:
 *
 * kubectl get services
 *
 * Output:
 *
 * finance-service  NodePort  30080
 * mysql-db         ClusterIP
 *
 * Browser:
 *
 * http://localhost:30080
 *
 * Aapka Finance Management System Kubernetes ke andar run hoga.
 *
 * Aapke project me Kubernetes ka real use
 * 1. Multiple users ke liye scaling
 *
 * Aaj:
 *
 * 1 Spring Boot App
 *
 * Kal:
 *
 * finance-app Pod 1
 * finance-app Pod 2
 * finance-app Pod 3
 *
 * Command:
 *
 * kubectl scale deployment finance-app --replicas=5
 * 2. Automatic restart
 *
 * Agar application crash:
 *
 * Finance App stopped
 *         |
 *         |
 *  Kubernetes
 *         |
 *         |
 * New Pod create
 * 3. Zero downtime deployment
 *
 * Aap new version deploy kar sakte ho:
 *
 * Version 1
 *    |
 * Version 2
 *
 * User ko downtime nahi milega.
 *
 * 4. Production architecture
 *
 * Aapka final project architecture:
 *
 *                  Internet
 *
 *                     |
 *               Ingress Controller
 *
 *                     |
 *
 *              Finance Service
 *
 *                     |
 *
 *         -----------------------
 *         |          |          |
 *      Pod-1      Pod-2      Pod-3
 *      Spring     Spring     Spring
 *
 *
 *                     |
 *
 *               MySQL Service
 *
 *                     |
 *
 *              MySQL Database
 *
 */