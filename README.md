# Product Shop Rest API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  ![Postgres](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white) ![OpenApi](https://img.shields.io/badge/Docs-OpenAPI-success?style=for-the-badge&logo=swagger)
![SpringSecurity](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)

I developed a Rest API to manage products, customers and their orders in a shop, built by using **Spring Boot, Java, and Postgres as the Database**, providing CRUD (Create, Read, Update, Delete) operations with authentication control enabled through **Spring Security and JWT tokens**. This API allows storing product information, such as: name, description, price, stock quantity, and the associated category, which contains its own name, description, and image. It also supports storing order information such as: payment method, purchase total price, address, phone number, and more. Finally, to ensure security, storing user information such as username and password is necessary for all user types, including admin, manager and customer.

I used some libraries for this Rest API such **Spring Web, Spring Data JPA, Validation, ModelMapper, PostgreSQL Driver, SpringDoc OpenAPI Starter WebMVC UI (for the API documentation), Spring Security and java-jwt**.

## Requirements

Below are some business rules that are essential for the system's functionality:

• Each user must register with a unique email and document number. Duplicate entries for either field are not allowed.

• A user can have multiple orders, while each order belongs to only one user. A product belongs to only one category, while each category can include multiple products.

• An order can contain multiple products, and each product can be part of multiple orders.

• Allows the creation, reading, updating, and removal (CRUD) of users, products, categories, and orders from the system, considering security roles and permissions.

• Permissions and allowed operations vary depending on the assigned role (see Authentication section).

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role, limited to standard store operations (e.g., browsing products, placing orders, and managing their own account).
MANAGER -> Management role with permissions to perform core store management tasks (e.g., managing products, inventory, and orders). 
ADMIN -> Administrative role with full access (e.g., manage users, roles, products, orders, and overall system settings).
```

## Database Initialization with Default Data
For this project, default users have been created using the data.sql file.

The default users were created with the following credentials:

- Username: pedro, Password: 123 (USER role).

- Username: marcos, Password: 123 (both USER and ADMIN roles).

## Database Config
For this API, the Postgres Database was used with the following configuration properties: 

- Database name: recipe_management_system_db
- Username: postgres
- Password: 123456

## Database Initialization Configuration
To run the application correctly, for the first time make sure the database already exists and set the following configuration in the application.properties file:

```
spring.sql.init.mode=always
```

For subsequent runs (once the database and its tables are already created), change the setting to:

```
spring.sql.init.mode=embedded
```

## Development Tools
This Rest API was built with:

- Spring Boot version: 3.4.2
- Java version: 17

## System Class Diagram

![SystemClassDiagram](https://github.com/user-attachments/assets/981d652c-583b-49a2-9253-e7ddabe82490)



