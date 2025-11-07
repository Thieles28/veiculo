package com.analise.veiculo.infrastructure.client;

import com.analise.veiculo.api.model.response.F3Response;
import com.analise.veiculo.api.model.response.MetricsResponse;
import com.analise.veiculo.api.model.response.SupplierStatusResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F3Client {
    public F3Response getVehicleData() {
        return F3Response.builder()
                .suppliers(List.of(
                        SupplierStatusResponse.builder()
                                .name("SupplierF3")
                                .status("OK")
                                .latencyMs(50)
                                .error(null)
                                .build()
                ))
                .metrics(MetricsResponse.builder()
                        .totalCostCents(0)
                        .build())
                .build();
    }
}