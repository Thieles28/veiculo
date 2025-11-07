package com.analise.veiculo.api.controller;

import com.analise.veiculo.api.model.response.VehicleAnalysisResponse;
import com.analise.veiculo.application.service.VehicleAnalysisUseCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("veiculos")
@RequiredArgsConstructor
public class VehicleAnalysisController {
    private final VehicleAnalysisUseCaseService vehicleAnalysisUseCaseService;

    @GetMapping("{idveiculo}/analise")
    public ResponseEntity<CompletableFuture<VehicleAnalysisResponse>> analyzeVehicle(
            @PathVariable String idveiculo) {
        return ResponseEntity.ok(vehicleAnalysisUseCaseService.execute(idveiculo));
    }
}