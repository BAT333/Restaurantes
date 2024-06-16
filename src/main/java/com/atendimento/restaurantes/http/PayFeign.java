package com.atendimento.restaurantes.http;

import com.atendimento.restaurantes.http.Config.FeignConfig;
import com.atendimento.restaurantes.model.DataPayDTO;
import com.atendimento.restaurantes.model.RegisterDataPayDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "econix",configuration = FeignConfig.class)
public interface PayFeign {
    @RequestMapping(method = RequestMethod.POST,value = "/pay")
    DataPayDTO registerPay(@RequestBody @Valid RegisterDataPayDTO dto);

}
