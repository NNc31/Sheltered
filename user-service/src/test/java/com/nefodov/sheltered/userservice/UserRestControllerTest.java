package com.nefodov.sheltered.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nefodov.sheltered.shared.model.LoginRequestDTO;
import com.nefodov.sheltered.shared.model.RegistrationApplicationDTO;
import com.nefodov.sheltered.shared.model.RegistrationRequestDTO;
import com.nefodov.sheltered.userservice.config.SecurityConfig;
import com.nefodov.sheltered.userservice.controller.UserRestController;
import com.nefodov.sheltered.userservice.model.RegistrationApplication;
import com.nefodov.sheltered.userservice.model.Role;
import com.nefodov.sheltered.userservice.model.User;
import com.nefodov.sheltered.userservice.service.JwtService;
import com.nefodov.sheltered.userservice.service.UserService;
import com.nefodov.sheltered.userservice.service.mapper.RegistrationApplicationMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
@Import(SecurityConfig.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean(name = "registrationApplicationMapperImpl")
    private RegistrationApplicationMapper applicationMapper;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void registrationApplicationTestSuccess() throws Exception {
        RegistrationApplicationDTO applicationDTO = new RegistrationApplicationDTO();

        applicationDTO.setEmail("email@test.com");
        applicationDTO.setOrganisation("test");
        applicationDTO.setPhone("+38099999999");
        applicationDTO.setFullName("test");

        Mockito.when(applicationMapper.toEntity(any())).thenReturn(new RegistrationApplication());
        Mockito.when(userService.saveRegistrationApplication(any())).thenReturn(true);

        mockMvc.perform(post("/user-service/registration-application")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(applicationDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void registrationApplicationTestFail() throws Exception {
        RegistrationApplicationDTO applicationDTO = new RegistrationApplicationDTO();
        applicationDTO.setEmail("email@test.com");
        applicationDTO.setOrganisation("test");
        applicationDTO.setPhone("+38099999999");
        applicationDTO.setFullName("test");

        Mockito.when(applicationMapper.toEntity(any())).thenReturn(new RegistrationApplication());
        Mockito.when(userService.saveRegistrationApplication(any())).thenReturn(false);

        mockMvc.perform(post("/user-service/registration-application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(applicationDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void registerTestSuccess() throws Exception {
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO();

        Mockito.when(userService.registerUser(any())).thenReturn(true);

        mockMvc.perform(post("/user-service/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void registerTestFail() throws Exception {
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO();
        Mockito.when(userService.registerUser(any())).thenReturn(false);

        mockMvc.perform(post("/user-service/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void loginTestSuccess() throws Exception {
        LoginRequestDTO requestDTO = new LoginRequestDTO();
        requestDTO.setPassword("password");
        User user = new User();
        user.setEmail("email@test.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        Mockito.when(userService.getUserByEmail(any())).thenReturn(user);
        Mockito.when(jwtService.generateToken(any())).thenReturn("token");

        mockMvc.perform(post("/user-service/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().encoding(StandardCharsets.UTF_8))
                .andExpect(content().string("token"));
    }

    @Test
    void loginTestFail() throws Exception {
        LoginRequestDTO requestDTO = new LoginRequestDTO();

        Mockito.when(userService.getUserByEmail(any())).thenReturn(null);

        mockMvc.perform(post("/user-service/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
    }
}
