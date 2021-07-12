package com.pragma.laboratorio.customer.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private String identificacion;
    private String nombres;
    private String apellidos;
    private IdTypeDto idType;
    private Integer edad;
    private String ciudad;
    private FotoDto foto;
}