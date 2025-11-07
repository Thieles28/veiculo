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
public class F3Response {
    private List<SupplierStatusResponse> suppliers;
    private MetricsResponse metrics;
    private int latencyMs;
    private String error;
}
