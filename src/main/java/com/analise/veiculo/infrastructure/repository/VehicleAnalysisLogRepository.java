package com.analise.veiculo.infrastructure.repository;

import com.analise.veiculo.domain.model.entity.VehicleAnalysisLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleAnalysisLogRepository extends JpaRepository<VehicleAnalysisLogEntity, UUID> {
}