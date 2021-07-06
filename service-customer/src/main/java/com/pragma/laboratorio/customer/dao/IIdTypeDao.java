package com.pragma.laboratorio.customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pragma.laboratorio.customer.entity.IdType;

public interface IIdTypeDao extends JpaRepository<IdType, Integer>{

}
