package com.nefodov.sheltered.userservice.controller;

import com.nefodov.sheltered.shared.model.LoginRequestDTO;
import com.nefodov.sheltered.shared.model.RegistrationApplicationDTO;
import com.nefodov.sheltered.shared.model.RegistrationRequestDTO;
import com.nefodov.sheltered.userservice.model.User;
import com.nefodov.sheltered.userservice.service.JwtService;
import com.nefodov.sheltered.userservice.service.UserService;
import com.nefodov.sheltered.userservice.service.mapper.RegistrationApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
public class UserRestController {

    private final UserService userService;
    private final RegistrationApplicationMapper applicationMapper;
    private final JwtService jwtService;

    @Autowired
    public UserRestController(UserService userService,
                              @Qualifier("registrationApplicationMapperImpl") RegistrationApplicationMapper applicationMapper,
                              JwtService jwtService) {
        this.userService = userService;
        this.applicationMapper = applicationMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/registration-application")
    public ResponseEntity<Void> registrationApplication(@RequestBody RegistrationApplicationDTO applicationDTO) {
        if (userService.saveRegistrationApplication(applicationMapper.toEntity(applicationDTO))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequestDTO requestDTO) {
        if (userService.registerUser(requestDTO)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {
        User user = userService.getUserByEmail(request.getEmail());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
