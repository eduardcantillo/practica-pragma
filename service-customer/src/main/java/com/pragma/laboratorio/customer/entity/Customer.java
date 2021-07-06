package com.pragma.laboratorio.customer.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "customers")
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	 @Id
	 @NotBlank(message = "La identificacion no puede ser vacia ni nula")
	 private String identificacion;
	
	 @NotBlank(message = "El nombre no puede ser vacio ni nulo")
	 private String nombres;
	 
	 @NotBlank(message = "El apellido no puede ser vacio ni nulo")
	 private String apellidos;
	 
	
	 @JoinColumn(name = "id_type")
	 @ManyToOne
	 private IdType idType;
	
	
	 @Positive(message = "la edad debe ser un numero mayor que cero")
	 private Integer edad;
	 
	 @NotBlank(message = "la ciudad debe ser valida") 
	 private String ciudad;
	 private String foto;
	 
	 
}
