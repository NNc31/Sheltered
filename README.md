# Sheltered
Spring Boot web application as master system of shelters.
Interactive map based on Google Maps API for location selection.
Allows CRUD operations, registration, finding the closest shelter, filtering shelters by conditions or status.
Functionality for placing a volunteering requests is in development.

## Installation and Docker deployment
1. Clone repository `git clone https://github.com/NNc31/Sheltered.git`
2. Prepare .env file (example in [Configuration](#Configuration))
3. Build project JARs with `mvn clean package -DskipTests`
4. Prepare images for all modules
```
docker build -t api-gateway:latest api-gateway
docker build -t web-service:latest web-service
docker build -t shelter-service:latest shelter-service
docker build -t user-service:latest user-service
```
5. Run `docker-compose up -d`

## Configuration
Example of .env file with all necessary variables
```
POSTGRES_DB=db_name
POSTGRES_USER=db_user
POSTGRES_PASSWORD=db_password
SPRING_DATASOURCE_URL=db_jdbc_link
SPRING_DATASOURCE_USERNAME=db_user
SPRING_DATASOURCE_PASSWORD=db_password
ADMINISTRATOR_EMAIL=email_for_sending_registration_requests
ADMINISTRATOR_EMAIL_PASSWORD=password_for_email_api_if_required
JWT_SECRET=jwt_secret_32+_chars
```

## Author
Nefodov Nazar

## License
The project is distributed under [MIT] license [MIT](LICENSE.md).