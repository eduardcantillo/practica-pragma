package com.pragma.laboratorio.customer;

import com.pragma.laboratorio.customer.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerService customerService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void findAllTest(){
        customerService.findAll();

    }
}
