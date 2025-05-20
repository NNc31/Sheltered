package com.nefodov.sheltered.webservice;

import com.nefodov.sheltered.shared.model.CoordinatesDTO;
import com.nefodov.sheltered.shared.model.ShelterCondition;
import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shared.model.ShelterStatus;
import com.nefodov.sheltered.webservice.controller.ShelterWebController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShelterWebController.class)
public class ShelterWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplate restTemplate;

    private ShelterDTO setUpShelter() {
        ShelterDTO shelter = new ShelterDTO();
        shelter.setCoordinates(new CoordinatesDTO(1.0, 1.0));
        shelter.setStatus(ShelterStatus.IN_USE);
        shelter.setConditions(Collections.singletonList(ShelterCondition.WATER));
        shelter.setCapacity(10);
        shelter.setArea(100);
        shelter.setAdditional("Test");
        return shelter;
    }

    @Test
    public void testIndex() throws Exception {
        ShelterDTO[] shelters = { new ShelterDTO(), new ShelterDTO() };
        when(restTemplate.getForObject(anyString(), eq(ShelterDTO[].class))).thenReturn(shelters);

        mockMvc.perform(get("/web-service/shelter/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("lat"))
                .andExpect(model().attributeExists("lng"))
                .andExpect(model().attribute("shelters", shelters))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(model().attributeExists("conditions"));
    }

    @Test
    public void testShowShelterSubmit() throws Exception {
        ShelterDTO shelter = setUpShelter();

        when(restTemplate.getForObject(anyString(), eq(ShelterDTO.class), anyMap())).thenReturn(shelter);

        mockMvc.perform(get("/web-service/shelter")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("shelter"));
    }

    @Test
    public void testAddShelter() throws Exception {
        ShelterDTO[] shelters = { new ShelterDTO(), new ShelterDTO() };
        when(restTemplate.getForObject(anyString(), eq(ShelterDTO[].class))).thenReturn(shelters);

        mockMvc.perform(get("/web-service/shelter/add-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("shelter"))
                .andExpect(model().attribute("shelters", shelters))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(model().attributeExists("conditions"));
    }

    @Test
    public void testAddShelterSubmit() throws Exception {
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(),
                eq(Void.class)
        )).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/web-service/shelter/add")
                        .param("coordinates.latitude", "1.0")
                        .param("coordinates.longitude", "1.0")
                        .param("status", "IN_USE")
                        .param("capacity", "100")
                        .param("conditions", "WATER", "FOOD")
                        .param("area", "1000.0")
                        .param("additional", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testEditShelter() throws Exception {
        ShelterDTO[] shelters = { new ShelterDTO(), new ShelterDTO() };
        when(restTemplate.getForObject(anyString(), eq(ShelterDTO[].class))).thenReturn(shelters);

        mockMvc.perform(get("/web-service/shelter/edit-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("shelter"))
                .andExpect(model().attribute("shelters", shelters))
                .andExpect(model().attributeExists("statuses"))
                .andExpect(model().attributeExists("conditions"));
    }

    @Test
    public void testEditShelterSubmit() throws Exception {
        Mockito.doNothing().when(restTemplate).put(anyString(), any(ShelterDTO[].class));

        mockMvc.perform(post("/web-service/shelter/add")
                        .param("coordinates.latitude", "1.0")
                        .param("coordinates.longitude", "1.0")
                        .param("status", "IN_USE")
                        .param("capacity", "100")
                        .param("conditions", "WATER", "FOOD")
                        .param("area", "1000.0")
                        .param("additional", "Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testDeleteShelter() throws Exception {
        ShelterDTO[] shelters = { new ShelterDTO(), new ShelterDTO() };
        when(restTemplate.getForObject(anyString(), eq(ShelterDTO[].class))).thenReturn(shelters);

        mockMvc.perform(get("/web-service/shelter/delete-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("delete"))
                .andExpect(model().size(3))
                .andExpect(model().attribute("shelters", shelters));
    }

    @Test
    public void testDeleteShelterSubmit() throws Exception {
        Mockito.doNothing().when(restTemplate).delete(anyString());

        mockMvc.perform(post("/web-service/shelter/delete")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
