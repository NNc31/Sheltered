package com.nefodov.sheltered.shelterservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nefodov.sheltered.shared.model.CoordinatesDTO;
import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shelterservice.controller.ShelterRestController;
import com.nefodov.sheltered.shelterservice.exception.ShelterNotFoundException;
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
        ShelterDTO dto = new ShelterDTO();
        dto.setCoordinates(new CoordinatesDTO(shelter.getCoordinates().getLatitude(),
                shelter.getCoordinates().getLongitude()));
        dto.setArea(shelter.getArea());
        dto.setCapacity(shelter.getCapacity());
        dto.setStatus(com.nefodov.sheltered.shared.model.ShelterStatus.IN_USE);
        dto.setConditions(List.of(com.nefodov.sheltered.shared.model.ShelterCondition.FOOD));
        dto.setAdditional(shelter.getAdditional());
        return dto;
    }

    @Test
    void testGetAllSuccess() throws Exception {
        Shelter shelter = setUpShelter();
        ShelterDTO dto = copyShelter(shelter);

        Mockito.when(shelterService.findAll()).thenReturn(List.of(shelter));
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(dto);

        mockMvc.perform(get("/shelter-service"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].coordinates.latitude").value(shelter.getCoordinates().getLatitude()))
                .andExpect(jsonPath("$[0].coordinates.longitude").value(shelter.getCoordinates().getLongitude()))
                .andExpect(jsonPath("$[0].status").value(ShelterStatus.IN_USE.toString()))
                .andExpect(jsonPath("$[0].conditions", hasSize(1)))
                .andExpect(jsonPath("$[0].conditions[0]").value(ShelterCondition.FOOD.toString()))
                .andExpect(jsonPath("$[0].capacity").value(shelter.getCapacity()))
                .andExpect(jsonPath("$[0].area").value(shelter.getArea()))
                .andExpect(jsonPath("$[0].additional").value(shelter.getAdditional()));
    }

    @Test
    void testFindByCoordsSuccess() throws Exception {
        Shelter shelter = setUpShelter();
        ShelterDTO dto = copyShelter(shelter);

        double lat = shelter.getCoordinates().getLatitude();
        double lng = shelter.getCoordinates().getLongitude();

        Mockito.when(shelterService.findByCoords(lat, lng)).thenReturn(shelter);
        Mockito.when(shelterMapper.toDTO(shelter)).thenReturn(dto);

        mockMvc.perform(get("/shelter-service/find")
                        .param("lat", String.valueOf(lat))
                        .param("lng", String.valueOf(lng)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.coordinates.latitude").value(shelter.getCoordinates().getLatitude()))
                .andExpect(jsonPath("$.coordinates.longitude").value(shelter.getCoordinates().getLongitude()))
                .andExpect(jsonPath("$.status").value(ShelterStatus.IN_USE.toString()))
                .andExpect(jsonPath("$.conditions", hasSize(1)))
                .andExpect(jsonPath("$.conditions[0]").value(ShelterCondition.FOOD.toString()))
                .andExpect(jsonPath("$.capacity").value(shelter.getCapacity()))
                .andExpect(jsonPath("$.area").value(shelter.getArea()))
                .andExpect(jsonPath("$.additional").value(shelter.getAdditional()));
    }

    @Test
    void testFindByCoordsFail() throws Exception {
        Mockito.when(shelterService.findByCoords(anyDouble(), anyDouble())).thenThrow(ShelterNotFoundException.class);

        mockMvc.perform(get("/shelter-service/find")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addShelter() throws Exception {
        Shelter shelter = setUpShelter();
        ShelterDTO dto = copyShelter(shelter);
        Mockito.when(shelterMapper.toEntity(any())).thenReturn(shelter);
        Mockito.when(shelterService.addShelter(shelter)).thenReturn(shelter);

        mockMvc.perform(post("/shelter-service/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShelter() throws Exception {
        Shelter shelter = setUpShelter();
        ShelterDTO dto = copyShelter(shelter);
        Mockito.when(shelterMapper.toEntity(any())).thenReturn(shelter);
        Mockito.when(shelterService.updateShelter(shelter)).thenReturn(shelter);

        mockMvc.perform(put("/shelter-service/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShelterSuccess() throws Exception {
        Mockito.doNothing().when(shelterService).deleteShelter(anyDouble(), anyDouble());

        mockMvc.perform(delete("/shelter-service/delete")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isOk());

        Mockito.verify(shelterService).deleteShelter(1.0, 1.0);
    }

    @Test
    void deleteShelterFail() throws Exception {
        Mockito.doThrow(ShelterNotFoundException.class)
                .when(shelterService)
                .deleteShelter(anyDouble(), anyDouble());

        mockMvc.perform(delete("/shelter-service/delete")
                        .param("lat", "1.0")
                        .param("lng", "1.0"))
                .andExpect(status().isInternalServerError());

        Mockito.verify(shelterService).deleteShelter(1.0, 1.0);
    }
}
