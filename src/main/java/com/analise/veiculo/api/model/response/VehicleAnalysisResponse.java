package com.analise.veiculo.api.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAnalysisResponse {
    private String vin;
    private String idInputType;
    private String vinCanonical;
    private ConstraintsResponse constraints;
    private InfractionsResponse infractions;
    private List<SupplierStatusResponse> suppliers;
    private MetricsResponse metrics;
    private String traceId;

    public static VehicleAnalysisResponse partialError(String error) {
        return VehicleAnalysisResponse.builder()
                .suppliers(Collections.singletonList(
                        SupplierStatusResponse.builder().name("unknown").status("error").error(error).build()))
                .build();
    }

    public String toSupplierJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(suppliers);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
