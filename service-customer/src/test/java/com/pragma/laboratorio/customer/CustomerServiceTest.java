package com.pragma.laboratorio.customer;


import com.pragma.laboratorio.customer.dao.ICustomerDao;
import com.pragma.laboratorio.customer.dao.IIdTypeDao;
import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.dto.FotoDto;
import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.foto.rest.FotoRest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {



    @Mock
    ICustomerDao customerDao;

    @Mock
    FotoRest fotoRest;

    @Mock
    IIdTypeDao idTypeDao;

    @InjectMocks
    CustomerServiceImplement customerService;

    @BeforeEach
    void config(){
            customerService=new CustomerServiceImplement( customerDao, new ModelMapper(),fotoRest,idTypeDao);
    }


    @Test
    void findAllTest(){

        Mockito.when(customerDao.findAll()).thenReturn(Datos.getClientes());
        Mockito.when(fotoRest.listAll()).thenReturn(ResponseEntity.ok(Datos.getAllFotos()));

        List<CustomerDto> customer=customerService.findAll();

        assertEquals(2,customer.size());

        assertEquals("eduard",customer.get(0).getNombres());
        assertEquals("123456",customer.get(0).getIdentificacion());
        assertEquals(1,customer.get(0).getIdType().getId());
        assertEquals("123456",customer.get(0).getFoto().get_id());

        assertEquals("jose",customer.get(1).getNombres());
        assertEquals("123457",customer.get(1).getIdentificacion());
        assertEquals(1,customer.get(1).getIdType().getId());
        assertEquals("1234567",customer.get(1).getFoto().get_id());


    }

    @Test
    void findByIdIfExist(){
        Mockito.when(customerDao.findById("12457")).thenReturn(Optional.of(Datos.getCliente()));
        Mockito.when(fotoRest.findById("123548")).thenReturn(ResponseEntity.ok(Datos.getFoto()));

        CustomerDto customer=this.customerService.findById("12457").getBody();

        assertEquals("12457",customer.getIdentificacion());
        assertEquals("123548",customer.getFoto().get_id());
        verify(customerDao).findById(anyString());

    }

    @Test
    void findByIdIfNotExist(){
        Mockito.when(customerDao.findById("12457")).thenReturn(Optional.ofNullable(null));

        ResponseEntity<CustomerDto> customer=this.customerService.findById("12457");
        assertEquals(404,customer.getStatusCodeValue());


    }

    @Test
    void saveIfNotExist(){
        Mockito.when(this.customerDao.findById(Datos.getCustomerDto().getIdentificacion())).thenReturn(Optional.ofNullable(null));
        Mockito.when(this.fotoRest.save(any(FotoDto.class))).thenReturn(ResponseEntity.ok(Datos.getFoto()));
        Mockito.when(this.idTypeDao.findById(Datos.getCustomerDto().getIdType().getId())).thenReturn(Optional.of(IdType.builder().descripcion("cedula para ciudadanos colombianos").id(1).nombre("CEDULA").build()));

        ResponseEntity<CustomerDto> customer=this.customerService.save(Datos.getCustomerDto());

        assertNotNull(customer);
        assertNotNull(customer.getBody());
        assertNotNull(customer.getBody().getFoto());
        assertEquals("54123",customer.getBody().getFoto().get_id());

        verify(customerDao).findById(anyString());
        verify(customerDao).save(any(Customer.class));
        verify(fotoRest).save(any(FotoDto.class));

        Mockito.when(this.fotoRest.save(any(FotoDto.class))).thenReturn(ResponseEntity.internalServerError().build());
        customer=this.customerService.save(Datos.getCustomerDto());

        assertEquals(500,customer.getStatusCodeValue());
    }

    @Test
    void saveIfExist(){
        Mockito.when(this.customerDao.findById(Datos.getCustomerDto().getIdentificacion())).thenReturn(Optional.of(Datos.getCliente()));
        ResponseEntity<CustomerDto> ans=this.customerService.save(Datos.getCustomerDto());
        assertEquals(400,ans.getStatusCodeValue());
    }

    @Test
    void deleteByIdWhenNotExistCustomer(){
        Mockito.when(this.customerDao.findById("00000")).thenReturn(Optional.ofNullable(null));
        Boolean deleted=this.customerService.deleteById("0000");

        assertEquals(false,deleted);
    }

    @Test
    void deleteByIdWhenExistCustomer(){
        Mockito.when(this.customerDao.findById("00000")).thenReturn(Optional.of(Datos.getCliente()));
        Mockito.when(this.fotoRest.deleteById(Datos.getCliente().getFoto())).thenReturn(ResponseEntity.ok(true));
        Boolean deleted=this.customerService.deleteById("0000");

        assertEquals(false,deleted);
    }

    @Test
    void updateWhenNotExist(){
        Mockito.when(this.customerDao.findById("12345")).thenReturn(Optional.ofNullable(null));
        ResponseEntity<CustomerDto> customerDtoResponseEntity=this.customerService.update(new CustomerDto(),"12345");
        assertEquals(404,customerDtoResponseEntity.getStatusCodeValue());
    }

    @Test
    void updateWhenExist(){
        Mockito.when(this.customerDao.findById(Datos.getCliente().getIdentificacion())).thenReturn(Optional.of(Datos.getCliente()));
        Mockito.when(this.fotoRest.update(Datos.getCliente().getFoto(),Datos.getCustomerDto().getFoto())).thenReturn(ResponseEntity.ok(Datos.getCustomerDto().getFoto()));

        Customer aux=Datos.getCliente();
        aux.setFoto(Datos.getCustomerDto().getFoto().get_id());
        aux.setNombres(Datos.getCustomerDto().getNombres());
        aux.setApellidos(Datos.getCustomerDto().getApellidos());
        aux.setCiudad(Datos.getCustomerDto().getCiudad());
        aux.setEdad(Datos.getCustomerDto().getEdad());

        Mockito.when(this.customerDao.save(aux)).thenReturn(aux);

        ResponseEntity<CustomerDto> customerDto=this.customerService.update(Datos.getCustomerDto(),Datos.getCliente().getIdentificacion());

        assertEquals(Datos.getCliente().getIdentificacion(),customerDto.getBody().getIdentificacion());
        assertNotEquals(Datos.getCliente().getCiudad(),customerDto.getBody().getCiudad());
        verify(customerDao).save(any(Customer.class));
        verify(customerDao).findById(anyString());
        verify(fotoRest).update(anyString(),any(FotoDto.class));

    }

    @Test
    void findByEdad(){
        Mockito.when(this.customerDao.findByEqualsOrGreater(20)).thenReturn(Datos.getCustomers());
        Mockito.when(this.fotoRest.findByIds(Arrays.asList("123456","1234567"))).thenReturn(ResponseEntity.ok(Datos.getAllFotos()));

        ResponseEntity<List<CustomerDto>> customerDtoResponseEntity=this.customerService.findByAge(20);

        verify(fotoRest).findByIds(any(List.class));
        verify(customerDao).findByEqualsOrGreater(anyInt());

        assertEquals(200,customerDtoResponseEntity.getStatusCodeValue());
        assertEquals(2,customerDtoResponseEntity.getBody().size());
        assertEquals(Datos.getAllFotos().get(0),customerDtoResponseEntity.getBody().get(0).getFoto());
    }

    @Test
    void findByEdadWhenIsEmpty(){
        Mockito.when(this.customerDao.findByEqualsOrGreater(35)).thenReturn(new ArrayList<Customer>());

        ResponseEntity<List<CustomerDto>> customerDtoResponseEntity=this.customerService.findByAge(35);
        assertEquals(false,customerDtoResponseEntity.hasBody());
        assertEquals(204,customerDtoResponseEntity.getStatusCodeValue());
    }

    @Test
    void findByIdType(){
        Mockito.when(this.customerDao.findByIdType(IdType.builder().id(1).nombre("cedula").descripcion("Cedula de cedula colombiana").build())).thenReturn(Datos.getCustomers());
        Mockito.when(this.fotoRest.findByIds(Arrays.asList("123456","1234567"))).thenReturn(ResponseEntity.ok(Datos.getAllFotos()));

        ResponseEntity<List<CustomerDto>> cus=this.customerService.findByIdType(IdType.builder().id(1).nombre("cedula").descripcion("Cedula de cedula colombiana").build());

        verify(this.fotoRest).findByIds(any(List.class));
        for (CustomerDto cust:cus.getBody()) {
            assertEquals(1,cust.getIdType().getId());

        }
    }

    @Test
    void findByIdTypeWhenIsEmpty(){

        Mockito.when(this.customerDao.findByIdType(IdType.builder().id(3).build())).thenReturn(new ArrayList<Customer>());
        ResponseEntity<List<CustomerDto>> cus=this.customerService.findByIdType(IdType.builder().id(3).build());
        assertEquals(HttpStatus.NO_CONTENT,cus.getStatusCode());
    }

    @Test
    void findByIdTypeAndIdentificacionWhenExist(){
        Mockito.when(this.customerDao.findByIdTypeAndIdentificacion(IdType.builder().id(0).build(),"12457")).thenReturn(Datos.getCliente());
        Mockito.when(this.fotoRest.findById(Datos.getCliente().getFoto())).thenReturn(ResponseEntity.ok(Datos.getFoto()));

        ResponseEntity<CustomerDto> customerDtoResponseEntity=this.customerService.findByIdTypeAndIdentificacion(IdType.builder().id(0).build(),"12457");

        assertEquals(200,customerDtoResponseEntity.getStatusCodeValue());
        assertEquals(Datos.getFoto().get_id(),customerDtoResponseEntity.getBody().getFoto().get_id());
        assertEquals(1,customerDtoResponseEntity.getBody().getIdType().getId());

        verify(customerDao).findByIdTypeAndIdentificacion(any(IdType.class),anyString());
        verify(fotoRest).findById(anyString());

    }
}
