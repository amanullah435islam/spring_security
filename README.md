🚀 Project Title

A Spring Boot application implementing JWT-based authentication and role-based authorization with full security.

📌 Features
🔐 JWT Authentication (Login & Register)
👤 Role-based Authorization (ADMIN, USER, EMPLOYEE)
🗄️ MySQL Database Integration
⚡ REST API with Spring Boot
🛠️ Tech Stack
Java 21
Spring Boot
Spring Security
Spring Data JPA
MySQL
Maven
📂 Project Structure

<p>
src/
├── controller/
├── service/
├── repository/
├── model/
├── security/
└── jwt/
├── enumm/
├── others/
└── boot/
</p>

⚙️ Installation & Setup
1️⃣ Clone the repository
git clone https://github.com/amanullah435islam/spring_security
2️⃣ Configure Database (application.properties)
spring.datasource.url=jdbc:mysql://localhost:3306/jee
spring.datasource.username=your_username
spring.datasource.password=your_password
3️⃣ Run the project
mvn spring-boot:run
🔐 API Endpoints
🟢 Register

POST /register

{
  "name": "Aman",
  "email": "aman@gmail.com",
  "password": "123456",
  "role": "USER"
}
🔵 Login

POST /login

{
  "email": "aman@gmail.com",
  "password": "123456"
}
🔑 Authorization

Add token in header:

Authorization: Bearer YOUR_TOKEN
🧪 Testing

Use Postman to test APIs.