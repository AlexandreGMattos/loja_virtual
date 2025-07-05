package br.com.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lojavirtual.ExceptionMentoriaJava;
import br.com.lojavirtual.model.PessoaFisica;
import br.com.lojavirtual.model.PessoaJuridica;
import br.com.lojavirtual.repository.PessoaRepository;
import br.com.lojavirtual.service.PessoaUserService;
import br.com.lojavirtual.service.ServiceSendEmail;
import br.com.lojavirtual.util.ValidaCnpj;
import br.com.lojavirtual.util.ValidaCpf;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	/*End-point ou microserviço ou API*/
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody  @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{ //@RequestBody recebe um json
		
		
		
		if(pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa juridica nao pode ser nulo");
		}
		
		if(!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("CNPJ " + pessoaJuridica.getCnpj() + " é inválido.");
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionMentoriaJava("Já existe CNPJ cadastro com o numero: " + pessoaJuridica.getCnpj());
		}
		
		if(pessoaJuridica.getId() == null && pessoaRepository.existeInscEstadualCadastrado(pessoaJuridica.getInscEstadual()) != null) {
			throw new ExceptionMentoriaJava("Já existe Inscrição Estadual cadastro com o numero: " + pessoaJuridica.getInscEstadual());
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica,HttpStatus.OK);
	}
	
	@GetMapping("/teste-email")
    public ResponseEntity<String> testeEmail() {
        try {
            serviceSendEmail.enviarEmailHtml("Teste de Email", "<h1>Funcionou!</h1>", "bonety@gmail.com");
            return ResponseEntity.ok("Email enviado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar email: " + e.getMessage());
        }
    }
	
	/*End-point ou microserviço ou API*/
	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{
		
		if(pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa física nao pode ser nulo");
		}
		
		if(!ValidaCpf.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF " + pessoaFisica.getCpf() + " é inválido.");
		}
		
		if(pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()) != null) {
			throw new ExceptionMentoriaJava("Já existe CPF cadastro com o numero: " + pessoaFisica.getCpf());
		}
		
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica,HttpStatus.OK);
	}
}
