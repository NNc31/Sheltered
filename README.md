# Sheltered

## Author

Nefodov Nazar
@Alphecca31

## Description

Web-application as master system of shelters.
Possible actions - add, edit, remove, find shelters.
Additional features - find the closest shelter, filter shelters by conditions or status.
Functionality for placing a volunteering requests is in development.

## Getting Started

### Dependencies

* Application requires Oracle Database with enabled listener

### Installing

* Download github repository
* application.properties required in resources folder
* prepare a build using Maven

Example of the application.properties folder:
```
spring.application.name=Sheltered
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.generate-ddl=true

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=mail_username@gmail.com
spring.mail.password=mail_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

administrator.mail=mail_of_registration_approver

bot.name=telegram_bot_name
bot.token=telegram_bot_api_token
```


### Executing program

* Run application on Local Machine with main class ua.edu.sumdu.nefodov.sheltered.ShelteredApplication or prepare a package and launch created JAR
