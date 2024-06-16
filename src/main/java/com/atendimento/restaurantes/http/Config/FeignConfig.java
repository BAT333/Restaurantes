package com.atendimento.restaurantes.http.Config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignConfig implements RequestInterceptor {
     /*
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + getTokenJWT());
    }
    private String getTokenJWT() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            String tokenJTW= request.getHeader("Authorization");
            if(tokenJTW != null){
                return tokenJTW.replace("Bearer ","");
            }
        }
        return null;
    }

      */




   @Override
    public void apply(RequestTemplate template) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            template.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        }
    }

}
