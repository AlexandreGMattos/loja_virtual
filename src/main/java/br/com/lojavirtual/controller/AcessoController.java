package br.com.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.model.Acesso;
import br.com.lojavirtual.repository.AcessoRepository;
import br.com.lojavirtual.service.AcessoService;

@Controller /*indica que a classe é responsável por lidar com as requisições HTTP e retornar as respostas, geralmente em forma de uma visão (view) como uma página HTML, JSON, ou XML*/
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/salvarAcesso") /*Mapeando a url para receber JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava{ /*@RequestBody recebe o JSON e converte para objeto, se nao colocar fica tudo nulo*/
		
		if(acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			if(!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe um acesso com essa descrição: " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody 
	@PostMapping(value = "**/deleteAcesso") 
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) { 
		
		acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso Removido.", HttpStatus.OK);
	}
	
	//@Secured({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody 
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}") 
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) { 
		
		acessoRepository.deleteById(id);
		
		return new ResponseEntity("Acesso Removido", HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/obterAcesso/{id}") 
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava{ 
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if(acesso == null) {
			throw new ExceptionMentoriaJava("Não encontrou o acesso com o id: " + id);
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
	@ResponseBody 
	@GetMapping(value = "**/buscaPorDesc/{desc}") 
	public ResponseEntity<List<Acesso>> buscaPorDesc(@PathVariable("desc") String desc) { 
		
		List <Acesso> acessos = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}
}
