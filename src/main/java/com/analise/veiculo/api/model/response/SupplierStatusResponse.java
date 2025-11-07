package com.analise.veiculo.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierStatusResponse {
    private String name;
    private String status;
    private Integer latencyMs;
    private String error;
}
