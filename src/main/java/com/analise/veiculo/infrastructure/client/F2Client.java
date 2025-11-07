package com.analise.veiculo.infrastructure.client;

import com.analise.veiculo.api.model.response.F2Response;
import com.analise.veiculo.api.model.response.SupplierStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class F2Client {

    private final WebClient webClient;

    public F2Response getAdditionalData(String vin) {
        return F2Response.builder()
                .suppliers(List.of(
                        SupplierStatusResponse.builder()
                                .name("SupplierF2")
                                .status("OK")
                                .latencyMs(50)
                                .error(null)
                                .build()
                ))
                .build();
    }
}