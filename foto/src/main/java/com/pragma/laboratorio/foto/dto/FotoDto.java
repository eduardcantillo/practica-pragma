package com.pragma.laboratorio.foto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoDto {

    private String _id;
    private byte[] content;
}