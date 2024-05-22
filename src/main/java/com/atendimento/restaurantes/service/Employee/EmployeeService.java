package com.atendimento.restaurantes.service.Employee;

import com.atendimento.restaurantes.domain.Employee;
import com.atendimento.restaurantes.domain.FunctionEmployee;
import com.atendimento.restaurantes.model.Employee.DataEmployee;
import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.Employee.DataUpdateEmployoo;
import com.atendimento.restaurantes.repository.EmployeeRepository;
import com.atendimento.restaurantes.repository.FunctionEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private FunctionEmployeeRepository repositoryFunction;
    public ResponseEntity<DataEmployee> registerEmployee(DataRegisterEmployee dataRester,UriComponentsBuilder builder) {
       FunctionEmployee functionEmployee = repositoryFunction.save(new FunctionEmployee(dataRester.workspace()));
        Employee employee = repository.save(new Employee(dataRester,functionEmployee));
        URI uri = builder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataEmployee(employee));
    }

    public ResponseEntity<DataEmployee> employeeID(Long id) {
       Optional<Employee> employee = repository.findByIdAndActiveTrue(id);
        return ResponseEntity.ok(new DataEmployee(employee.get()));
    }

    public ResponseEntity<DataEmployee> updateEmployee(Long id, DataUpdateEmployoo updateEmployoo) {
        Optional<Employee> employee = repository.findByIdAndActiveTrue(id);
        employee.ifPresent(em-> em.updateData(updateEmployoo));
        return ResponseEntity.ok(new DataEmployee(employee.get()));
    }

    public ResponseEntity deleteEmployee(Long id) {
        repository.findByIdAndActiveTrue(id).ifPresent(Employee::delete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<DataEmployee>> list(Pageable pageable) {

        return ResponseEntity.ok(this.repository.findAllByActiveTrue(pageable).map(DataEmployee::new));
    }
}
