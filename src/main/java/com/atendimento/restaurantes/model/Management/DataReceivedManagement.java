package com.atendimento.restaurantes.model.Management;

import java.time.LocalDate;

public record DataReceivedManagement(
        Decades decades,
        LocalDate date
) {
}
