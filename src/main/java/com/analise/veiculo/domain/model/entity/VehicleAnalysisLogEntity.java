package com.analise.veiculo.domain.model.entity;

import com.analise.veiculo.api.model.response.SupplierStatusResponse;
import com.analise.veiculo.api.model.response.VehicleAnalysisResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicle_analysis_log")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleAnalysisLogEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String idInputType;
    private String idInputValue;
    private String vinCanonical;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private JsonNode supplierCalls;

    private Boolean hasConstraints;
    private Integer estimatedCostCents;
    private String traceId;
    private long executionTimeMs;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static VehicleAnalysisLogEntity from(VehicleAnalysisResponse response, String inputValue, long executionTimeMs) {
        return VehicleAnalysisLogEntity.builder()
                .idInputType(determineInputType(inputValue))
                .idInputValue(inputValue)
                .vinCanonical(response.getVin())
                .supplierCalls(safeSupplierJson(response))
                .hasConstraints(response.getConstraints() != null &&
                        (response.getConstraints().isRenajud() || response.getConstraints().isRecall()))
                .estimatedCostCents(response.getMetrics() != null ? response.getMetrics().getTotalCostCents() : 0)
                .traceId(response.getTraceId())
                .executionTimeMs(executionTimeMs)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private static String determineInputType(String input) {
        if (input.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) return "PLACA";
        if (input.matches("[0-9]{11}")) return "RENAVAM";
        return "VIN";
    }

    private static JsonNode safeSupplierJson(VehicleAnalysisResponse response) {
        try {
            List<SupplierStatusResponse> suppliers = response.getSuppliers();
            if (suppliers == null) {
                suppliers = new ArrayList<>();
            }
            return OBJECT_MAPPER.valueToTree(suppliers);
        } catch (Exception e) {
            return OBJECT_MAPPER.createArrayNode();
        }
    }
}