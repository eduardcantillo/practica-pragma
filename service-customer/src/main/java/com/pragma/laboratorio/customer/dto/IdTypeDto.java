package com.pragma.laboratorio.customer.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdTypeDto {
    private Integer id;
    private String descripcion;
    private String nombre;

    @JsonIgnore
    private List<CustomerDto> customers;
}
