package ua.edu.sumdu.nefodov.sheltered.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenericController.class)
@AutoConfigureMockMvc
class GenericControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/shelter/home"))
                .andExpect(view().name("forward:/shelter/home"));
    }

    @Test
    @WithMockUser
    void testApiInfo() throws Exception {
        mockMvc.perform(get("/api-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("api-info"));
    }

    @Test
    @WithMockUser
    void testProjectInfo() throws Exception {
        mockMvc.perform(get("/project-info"))
                .andExpect(status().isOk())
                .andExpect(view().name("project-info"));
    }
}