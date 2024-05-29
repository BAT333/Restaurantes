package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.Employee.DataRegisterEmployee;
import com.atendimento.restaurantes.model.Employee.DataUpdateEmployoo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "cpf",unique = true)
    private String cpf;
    @Column(name = "nascimento")
    private LocalDate birthDate;
    @Column(name = "ativo")
    private Boolean active;
    @Embedded
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area")
    private FunctionEmployee employee;

    public Employee(DataRegisterEmployee dataRester, FunctionEmployee employee) {
        this.name = dataRester.name();
        this.cpf = dataRester.cpf();
        this.birthDate = dataRester.birthDate();
        this.active = true;
        this.address = new Address(dataRester.address());
        this.employee = employee;
    }

    public void updateData(DataUpdateEmployoo updateEmployoo) {
        if(updateEmployoo.name() != null){
            this.name = updateEmployoo.name();
        }
        if(updateEmployoo.address()!= null){
            this.address.updateAddress(updateEmployoo.address());
        }
        if(updateEmployoo.function()!= null){
            this.employee.update(updateEmployoo.function());
        }
    }

    public void delete() {
        this.active =false;
    }
}
