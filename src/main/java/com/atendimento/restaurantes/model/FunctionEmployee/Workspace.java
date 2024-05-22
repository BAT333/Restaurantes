package com.atendimento.restaurantes.model.FunctionEmployee;

import java.math.BigDecimal;

public enum Workspace {
    MANAGER("function will be added later",BigDecimal.valueOf(1564)),
    CHEF("function will be added later",BigDecimal.valueOf(4657)),
    MAITRE("function will be added later",BigDecimal.valueOf(1458)),
    SOUSCHEF("function will be added later",BigDecimal.valueOf(3487)),
    BARMANAGER("function will be added later",BigDecimal.valueOf(5558)),
    WAITER("function will be added later",BigDecimal.valueOf(4629)),
    COOK("function will be added later",BigDecimal.valueOf(64786)),
    BARTEND("function will be added later",BigDecimal.valueOf(3486)),
    COMMINS("function will be added later",BigDecimal.valueOf(4876)),
    KITCHENASSISTANT("function will be added later",BigDecimal.valueOf(6789)),
    CLEANINGASSITANT("function will be added later",BigDecimal.valueOf(5855));
    public String description;
    public BigDecimal salary;
    Workspace(String description, BigDecimal salary){

    }

}
