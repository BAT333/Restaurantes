package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.Employee.DataFunction;
import com.atendimento.restaurantes.model.FunctionEmployee.Workspace;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "funcaofuncionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FunctionEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    private Workspace workspace;
    @Column(name = "descricao")
    private String description;
    @Column(name = "salario")
    private BigDecimal salary;
    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private Employee employee;


    public FunctionEmployee(DataFunction function) {
        this.workspace = function.workspaceName();
        if(function.description()!=null){
            this.description =function.description();
        }else{
            this.description=workspace.description;
        }
        if(function.salary()!=null){
            this.salary =function.salary();
        }else{
            this.salary=workspace.salary;
        }
    }

    public void update(DataFunction function) {
        if(function.salary() != null){
            this.salary = function.salary();

        }
        if(function.workspaceName()!= null){
            this.workspace = function.workspaceName();
            if(function.description() != null){
                this.description = function.description();
            }else{
                this.description = function.workspaceName().description;
            }

        }

    }
}
