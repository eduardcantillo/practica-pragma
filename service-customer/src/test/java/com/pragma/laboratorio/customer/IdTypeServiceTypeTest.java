package com.pragma.laboratorio.customer;

import com.pragma.laboratorio.customer.dao.IIdTypeDao;
import com.pragma.laboratorio.customer.dto.IdTypeDto;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.services.implement.IdTypeServiceImplements;
import com.pragma.laboratorio.customer.utils.Datos;
import com.pragma.laboratorio.customer.utils.DatosIdTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;



import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IdTypeServiceTypeTest {

    @Mock
    IIdTypeDao idTypeDao;
    @Mock
    BindingResult result;

    @InjectMocks
    IdTypeServiceImplements serviceImplements;
    @BeforeEach
    void config(){
        serviceImplements=new IdTypeServiceImplements(new ModelMapper(), idTypeDao);
    }

    @Test
    void findAllTest(){
        Mockito.when(idTypeDao.findAll()).thenReturn(DatosIdTypeService.findAll());
        List<IdTypeDto> tipes=this.serviceImplements.findAll();

        assertEquals(2,tipes.size());
    }

    @Test
    void findByIdWhenExist(){
        Mockito.when(idTypeDao.findById(1)).thenReturn(Optional.of(DatosIdTypeService.getIdType()));
        ResponseEntity<IdTypeDto> res=this.serviceImplements.findById(1);

        assertEquals(200,res.getStatusCodeValue());
        assertEquals(true,res.hasBody());
        assertEquals(01,res.getBody().getId());
    }

    @Test
    void findByIdWhenNotExist(){
        Mockito.when(idTypeDao.findById(0)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<IdTypeDto> res=this.serviceImplements.findById(0);

        assertEquals(404,res.getStatusCodeValue());
        assertEquals(false,res.hasBody());
        assertEquals(null,res.getBody());

    }

    @Test
    void saveWhenHasError(){
        IdType type=IdType.builder().id(0).build();
        ResponseEntity<IdTypeDto> res=this.serviceImplements.save(type, result);
        assertEquals(400,res.getStatusCodeValue());
    }


    @Test
    void saveWhenNotHasError(){

        Mockito.when(this.idTypeDao.save(DatosIdTypeService.getIdType())).thenReturn(DatosIdTypeService.getIdType());
        ResponseEntity<IdTypeDto> res=this.serviceImplements.save(DatosIdTypeService.getIdType(), result);
        assertEquals(201,res.getStatusCodeValue());
        assertEquals("cedula",res.getBody().getNombre());
    }

    @Test
    void updateWhenHasError(){
        ResponseEntity<IdTypeDto> res=this.serviceImplements.updateIdType(IdType.builder().id(0).build(),result,1);

        assertEquals(400,res.getStatusCodeValue());
        assertEquals(false,res.hasBody());

    }

    @Test
    void updateWhenNotHasErrorsButNotFound(){
        Mockito.when(this.idTypeDao.findById(1)).thenReturn(Optional.ofNullable(null));
        ResponseEntity<IdTypeDto> res=this.serviceImplements.updateIdType(DatosIdTypeService.getIdType(),result,1);
        assertEquals(404,res.getStatusCodeValue());
        verify(idTypeDao).findById(anyInt());
    }

    @Test
    void updateWhenNotHasError(){
        Mockito.when(this.idTypeDao.findById(1)).thenReturn(Optional.of(IdType.builder().id(1).build()));
        Mockito.when(this.idTypeDao.save(DatosIdTypeService.getIdType())).thenReturn(DatosIdTypeService.getIdType());

        ResponseEntity<IdTypeDto> res=this.serviceImplements.updateIdType(DatosIdTypeService.getIdType(),result,1);

        assertEquals(200,res.getStatusCodeValue());
        assertEquals("cedula",res.getBody().getNombre());
        verify(idTypeDao).save(any(IdType.class));
        verify(idTypeDao).findById(anyInt());


    }
}
