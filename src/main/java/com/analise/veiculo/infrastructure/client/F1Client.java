package com.analise.veiculo.infrastructure.client;

import com.analise.veiculo.api.model.response.ConstraintsResponse;
import com.analise.veiculo.api.model.response.F1Response;
import com.analise.veiculo.api.model.response.InfractionsResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class F1Client {

    public F1Response getVehicleData() {
        String simulatedVin = "9BWZZZ377VT004251";

        List<InfractionsResponse> infractions = List.of(
                InfractionsResponse.builder()
                        .count(1)
                        .hasInfractions(true)
                        .build(),
                InfractionsResponse.builder()
                        .count(2)
                        .hasInfractions(true)
                        .build()
        );

        return F1Response.builder()
                .idInputType(simulatedVin)
                .vinCanonical(simulatedVin)
                .constraints(ConstraintsResponse.builder()
                        .renajud(true)
                        .recall(false)
                        .build())
                .infractions(InfractionsResponse.builder()
                        .count(infractions.size())
                        .hasInfractions(true)
                        .build())
                .suppliers(Collections.emptyList())
                .latencyMs(100)
                .error(null)
                .build();
    }
}