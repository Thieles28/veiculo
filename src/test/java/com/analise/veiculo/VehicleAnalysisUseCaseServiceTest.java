package com.analise.veiculo;

import com.analise.veiculo.api.model.response.ConstraintsResponse;
import com.analise.veiculo.api.model.response.F1Response;
import com.analise.veiculo.api.model.response.F2Response;
import com.analise.veiculo.api.model.response.F3Response;
import com.analise.veiculo.api.model.response.VehicleAnalysisResponse;
import com.analise.veiculo.application.service.impl.VehicleAnalysisUseCaseServiceImpl;
import com.analise.veiculo.infrastructure.client.F1Client;
import com.analise.veiculo.infrastructure.client.F2Client;
import com.analise.veiculo.infrastructure.client.F3Client;
import com.analise.veiculo.infrastructure.repository.VehicleAnalysisLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class VehicleAnalysisUseCaseServiceTest {

    @Mock
    private F1Client f1Client;

    @Mock
    private F2Client f2Client;

    @Mock
    private F3Client f3Client;

    @Mock
    private VehicleAnalysisLogRepository logRepository;

    @InjectMocks
    private VehicleAnalysisUseCaseServiceImpl vehicleAnalysisUseCaseService;

    @Test
    void testWithoutF1Restrictions_f2NotCalled() {
        String vin = "1HGCM82633A004352";

        F1Response f1Response = F1Response.builder()
                .constraints(new ConstraintsResponse(false, false))
                .build();
        F3Response f3Response = new F3Response();

        Mockito.when(f1Client.getVehicleData()).thenReturn(f1Response);
        Mockito.when(f3Client.getVehicleData()).thenReturn(f3Response);

        VehicleAnalysisResponse response = vehicleAnalysisUseCaseService.execute(vin).join();

        assertEquals(vin, response.getVin());
        assertNotNull(response.getConstraints());
        Mockito.verify(f2Client, Mockito.never()).getAdditionalData(Mockito.any());
    }

    @Test
    void testWithF1Restrictions_f2Called() {
        String vin = "1HGCM82633A004352";

        F1Response f1Response = F1Response.builder()
                .constraints(new ConstraintsResponse(true, false))
                .build();
        F3Response f3Response = new F3Response();
        F2Response f2Response = new F2Response();

        Mockito.when(f1Client.getVehicleData()).thenReturn(f1Response);
        Mockito.when(f3Client.getVehicleData()).thenReturn(f3Response);
        Mockito.when(f2Client.getAdditionalData(vin)).thenReturn(f2Response);

        VehicleAnalysisResponse response = vehicleAnalysisUseCaseService.execute(vin).join();

        assertEquals(vin, response.getVin());
        assertNotNull(response.getConstraints());
        Mockito.verify(f2Client, Mockito.times(1)).getAdditionalData(vin);
    }

    @Test
    void testFallback_whenExceptionThrown() {
        String vin = "1HGCM82633A004352";

        Mockito.when(f1Client.getVehicleData()).thenThrow(new RuntimeException("F1 down"));
        Mockito.when(f3Client.getVehicleData()).thenReturn(new F3Response());

        VehicleAnalysisResponse response = vehicleAnalysisUseCaseService.execute(vin).join();

        assertNotNull(response);
        assertTrue(response.getVin() == null || response.getVin().isEmpty());
        assertNull(response.getConstraints());
    }
}
