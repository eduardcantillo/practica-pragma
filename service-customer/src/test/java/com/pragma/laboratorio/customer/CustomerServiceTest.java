package com.pragma.laboratorio.customer;

import com.pragma.laboratorio.customer.dao.ICustomerDao;
import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.dto.FotoDto;
import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.foto.rest.FotoRest;
import com.pragma.laboratorio.customer.services.CustomerService;
import com.pragma.laboratorio.customer.services.implement.CustomerServiceImplement;
import com.pragma.laboratorio.customer.utils.Datos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {



    @Mock
    ICustomerDao customerDao;

    @Mock
    FotoRest fotoRest;

    @InjectMocks
    CustomerServiceImplement customerService;

    @BeforeEach
    void config(){
            customerService=new CustomerServiceImplement( customerDao, new ModelMapper(),fotoRest);
    }


    @Test
    void findAllTest(){

        Mockito.when(customerDao.findAll()).thenReturn(Datos.getClientes());
        Mockito.when(fotoRest.listAll()).thenReturn(ResponseEntity.ok(Datos.getAllFotos()));

        List<CustomerDto> customer=customerService.findAll();

        assertEquals(2,customer.size());
        assertEquals("eduard",customer.get(0).getNombres());

    }

    @Test
    void findById(){
        customerService.findById("12345455");
    }
}
