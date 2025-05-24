package com.nefodov.sheltered.webservice;

import com.nefodov.sheltered.shared.model.RegistrationRequestDTO;
import com.nefodov.sheltered.webservice.controller.UserWebController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(UserWebController.class)
public class UserWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void registrationApplicationTest() throws Exception {
        mockMvc.perform(get("/web-service/user/registration-application"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration-application"))
                .andExpect(model().attributeExists("application"));
    }

    @Test
    void registrationApplicationSubmitTest() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(null);

        mockMvc.perform(post("/web-service/user/registration-application"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/web-service/user/registration-application"));
    }

    @Test
    void registrationTest() throws Exception {
        mockMvc.perform(get("/web-service/user/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    void registrationSubmitTestSuccess() throws Exception {
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        mockMvc.perform(post("/web-service/user/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void registrationSubmitTestFail() throws Exception {
        RegistrationRequestDTO request = new RegistrationRequestDTO();
        request.setEmail("email");
        request.setPassword("password");
        request.setAccessKey("accessKey");

        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        mockMvc.perform(post("/web-service/user/registration")
                        .param("email", "email@example.com")
                        .param("password", "password123")
                        .param("accessKey", "test-access-key"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    void loginTest() throws Exception {
        mockMvc.perform(get("/web-service/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    void loginSubmitTestSuccess() throws Exception {
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        mockMvc.perform(post("/web-service/user/login"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/web-service/shelter/home"));
    }

    @Test
    void loginSubmitTestFail() throws Exception {
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Mockito.when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        mockMvc.perform(post("/web-service/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void logoutTest() throws Exception {

    }
}
