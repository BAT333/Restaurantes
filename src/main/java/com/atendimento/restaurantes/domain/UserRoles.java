package com.atendimento.restaurantes.domain;

public enum UserRoles {
    BOSS("boss"),
    EMPLOYEE("employee"),
    USER("user");
    private String values;
    UserRoles(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
