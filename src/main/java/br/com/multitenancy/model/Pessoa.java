package br.com.multitenancy.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Pessoa {

	@Id
	private Long id;
	private String nome;
	
}
