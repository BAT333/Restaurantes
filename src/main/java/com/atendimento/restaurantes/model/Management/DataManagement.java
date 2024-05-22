package com.atendimento.restaurantes.model.Management;

import java.util.DoubleSummaryStatistics;

public record DataManagement(
        Long count,
        Double max,
        Double min,
        Double average,
        Double sum
) {
    public DataManagement(DoubleSummaryStatistics collect) {
        this(collect.getCount(),collect.getMax(),collect.getMin(),collect.getAverage(),collect.getSum());
    }
}
