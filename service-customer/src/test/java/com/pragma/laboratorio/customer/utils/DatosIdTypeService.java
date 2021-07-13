package com.pragma.laboratorio.customer.utils;

import com.pragma.laboratorio.customer.entity.IdType;


import java.util.Arrays;
import java.util.List;

public class DatosIdTypeService {

    public static List<IdType> findAll(){
       IdType type1= IdType.builder().id(1).nombre("cedula").descripcion("documentos de las personas nacidas en colombia").build();
        IdType type2=IdType.builder().id(1).nombre("pasaporte").descripcion("usado por poersonas extranjeras para entrar al pais").build();
        return Arrays.asList(type1,type2);
    }

    public static IdType getIdType(){
        return  IdType.builder().id(1).nombre("cedula").descripcion("documentos de las personas nacidas en colombia").build();
    }
}
