package com.analise.veiculo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class F2Response {
    private String additionalInfo;
    private int latencyMs;
    private String error;
    private List<SupplierStatusResponse> suppliers;
    private ConstraintsResponse constraints;
    private InfractionsResponse infractions;
}
