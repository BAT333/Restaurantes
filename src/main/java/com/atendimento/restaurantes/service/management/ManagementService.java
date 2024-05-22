package com.atendimento.restaurantes.service.management;

import com.atendimento.restaurantes.domain.OrderTotal;
import com.atendimento.restaurantes.model.Management.DataManagement;
import com.atendimento.restaurantes.model.Management.DataReceivedManagement;
import com.atendimento.restaurantes.repository.OrderTotalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagementService {
    @Autowired
    private OrderTotalRepository orderTotalRepository;
    private final LocalDate date = LocalDate.now();



    public ResponseEntity<DataManagement> dataManagement(DataReceivedManagement receivedManagement) {
        if(receivedManagement.decades()!= null){
            switch (receivedManagement.decades()){
                case YEAR -> {return this.dataYear(receivedManagement);}
                case MONTH -> {return this.dataMonth(receivedManagement);}
                case DAY -> {return this.dataDay(receivedManagement);}
            }
        }
        List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrue();
        DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        return ResponseEntity.ok(new DataManagement(collect));
    }
    public ResponseEntity<DataManagement> dataDay(DataReceivedManagement receivedManagement) {
        if(receivedManagement.date()!=null){
            List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDate(receivedManagement.date());
            DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
            return ResponseEntity.ok(new DataManagement(collect));
        }
        List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDate(date);
        DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        return ResponseEntity.ok(new DataManagement(collect));
    }

    private ResponseEntity<DataManagement> dataMonth(DataReceivedManagement receivedManagement) {
        if(receivedManagement.date()!=null){
            List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDateBetween(receivedManagement.date().with(TemporalAdjusters.firstDayOfMonth()),receivedManagement.date().with(TemporalAdjusters.lastDayOfMonth()));
            DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
            return ResponseEntity.ok(new DataManagement(collect));
        }
        List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDateBetween(date.with(TemporalAdjusters.firstDayOfMonth()),date.with(TemporalAdjusters.lastDayOfMonth()));
        DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        return ResponseEntity.ok(new DataManagement(collect));
    }

    private ResponseEntity<DataManagement> dataYear(DataReceivedManagement receivedManagement) {
        if(receivedManagement.date()!=null) {
            List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDateBetween(receivedManagement.date().with(TemporalAdjusters.firstDayOfYear()),receivedManagement.date().with(TemporalAdjusters.lastDayOfYear()));
            DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
            return ResponseEntity.ok(new DataManagement(collect));
        }
        List<OrderTotal> orderTotals = this.orderTotalRepository.findAllByActiveTrueAndDateBetween(date.with(TemporalAdjusters.firstDayOfYear()),date.with(TemporalAdjusters.lastDayOfYear()));
        DoubleSummaryStatistics collect = orderTotals.stream().map(OrderTotal::getTotal).collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        return ResponseEntity.ok(new DataManagement(collect));

    }
}
