package com.analise.veiculo.api.controller;

import com.analise.veiculo.api.model.response.F3Response;
import com.analise.veiculo.api.model.response.SupplierStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/f3")
public class F3MockController {
    @GetMapping("/vehicle/{vin}")
    public ResponseEntity<F3Response> getVehicle(@PathVariable String vin) {
        F3Response response = F3Response.builder()
                .suppliers(List.of(
                        SupplierStatusResponse.builder()
                                .name("SupplierF3")
                                .status("OK")
                                .latencyMs(50)
                                .error(null)
                                .build()
                ))
                .build();
        return ResponseEntity.ok(response);
    }
}
