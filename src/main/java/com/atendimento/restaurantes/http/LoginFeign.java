package com.atendimento.restaurantes.http;

import com.atendimento.restaurantes.http.Config.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name ="userSeguranca",configuration = FeignConfig.class)
public interface LoginFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/login/auth")
    @CircuitBreaker(name = "erroServers")
    List<String> crede(String token);

    @RequestMapping(method = RequestMethod.POST, value = "/login/register")
    DataLoginsDTO register(@RequestBody @Valid DataLoginsDTO dto);

    @RequestMapping(method = RequestMethod.PUT,value = "/login/update")
    DataLoginsDTO update(@RequestBody @Valid DataLoginsDTO dto);

}
