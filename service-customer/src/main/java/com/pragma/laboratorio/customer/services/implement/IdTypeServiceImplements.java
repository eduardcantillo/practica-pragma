package com.pragma.laboratorio.customer.services.implement;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.pragma.laboratorio.customer.dto.IdTypeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.pragma.laboratorio.customer.dao.IIdTypeDao;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.services.IdTypeService;
@Service
public class IdTypeServiceImplements implements IdTypeService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private IIdTypeDao typeDao;

	@Override
	public List<IdTypeDto> findAll() {
		List<IdTypeDto> typeDtos=new ArrayList<>();
		List<IdType>type=this.typeDao.findAll();;
		for (IdType idType: type) {
			IdTypeDto typeDto=modelMapper.map(idType,IdTypeDto.class);
			typeDtos.add(typeDto);
		}
		return typeDtos;

	}

	@Override
	public ResponseEntity<IdTypeDto> findById(Integer id) {
		IdType type= this.typeDao.findById(id).orElse(null);
		
		return (type==null)? ResponseEntity.notFound().build():ResponseEntity.ok(modelMapper.map(type,IdTypeDto.class));
	}

	@Override
	public ResponseEntity<IdTypeDto> save(@Valid IdType idType,BindingResult result) {
		
		try {
			if(result.hasErrors()) {
				throw new NoSuchMessageException("Los datos no son correctos");}
			IdTypeDto ans=modelMapper.map(this.typeDao.save(idType),IdTypeDto.class);
				return ResponseEntity.status(HttpStatus.CREATED).body(ans);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@Override
	public boolean deleteById(Integer id) {
		try {
		this.typeDao.deleteById(id);
			return true;
		}catch (DataAccessException e) {
			return false;
		}
		
	}

	@Override
	public ResponseEntity<IdTypeDto> updateIdType(@Valid IdType idType, BindingResult result,int toUpdate) {
		
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		
		IdType typeDb=this.typeDao.findById(toUpdate).orElse(null);
		
		if(typeDb==null) {
			return ResponseEntity.notFound().build();
		}

		typeDb.setNombre(idType.getNombre());
		typeDb.setDescripcion(idType.getDescripcion());

		typeDb=this.typeDao.save(typeDb);
		IdTypeDto ans=modelMapper.map(typeDb,IdTypeDto.class);
		return ResponseEntity.ok(ans);
	}


}
