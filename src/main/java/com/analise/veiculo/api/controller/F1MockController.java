package com.analise.veiculo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soap/f1")
public class F1MockController {
    @PostMapping
    public ResponseEntity<String> responder() {
        String xml = """
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
               <soapenv:Body>
                  <GetVehicleDataResponse>
                     <renajud>true</renajud>
                     <recall>false</recall>
                  </GetVehicleDataResponse>
               </soapenv:Body>
            </soapenv:Envelope>
        """;
        return ResponseEntity.ok(xml);
    }
}