package com.nefodov.sheltered.shelterservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nefodov.sheltered.shared.model.CoordinatesDTO;
import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shelterservice.controller.ShelterRestController;
import com.nefodov.sheltered.shelterservice.model.Coordinates;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import com.nefodov.sheltered.shelterservice.model.ShelterCondition;
import com.nefodov.sheltered.shelterservice.model.ShelterStatus;
import com.nefodov.sheltered.shelterservice.service.ShelterService;
import com.nefodov.sheltered.shelterservice.service.mapper.ShelterMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShelterRestController.class)
public class ShelterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ShelterService shelterService;

    @MockitoBean
    private ShelterMapper shelterMapper;

    private final Random RANDOM = new Random();

    private Shelter setUpShelter() {
        return new Shelter(new Coordinates(RANDOM.nextDouble(), RANDOM.nextDouble()),
                ShelterStatus.IN_USE,
                List.of(ShelterCondition.FOOD),
                RANDOM.nextInt(1000), RANDOM.nextDouble() * 100, "test");
    }

    private ShelterDTO copyShelter(Shelter shelter) {
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setCoordinates(new CoordinatesDTO(shelter.getCoordinates().getLatitude(), shelter.getCoordinates().getLongitude()));
        shelterDTO.setStatus(com.nefodov.sheltered.shared.model.ShelterStatus.IN_USE);
        shelterDTO.setConditions(List.of(com.nefodov.sheltered.shared.model.ShelterCondition.FOOD));
        shelterDTO.setCapacity(shelter.getCapacity());
        shelterDTO.setArea(shelter.getArea());
        shelterDTO.setAdditional(shelter.getAdditional());
        return shelterDTO;
    }

    @Test
    void testGetAllSuccess() throws Exception {
        Shelter shelter = setUpShelter();
        Mockito.when(shelterService.findAll()).thenReturn(List.of(shelter));
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(copyShelter(shelter));
        ShelterDTO shelterDTO = shelterMapper.toDTO(shelter);

        mockMvc.perform(get("/shelter-service"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].coordinates.latitude").value(shelterDTO.getCoordinates().getLatitude()))
                .andExpect(jsonPath("$[0].coordinates.longitude").value(shelterDTO.getCoordinates().getLongitude()))
                .andExpect(jsonPath("$[0].status").value(ShelterStatus.IN_USE.toString()))
                .andExpect(jsonPath("$[0].conditions", hasSize(1)))
                .andExpect(jsonPath("$[0].conditions[0]").value(ShelterCondition.FOOD.toString()))
                .andExpect(jsonPath("$[0].capacity").value(shelterDTO.getCapacity()))
                .andExpect(jsonPath("$[0].area").value(shelterDTO.getArea()))
                .andExpect(jsonPath("$[0].additional").value(shelterDTO.getAdditional()));
    }
/*
    @Test
    void findByCoords_shouldReturnShelter() throws Exception {
        Shelter shelter = new Shelter();
        ShelterDTO dto = new ShelterDTO();

        Mockito.when(shelterService.findByCoords(1.0, 2.0)).thenReturn(shelter);
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(dto);

        mockMvc.perform(get("/shelter-service/find")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isOk());
    }

    @Test
    void findByCoords_shouldReturnNotFound() throws Exception {
        Mockito.when(shelterService.findByCoords(anyDouble(), anyDouble())).thenReturn(null);

        mockMvc.perform(get("/shelter-service/find")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addShelter_shouldReturnSavedDto() throws Exception {
        ShelterDTO dto = new ShelterDTO();
        Shelter shelter = new Shelter();

        Mockito.when(shelterMapper.toEntity(any())).thenReturn(shelter);
        Mockito.when(shelterService.addShelter(shelter)).thenReturn(shelter);
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(dto);

        mockMvc.perform(post("/shelter-service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShelter_shouldReturnUpdatedDto() throws Exception {
        ShelterDTO dto = new ShelterDTO();
        Shelter shelter = new Shelter();

        Mockito.when(shelterMapper.toEntity(any())).thenReturn(shelter);
        Mockito.when(shelterService.updateShelter(shelter)).thenReturn(shelter);
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(dto);

        mockMvc.perform(put("/shelter-service/update")
                        .content(objectMapper.writeValueAsString(dto)))
                        .contentType(MediaType.APPLICATION_JSON)

                .andExpect(status().isOk());
    }

    @Test
    void deleteShelter_shouldCallService() throws Exception {
        mockMvc.perform(delete("/shelter-service/delete")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isOk());

        Mockito.verify(shelterService).deleteShelter(1.0, 2.0);
    }

    @Test
    void getSheltersCoordinates_shouldReturnList() throws Exception {
        Coordinates coords = new Coordinates(1.0, 2.0);
        Mockito.when(shelterService.getAllCoords()).thenReturn(List.of(coords));

        mockMvc.perform(get("/shelter-service/coords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitude").value(1.0))
                .andExpect(jsonPath("$[0].longitude").value(1.0));
    }

 */
}
