package br.com.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

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
import br.com.lojavirtual.enums.TipoPessoa;
import br.com.lojavirtual.model.Endereco;
import br.com.lojavirtual.model.PessoaFisica;
import br.com.lojavirtual.model.PessoaJuridica;
import br.com.lojavirtual.model.dto.CepDTO;
import br.com.lojavirtual.model.dto.ConsultaCnpjDTO;
import br.com.lojavirtual.repository.EnderecoRepository;
import br.com.lojavirtual.repository.PessoaFisicaRepository;
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
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaFisicaRepository  pessoaFisicaRepository;
	
	@ResponseBody
	@GetMapping(value = "**/consultaPFNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPFNome(@PathVariable("nome") String nome){
		
		List<PessoaFisica> lista = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaFisica>>(lista, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPFcpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPFcpf(@PathVariable("cpf") String cpf){
		
		List<PessoaFisica> lista = pessoaFisicaRepository.existeCpfCadastradoList(cpf);   
		
		return new ResponseEntity<List<PessoaFisica>>(lista, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaNomePJ/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaNomePJ(@PathVariable("nome") String nome){
		
		List<PessoaJuridica> lista = pessoaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaJuridica>>(lista, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjPJ/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaCnpjPJ(@PathVariable("cnpj") String cnpj){
		
		List<PessoaJuridica> lista = pessoaRepository.existeCnpjCadastradoList(cnpj);
		
		return new ResponseEntity<List<PessoaJuridica>>(lista, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}") // passando uma variavel {cep}
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
		
		CepDTO cepDTO = pessoaUserService.consultaCep(cep);
		
		return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjReceitaWs/{cnpj}") // passando uma variavel {cnpj}
	public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWs(@PathVariable("cnpj") String cnpj){
		
		return new ResponseEntity<ConsultaCnpjDTO>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);		
	}
	
	/*End-point ou microserviço ou API*/
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj(@RequestBody  @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{ //@RequestBody recebe um json
		
		
		
		if(pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa juridica nao pode ser nulo");
		}
		
		if(pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoriaJava("Informe o tipo Jurídico ou Fornecedor da LOJA.");
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
		
		if(pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(i).getCep());
				
				pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.getUf());
			}
		}else {
			for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(i).getId()).get();// busca id no banco
				
				if(!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(i).getCep())) {
					CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(i).getCep());
					
					pessoaJuridica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(i).setRuaLogra(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(i).setUf(cepDTO.getUf());
				}
			}
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
		
		if(pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
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
