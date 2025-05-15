package com.nefodov.sheltered.shelterservice;

import com.nefodov.sheltered.shelterservice.exception.ShelterNotFoundException;
import com.nefodov.sheltered.shelterservice.model.Coordinates;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import com.nefodov.sheltered.shelterservice.model.ShelterStatus;
import com.nefodov.sheltered.shelterservice.repository.ShelterRepository;
import com.nefodov.sheltered.shelterservice.service.ShelterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ShelterService shelterService;

    @Test
    void testFindAll() {
        List<Shelter> shelters = List.of(new Shelter(), new Shelter());
        when(shelterRepository.findAll()).thenReturn(shelters);

        List<Shelter> result = shelterService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByCoordsFound() {
        Coordinates coords = new Coordinates(1, 1);
        Shelter shelter = new Shelter();
        when(shelterRepository.findById(coords)).thenReturn(Optional.of(shelter));

        Shelter result = shelterService.findByCoords(1, 1);
        assertNotNull(result);
    }

    @Test
    void testFindByCoordsNotFound() {
        when(shelterRepository.findById(any())).thenReturn(Optional.empty());

        Shelter result = shelterService.findByCoords(1, 1);
        assertNull(result);
    }

    @Test
    void testAddShelter() {
        Shelter shelter = new Shelter();
        when(shelterRepository.save(shelter)).thenReturn(shelter);

        Shelter result = shelterService.addShelter(shelter);
        assertEquals(shelter, result);
    }

    @Test
    void testUpdateShelterSuccess() {
        Shelter shelter = new Shelter();
        Coordinates coords = new Coordinates(1, 1);
        shelter.setCoordinates(coords);

        when(shelterRepository.existsById(coords)).thenReturn(true);
        when(shelterRepository.save(shelter)).thenReturn(shelter);

        Shelter result = shelterService.updateShelter(shelter);
        assertEquals(shelter, result);
    }

    @Test
    void testUpdateShelterThrowsIfNotFound() {
        Shelter shelter = new Shelter();
        Coordinates coords = new Coordinates(1, 1);
        shelter.setCoordinates(coords);

        when(shelterRepository.existsById(coords)).thenReturn(false);

        assertThrows(ShelterNotFoundException.class, () -> shelterService.updateShelter(shelter));
    }

    @Test
    void testDeleteShelter() {
        shelterService.deleteShelter(1, 1);
        verify(shelterRepository).deleteById(new Coordinates(1, 1));
    }

    @Test
    void testGetAllCoords() {
        List<Coordinates> coordsList = List.of(new Coordinates(1, 1));
        when(shelterRepository.findAllCoordinates()).thenReturn(coordsList);

        List<Coordinates> result = shelterService.getAllCoords();
        assertEquals(1, result.size());
    }

    //TODO: add test for coordinates precision in coords
}
