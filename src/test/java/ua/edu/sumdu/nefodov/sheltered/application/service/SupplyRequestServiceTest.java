package ua.edu.sumdu.nefodov.sheltered.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.edu.sumdu.nefodov.sheltered.application.model.SupplyRequest;
import ua.edu.sumdu.nefodov.sheltered.application.repository.SupplyRequestRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupplyRequestServiceTest {
    @Mock
    private SupplyRequestRepository srRepo;

    @InjectMocks
    private SupplyRequestService supplyRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        SupplyRequest req1 = new SupplyRequest();
        SupplyRequest req2 = new SupplyRequest();
        List<SupplyRequest> expectedRequests = Arrays.asList(req1, req2);
        when(srRepo.findAll()).thenReturn(expectedRequests);
        List<SupplyRequest> actualRequests = supplyRequestService.findAll();
        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void testFindById() {
        SupplyRequest req = new SupplyRequest();
        long id = 1;
        when(srRepo.findById(id)).thenReturn(Optional.of(req));
        SupplyRequest actualRequest = supplyRequestService.findById(id);
        assertEquals(req, actualRequest);
    }

    @Test
    void testFindByIdNotFound() {
        long id = 1;
        when(srRepo.findById(id)).thenReturn(Optional.empty());
        SupplyRequest actualRequest = supplyRequestService.findById(id);
        assertNull(actualRequest);
    }

    @Test
    void testAddSupplyRequest() {
        SupplyRequest req = new SupplyRequest();
        supplyRequestService.addSupplyRequest(req);
        assertNotNull(req.getSubmitDate());
        verify(srRepo, times(1)).save(req);
    }
}
