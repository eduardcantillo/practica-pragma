package com.pragma.laboratorio.customer.dao;

import java.util.List;

import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.entity.IdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICustomerDao extends  JpaRepository<Customer, String>{

	@Query("select c from Customer c where c.edad=?1 or c.edad>?1")
	public List<Customer> findByEqualsOrGreater(int edad);

	public List<Customer> findByIdType(IdType idType);

	@Query("select c from Customer c  join fetch c.idType t where t=?1 and c.identificacion=?2 ")
	public Customer findByIdTypeAndIdentificacion(IdType idType,String identificacion);
}
