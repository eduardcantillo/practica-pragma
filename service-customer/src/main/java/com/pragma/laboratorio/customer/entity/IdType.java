package com.pragma.laboratorio.customer.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "id_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Debe dar una descripcion valida")
	private String descripcion;
	
	@NotBlank(message = "Debe dar un nombre valido")
	private String nombre;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "idType",fetch = FetchType.LAZY)
	private List<Customer> customers;
}
