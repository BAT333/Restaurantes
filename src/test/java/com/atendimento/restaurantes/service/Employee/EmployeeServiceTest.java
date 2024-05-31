package com.atendimento.restaurantes.service.Employee;

import com.atendimento.restaurantes.domain.*;
import com.atendimento.restaurantes.model.Employee.*;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import com.atendimento.restaurantes.repository.EmployeeRepository;
import com.atendimento.restaurantes.repository.FunctionEmployeeRepository;
import com.atendimento.restaurantes.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private DataRegisterEmployee dto;
    @Spy
    private UriComponentsBuilder builder;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FunctionEmployeeRepository repositoryFunction;
    @Captor
    private ArgumentCaptor<FunctionEmployee> functionEmployee;
    @Captor
    private ArgumentCaptor<Employee> employee;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Mock
    private Workspace workspace;
    @Spy
    private URI uri;
    @Mock
    private Employee employees;
    @Mock
    private User willReturn;
    @Mock
    private FunctionEmployee willReturn2;
    @Mock
    private Address address;
    @Mock
    private DataUpdateEmployoo updateEmployoo;


    @Test
    @DisplayName("If you are saving everything ok")
    void register01(){
        dto =this.dtosInfos();

        Assertions.assertThrows(RuntimeException.class,()->employeeService.registerEmployee(dto,builder));
        then(repositoryFunction).should().save(functionEmployee.capture());
        then(employeeRepository).should().save(employee.capture());
        then(userRepository).should().save(userArgumentCaptor.capture());

        User userexpectation = new User("5",null);

        User userlues = userArgumentCaptor.getValue();
        Assertions.assertEquals(userexpectation.getLogins(),userlues.getLogins());



    }
    @Test
    @DisplayName("Testing encryption")
    void register02(){
        dto =this.dtosInfos();
        Assertions.assertThrows(RuntimeException.class,()->employeeService.registerEmployee(dto,builder));
        then(userRepository).should().save(userArgumentCaptor.capture());
        User userlues = userArgumentCaptor.getValue();
        User userexpectation = new User("5","4552");
        Assertions.assertEquals(userexpectation.getLogins(),userlues.getLogins());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assertions.assertTrue(passwordEncoder.matches(userexpectation.getPassword(), userlues.getPassword()));
    }

    @Test
    @DisplayName("Checking that the builder is running without error")
    void register03(){
        dto =this.dtosInfos();
        given(employeeRepository.save(new Employee(dto,willReturn2))).willReturn(employees);
        given(employees.getAddress()).willReturn(address);
        given(employees.getEmployee()).willReturn(willReturn2);
        given(employees.getEmployee().getWorkspace()).willReturn(workspace);
        uri = builder.path("employee/1").build().toUri();
        Assertions.assertDoesNotThrow(()->employeeService.registerEmployee(dto,builder));
    }
    @Test
    @DisplayName("Testing validation")
    void register04(){
        dto =this.dtosInfos();
        given(userRepository.existsByLogins(dto.cpf())).willReturn(true);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,employeeService.registerEmployee(dto,builder).getStatusCode());
    }


    @Test
    @DisplayName("Testing calling an employee")
    void employeeID01(){
        Long id = 1L;
        this.employeeService.employeeID(id);
        then(employeeRepository).should().findByIdAndActiveTrue(id);
    }
    @Test
    @DisplayName("Testing if update")
    void update01(){
        Long id = 1L;
        this.employeeService.updateEmployee(id,updateEmployoo);
        then(employeeRepository).should().findByIdAndActiveTrue(id);
    }
    @Test
    @DisplayName("Testing if delete")
    void delete01(){
        Long id = 1L;
        this.employeeService.deleteEmployee(id);
        then(employeeRepository).should().findByIdAndActiveTrue(id);
    }
    @Test
    @DisplayName("Testing if lists")
    void gets01(){
        Assertions.assertThrows(NullPointerException.class,()->this.employeeService.list(any()));
        then(employeeRepository).should().findAllByActiveTrue(any());
    }

    private DataRegisterEmployee dtosInfos() {
        return  new DataRegisterEmployee("a","5", LocalDate.now().withYear(5),
                new DataFunction(Workspace.BARMANAGER,"A", BigDecimal.ZERO),
                new DataAddress("1","s","d","R","s","x",1),"4552");
    }



}