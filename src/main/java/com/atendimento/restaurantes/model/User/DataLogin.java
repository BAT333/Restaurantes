package com.atendimento.restaurantes.model.User;

import javax.xml.transform.sax.SAXResult;

public record DataLogin(
        String login,
        String password
) {
}
