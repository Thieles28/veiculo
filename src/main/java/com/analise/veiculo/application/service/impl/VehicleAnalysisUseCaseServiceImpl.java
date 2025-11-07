package com.analise.veiculo.application.service.impl;

import com.analise.veiculo.api.model.response.F1Response;
import com.analise.veiculo.api.model.response.F2Response;
import com.analise.veiculo.api.model.response.F3Response;
import com.analise.veiculo.api.model.response.VehicleAnalysisResponse;
import com.analise.veiculo.application.service.VehicleAnalysisUseCaseService;
import com.analise.veiculo.domain.model.entity.VehicleAnalysisLogEntity;
import com.analise.veiculo.infrastructure.client.F1Client;
import com.analise.veiculo.infrastructure.client.F2Client;
import com.analise.veiculo.infrastructure.client.F3Client;
import com.analise.veiculo.infrastructure.repository.VehicleAnalysisLogRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class VehicleAnalysisUseCaseServiceImpl implements VehicleAnalysisUseCaseService {

    private final F1Client f1Client;
    private final F2Client f2Client;
    private final F3Client f3Client;
    private final VehicleAnalysisLogRepository logRepository;

    @CircuitBreaker(name = "vehicleAnalysis", fallbackMethod = "fallback")
    @TimeLimiter(name = "vehicleAnalysis")
    @Retry(name = "vehicleAnalysis")
    public CompletableFuture<VehicleAnalysisResponse> execute(String idVeiculo) {
        return CompletableFuture.supplyAsync(() -> {
            String vin = normalizeId(idVeiculo);

            long start = System.currentTimeMillis();

            CompletableFuture<F1Response> f1Future = CompletableFuture.supplyAsync(f1Client::getVehicleData);
            CompletableFuture<F3Response> f3Future = CompletableFuture.supplyAsync(f3Client::getVehicleData);

            CompletableFuture.allOf(f1Future, f3Future).join();

            VehicleAnalysisResponse response = mergeResponses(f1Future.join(), f3Future.join());
            response.setVin(vin);

            if (response.getConstraints() != null &&
                    (response.getConstraints().isRenajud() || response.getConstraints().isRecall())) {
                F2Response f2Response = f2Client.getAdditionalData(vin);
                response = mergeF2(response, f2Response);
            }

            long end = System.currentTimeMillis();

            logRepository.save(VehicleAnalysisLogEntity.from(response, idVeiculo, end - start));

            return response;
        });
    }

    private String normalizeId(String id) {
        if (id.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
            return convertPlacaToVin(id);
        }
        if (id.matches("\\d{11}")) {
            return convertRenavamToVin(id);
        }
        return id;
    }

    private String convertPlacaToVin(String placa) {
        return placa;
    }

    private String convertRenavamToVin(String renavam) {
        return renavam;
    }

    private VehicleAnalysisResponse mergeResponses(F1Response f1, F3Response f3) {
        return VehicleAnalysisResponse.builder()
                .idInputType(f1.getIdInputType())
                .vinCanonical(f1.getVinCanonical())
                .constraints(f1.getConstraints())
                .infractions(f1.getInfractions())
                .metrics(f3.getMetrics())
                .suppliers(f3.getSuppliers())
                .traceId(UUID.randomUUID().toString())
                .build();
    }

    private VehicleAnalysisResponse mergeF2(VehicleAnalysisResponse base, F2Response f2) {
        return VehicleAnalysisResponse.builder()
                .vin(base.getVin())
                .idInputType(base.getIdInputType())
                .vinCanonical(base.getVinCanonical())
                .constraints(base.getConstraints())
                .infractions(base.getInfractions())
                .metrics(base.getMetrics())
                .suppliers(base.getSuppliers())
                .traceId(base.getTraceId())
                .build();
    }
}