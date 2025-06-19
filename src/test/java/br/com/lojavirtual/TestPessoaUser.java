package br.com.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.lojavirtual.controller.PessoaController;
import br.com.lojavirtual.enums.TipoEndereco;
import br.com.lojavirtual.model.Endereco;
import br.com.lojavirtual.model.PessoaFisica;
import br.com.lojavirtual.model.PessoaJuridica;
import br.com.lojavirtual.repository.PessoaRepository;
import br.com.lojavirtual.service.PessoaUserService;

@Profile("dev") 
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestPessoaUser {

	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		pessoaJuridica.setCnpj("4" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Alexandre");
		pessoaJuridica.setEmail("alexandreTESTE@gmail.com");
		pessoaJuridica.setTelefone("44555787686");
		pessoaJuridica.setInscEstadual("33243242424");
		pessoaJuridica.setInscMunicipal("343434242342");
		pessoaJuridica.setNomeFantasia("Empresa X");
		pessoaJuridica.setRazaoSocial("empresa X");
		pessoaJuridica.setTipoPessoa("Clinte");
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("jardim das torres");
		endereco1.setCep("454654654");
		endereco1.setCidade("Mandaguari");
		endereco1.setComplemento("casa verde");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("34");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogra("rua abacaxo");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("PR");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("jardim imperial");
		endereco2.setCep("454654654");
		endereco2.setCidade("Mandaguari");
		endereco2.setComplemento("apto 3");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("444");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogra("av joao do pulo");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("PR");
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);
		
		
		pessoaController.salvarPj(pessoaJuridica);
	}
}
