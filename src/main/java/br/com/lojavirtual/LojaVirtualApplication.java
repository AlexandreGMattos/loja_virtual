package br.com.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.lojavirtual.model") // mapeia onde esta as classes do banco no projeto
@ComponentScan(basePackages = {"br.*"})
@EnableJpaRepositories(basePackages = {"br.com.lojavirtual.repository"})
@EnableTransactionManagement // transacao com o banco de dados
public class LojaVirtualApplication {

	public static void main(String[] args) {
		
		
		//System.out.println(new BCryptPasswordEncoder().encode("123")); // gera um token para a senha 123
		
		
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

}
