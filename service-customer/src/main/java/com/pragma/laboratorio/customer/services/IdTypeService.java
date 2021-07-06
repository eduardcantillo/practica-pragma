package com.pragma.laboratorio.customer.services;

import java.util.List;

import com.pragma.laboratorio.customer.dto.IdTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.pragma.laboratorio.customer.entity.IdType;
@Service
public interface IdTypeService {

	public List<IdTypeDto> findAll();
	public ResponseEntity<IdTypeDto> findById(Integer id);
	public ResponseEntity<IdTypeDto> save(IdType idType,BindingResult result);
	public boolean deleteById(Integer id);
	public ResponseEntity<IdTypeDto> updateIdType(IdType idType,BindingResult result,int toUpdate);
}
