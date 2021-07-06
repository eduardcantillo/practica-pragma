package com.pragma.laboratorio.customer.services.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import javax.validation.Valid;

import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.dto.FotoDto;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.foto.rest.FotoRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.pragma.laboratorio.customer.dao.ICustomerDao;
import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.services.CustomerService;


@Service
public class CustomerServiceImplement implements CustomerService{

	@Autowired
	private ICustomerDao customerDao;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	FotoRest fotoRest;

	@Override
	public List<CustomerDto> findAll() {
		List<Customer> customers=this.customerDao.findAll();
		List<CustomerDto> customersDto=new ArrayList<>();
		List<FotoDto> fotoDtos=this.fotoRest.listAll().getBody();
		Map <String,FotoDto> fotos=new HashMap<>();
		for (FotoDto foto: fotoDtos){
			fotos.put(foto.get_id(),foto);
		}

		for (Customer c:customers) {
			CustomerDto cus=modelMapper.map(c,CustomerDto.class);
			cus.setFoto(fotos.get(c.getFoto()));
			customersDto.add(cus);
		}
		return customersDto;
	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<CustomerDto> findById(String identificacion) {
		Customer found=this.customerDao.findById(identificacion).orElse(null);
		CustomerDto customerDto=modelMapper.map(found,CustomerDto.class);
		customerDto.setFoto(fotoRest.findById(found.getFoto()).getBody());
		return (found==null)? ResponseEntity.notFound().build():ResponseEntity.ok(customerDto);
	}

	@Override
	public ResponseEntity<CustomerDto> save(@Valid CustomerDto customerDto) {
		Customer customer=modelMapper.map(customerDto,Customer.class);
		FotoDto fotoDto=customerDto.getFoto();

		Customer found=this.customerDao.findById(customer.getIdentificacion()).orElse(null);
		ResponseEntity<FotoDto> res=this.fotoRest.save(fotoDto);

		if(res.getStatusCodeValue()!=200){
			System.out.println("fallo al guardar en service foto");
			return ResponseEntity.internalServerError().build();
		}

		if(found!=null) { return ResponseEntity.badRequest().build();}
		
		
		try {
			customer.setFoto(res.getBody().get_id());
			this.customerDao.save(customer);
			return ResponseEntity.ok(customerDto);
			
		}catch (Exception e) {
			System.out.println("fallo al guardar la foto");
			return ResponseEntity.internalServerError().build();
		}
	}

	@Override
	public boolean deleteById(String identificion) {
		try {
			Customer customer=this.customerDao.findById(identificion).orElse(null);
			this.customerDao.deleteById(identificion);
			this.fotoRest.deleteById(customer.getFoto());
				return true;
			}catch (DataAccessException e) {
				return false;
			}
		}

	@Override
	public ResponseEntity<CustomerDto> update( CustomerDto customerDto,  String cedulaCustomer) {
		Customer customerDb=this.customerDao.findById(cedulaCustomer).orElse(null);

		/*if(result.hasErrors()) {
			result.getAllErrors().stream().map(e ->{
				String h=e.getObjectName()+" : "+e.getDefaultMessage();
				return h;
			}).collect(Collectors.toList()).forEach(System.out::println);
			return ResponseEntity.badRequest().build();
			
		}/*/
		if(customerDb==null) { 
			return ResponseEntity.notFound().build();
		}
		ResponseEntity<FotoDto> res=this.fotoRest.update(customerDb.getFoto(),customerDto.getFoto());
		if(res.getStatusCodeValue()!=200){
			return ResponseEntity.internalServerError().build();
		}
		Customer customer=modelMapper.map(customerDto,Customer.class);

		
		customerDb.setApellidos(customer.getApellidos());
		customerDb.setCiudad(customer.getCiudad());
		customerDb.setEdad(customer.getEdad());
		customerDb.setNombres(customer.getNombres());

		
		try {
			customerDb=this.customerDao.save(customerDb);
			customerDto=modelMapper.map(customerDb,CustomerDto.class);
			customerDto.setFoto(res.getBody());
			return ResponseEntity.ok(customerDto);
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}

	@Override
	public ResponseEntity<List<CustomerDto>> findByAge(int age) {
		List<Customer> customers=this.customerDao.findByEqualsOrGreater(age);
		List<CustomerDto> customersDto=new ArrayList<>();
		ResponseEntity<List<FotoDto>> res=this.fotoRest.listAll();
		if(customers ==null || customers.isEmpty()){
			return ResponseEntity.noContent().build();
		}
		if(res.getStatusCodeValue()!=200){
			return ResponseEntity.notFound().build();
		}
		List<FotoDto> fotos=res.getBody();
		Map <String,FotoDto> fotosAasignar=new HashMap<>();
		for(FotoDto foto:fotos){
			fotosAasignar.put(foto.get_id(),foto);
		}
		CustomerDto aux;
		for (Customer cs: customers) {
			aux=modelMapper.map(cs,CustomerDto.class);
			aux.setFoto(fotosAasignar.get(cs.getFoto()));
			customersDto.add(aux);
		}

		return ResponseEntity.ok(customersDto);
	}

	@Override
	public ResponseEntity<List<CustomerDto>> findByIdType(IdType idType) {
		try{
			List<Customer> customers=this.customerDao.findByIdType(idType);
			List<CustomerDto> customerDtos=new ArrayList<>();

			if(customers ==null || customers.isEmpty()){
				return ResponseEntity.noContent().build();
			}

			for (Customer cs: customers){
				customerDtos.add(modelMapper.map(cs,CustomerDto.class));
			}

			return ResponseEntity.ok(customerDtos);
		}catch (Exception e){
			return ResponseEntity.internalServerError().build();
		}
	}

	@Override
	public ResponseEntity<CustomerDto> findByIdTypeAndIdentificacion(IdType idType,String id) {
		try{
			Customer cus=this.customerDao.findByIdTypeAndIdentificacion(idType,id);
			if(cus==null){
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(modelMapper.map(cus,CustomerDto.class));
		}catch (Exception e){
			return ResponseEntity.internalServerError().build();
		}
	}


}
