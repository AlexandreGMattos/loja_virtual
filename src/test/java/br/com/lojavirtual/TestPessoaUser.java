package br.com.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.lojavirtual.controller.PessoaController;
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
		
		
		
		pessoaController.salvarPj(pessoaJuridica);
	}
}
