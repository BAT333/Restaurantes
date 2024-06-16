package com.atendimento.restaurantes.service.Employee;

import com.atendimento.restaurantes.domain.Employee;
import com.atendimento.restaurantes.domain.FunctionEmployee;
import com.atendimento.restaurantes.http.LoginFeign;
import com.atendimento.restaurantes.model.DataEmployeeCreate;
import com.atendimento.restaurantes.model.Employee.DataEmployee;
import com.atendimento.restaurantes.model.Employee.DataLoginsDTO;
import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.Employee.DataUpdateEmployoo;
import com.atendimento.restaurantes.repository.EmployeeRepository;
import com.atendimento.restaurantes.repository.FunctionEmployeeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private LoginFeign feign;

    @Autowired
    private FunctionEmployeeRepository repositoryFunction;
    public ResponseEntity<DataEmployeeCreate> registerEmployee(DataRegisterEmployee dataRester, UriComponentsBuilder builder) {
        var logins= this.creationLogin(dataRester);
        FunctionEmployee functionEmployee = repositoryFunction.save(new FunctionEmployee(dataRester.workspace()));
        Employee employee = repository.save(new Employee(dataRester,functionEmployee));
        this.creatingUser(dataRester.cpf(),dataRester.password());
        URI uri = builder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataEmployeeCreate(employee,logins));
    }

    private DataLoginsDTO creationLogin(DataRegisterEmployee dataRester) {
        return this.feign.register(new DataLoginsDTO(dataRester.cpf(),dataRester.password()));
    }

    private void creatingUser(String cpf,String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
    }

    public ResponseEntity<DataEmployee> employeeID(Long id) {
        Optional<Employee> employee = repository.findByIdAndActiveTrue(id);
        return employee.map(value -> ResponseEntity.ok(new DataEmployee(value))).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity<DataEmployee> updateEmployee(Long id, DataUpdateEmployoo updateEmployoo) {
        Optional<Employee> employee = repository.findByIdAndActiveTrue(id);
        employee.ifPresent(em-> em.updateData(updateEmployoo));
        return employee.map(values-> ResponseEntity.ok(new DataEmployee(values))).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity<Object> deleteEmployee(Long id) {
        repository.findByIdAndActiveTrue(id).ifPresent(Employee::delete);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<DataEmployee>> list(Pageable pageable) {

        return ResponseEntity.ok(this.repository.findAllByActiveTrue(pageable).map(DataEmployee::new));
    }

    public ResponseEntity<DataLoginsDTO> update(DataLoginsDTO dto) {
      return ResponseEntity.ok(feign.update(dto));
    }
}
