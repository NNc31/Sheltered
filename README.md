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
docker build -t sheltered-api-gateway:latest api-gateway
docker build -t sheltered-web-service:latest web-service
docker build -t sheltered-shelter-service:latest shelter-service
docker build -t sheltered-user-service:latest user-service
```
5. Run `docker-compose up -d`

## Deployment to Kubernetes
1. Prepare repository, JARs and Docker images as done for [Docker deployment](#installation-and-docker-deployment)
2. Prepare secret.yaml based on secret-template.yaml
3. Apply secret configuration `kubectl apply -f secret.yaml`
4. Apply deployment configuration `kubectl apply -f k8s-deployment.yaml`
5. Forward port for api gateway `kubectl port-forward service/api-gateway 8080:8080`

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
The project is distributed under [MIT](LICENSE.md) license.