package br.com.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.model.CategoriaProduto;
import br.com.lojavirtual.model.dto.CategoriaProdutoDTO;
import br.com.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	
	
	
	@ResponseBody
	@GetMapping(value = "**/listarCategoria")
	public ResponseEntity<List<CategoriaProduto>> listarCategoria(){
		List<CategoriaProduto> lista = categoriaProdutoRepository.findAll();
		
		return new ResponseEntity<List<CategoriaProduto>>(lista, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscaPorCategoria/{desc}")
	public ResponseEntity<List<CategoriaProduto>> buscaPorDesc(@PathVariable("desc") String desc){
		List<CategoriaProduto> lista = categoriaProdutoRepository.buscarCategoriaDes(desc.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(lista, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteCategoria")
	public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto){
		
		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		
		return new ResponseEntity("Categoria Removida", HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDTO> salvarCategoria(@RequestBody CategoriaProduto  categoriaProduto) throws ExceptionMentoriaJava{
		
		if(categoriaProduto.getEmpresa() == null || categoriaProduto.getEmpresa().getId() == null) {
			throw new ExceptionMentoriaJava("A empresa não foi informada.");
		}
		
		if(categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
			throw new ExceptionMentoriaJava("Esse produto já esta cadastrado");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();
		categoriaProdutoDTO.setId(categoriaSalva.getId());
		categoriaProdutoDTO.setNomeDesc(categoriaSalva.getNomeDesc());
		categoriaProdutoDTO.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		return new ResponseEntity<CategoriaProdutoDTO>(categoriaProdutoDTO, HttpStatus.OK);
	}
	
	
}
