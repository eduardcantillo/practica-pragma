package com.pragma.laboratorio.customer.utils;

import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.dto.FotoDto;
import com.pragma.laboratorio.customer.dto.IdTypeDto;
import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.entity.IdType;

import java.util.Arrays;
import java.util.List;

public class Datos {

    public static List<Customer> getClientes(){
        Customer customer=Customer.builder().apellidos("cantillo").nombres("eduard").identificacion("123456")
                .ciudad("cúcuta").edad(23)
                .idType(IdType.builder().descripcion("cedula colombiana").
                        id(1).nombre("C.C").build()).foto("123456").build();

        Customer customer1=Customer.builder().apellidos("cantillo").nombres("jose").identificacion("123457")
                .ciudad("cúcuta").edad(23)
                .idType(IdType.builder().descripcion("cedula colombiana").
                        id(1).nombre("C.C").build()).foto("1234567").build();

        return Arrays.asList(customer,customer1);
    }

    public static List<FotoDto> getAllFotos(){
        FotoDto fotoDto= FotoDto.builder().content(new byte[45])._id("123456").build();
        FotoDto fotoDto1= FotoDto.builder().content(new byte[54])._id("1234567").build();

        return Arrays.asList(fotoDto,fotoDto1);
    }

    public static Customer getCliente(){

        return Customer.builder().apellidos("cantillo").nombres("eduard").identificacion("12457")
                .ciudad("cúcuta").edad(23)
                .idType(IdType.builder().descripcion("cedula colombiana").
                        id(1).nombre("C.C").build()).foto("123548").build();
    }

    public static FotoDto getFoto(){
       return FotoDto.builder().content(new byte[45])._id("123548").build();
    }

    public static CustomerDto getCustomerDto(){

    return  CustomerDto.builder().apellidos("cantillo").ciudad("cucuta").edad(23)
            .idType(IdTypeDto.builder().descripcion("cedula para ciudadanos colombianos").id(1).nombre("CEDULA").build()).identificacion("1234568")
            .foto(FotoDto.builder().content(new byte[1])._id("54123").build()).nombres("eduard").build();
    }

    public static List<Customer> getCustomers(){
        Customer customer=Customer.builder().apellidos("cantillo").nombres("jose").identificacion("123456")
                .ciudad("cúcuta").edad(23)
                .idType(IdType.builder().descripcion("cedula colombiana").
                        id(1).nombre("C.C").build()).foto("123456").build();

        Customer customer1=Customer.builder().apellidos("cantillo").nombres("pedro").identificacion("123457")
                .ciudad("cúcuta").edad(28)
                .idType(IdType.builder().descripcion("cedula colombiana").
                        id(1).nombre("C.C").build()).foto("1234567").build();

        return Arrays.asList(customer,customer1);
    }


}
