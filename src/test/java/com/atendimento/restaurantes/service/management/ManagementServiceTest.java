package com.atendimento.restaurantes.service.management;

import com.atendimento.restaurantes.model.Management.DataReceivedManagement;
import com.atendimento.restaurantes.model.Management.Decades;
import com.atendimento.restaurantes.repository.OrderTotalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ManagementServiceTest {

    @InjectMocks
    private ManagementService managementService;
    @Mock
    private OrderTotalRepository orderTotalRepository;
    @Mock
    private DataReceivedManagement receivedManagement;
    @Mock
    private Decades decades;

    @Test
    @DisplayName("")
    void management01(){
        given(receivedManagement.decades()).willReturn(Decades.YEAR);
        given(receivedManagement.date()).willReturn(LocalDate.now());
        managementService.dataManagement(receivedManagement);
        then(orderTotalRepository).should().findAllByActiveTrueAndDateBetween(receivedManagement.date().with(TemporalAdjusters.firstDayOfYear()),receivedManagement.date().with(TemporalAdjusters.lastDayOfYear()));
    }
    @Test
    @DisplayName("")
    void management02(){
        given(receivedManagement.decades()).willReturn(Decades.MONTH);
        given(receivedManagement.date()).willReturn(LocalDate.now());
        managementService.dataManagement(receivedManagement);
        then(orderTotalRepository).should().findAllByActiveTrueAndDateBetween(receivedManagement.date().with(TemporalAdjusters.firstDayOfMonth()),receivedManagement.date().with(TemporalAdjusters.lastDayOfMonth()));
    }
    @Test
    @DisplayName("")
    void management03(){
        given(receivedManagement.decades()).willReturn(Decades.DAY);
        given(receivedManagement.date()).willReturn(LocalDate.now());
        managementService.dataManagement(receivedManagement);
        then(orderTotalRepository).should().findAllByActiveTrueAndDate(receivedManagement.date());
    }

}