package br.com.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	@ResponseBody /*pode dar um retorno da API*/
	@PostMapping(value = "**/salvarAcesso") /*mapeando a url para receber um JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) { /*@RequestBody recebe o JSON e converte para objeto, se nao colocar fica tudo nulo*/
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody 
	@PostMapping(value = "**/deleteAcesso") 
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) { 
		
		acessoRepository.delete(acesso);
		
		return new ResponseEntity("Acesso Removido.", HttpStatus.OK);
	}
}
