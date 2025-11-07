package com.analise.veiculo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/f2")
public class F2MockController {
    @GetMapping("/vehicle/{vin}")
    public ResponseEntity<Map<String, Object>> getVehicleData(@PathVariable String vin) {
        Map<String, Object> dados = new HashMap<>();
        dados.put("vin", vin);
        dados.put("fines", 3);
        dados.put("infractions", List.of("Licenciamento atrasado", "IPVA pendente"));
        return ResponseEntity.ok(dados);
    }
}
