package br.com.multitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.multitenancy.model.Pessoa;
import br.com.multitenancy.repository.PessoaRepository;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository cidadeRepo;
	
	@GetMapping
	public Iterable<Pessoa> findAll() {
		return cidadeRepo.findAll();
	}
	
	@PostMapping
	public Pessoa save(Pessoa pessoa) {
		return cidadeRepo.save(pessoa);
	}
	
}
