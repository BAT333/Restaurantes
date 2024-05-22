package com.atendimento.restaurantes.domain;

import com.atendimento.restaurantes.model.Employee.DataAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(name = "cep")
    private String zipCode;
    @Column(name = "bairro")
    private String neighborhood ;
    @Column(name = "cidade")
    private String city ;
    @Column(name = "complemento")
    private String complement ;
    @Column(name = "estado")
    private String state ;
    @Column(name = "rua")
    private String street ;
    @Column(name = "numero")
    private Integer number ;

    public Address(DataAddress address) {
        this.zipCode = address.zipCode();
        this.city =address.city();
        this.complement = address.complement();
        this.neighborhood =address.neighborhood();
        this.street = address.street();
        this.state = address.state();
        this.number =address.number();
    }

    public void updateAddress(DataAddress address) {
        if(address.city() != null){
            this.city = address.city();
        }
        if(address.complement() != null){
            this.complement = address.complement();
        }
        if(address.neighborhood() != null){
            this.neighborhood = address.neighborhood();
        }
        if(address.street() != null){
            this.street =address.street();
        }
        if(address.number() != null){
            this.number =address.number();
        }
        if(address.state() != null){
            this.state= address.state();
        }
        if(address.zipCode() != null){
            this.zipCode = address.zipCode();
        }


    }
}
