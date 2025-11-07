package com.analise.veiculo.application.service;

import com.analise.veiculo.api.model.response.VehicleAnalysisResponse;

import java.util.concurrent.CompletableFuture;

public interface VehicleAnalysisUseCaseService {
    CompletableFuture<VehicleAnalysisResponse> execute(String idVeiculo);
}