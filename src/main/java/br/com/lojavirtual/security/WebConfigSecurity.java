package br.com.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener{

	@Autowired
	private ImplementacaoUserDetailsService ImplementacaoUserDetailsService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.disable().authorizeRequests().antMatchers("/").permitAll() // libera o acesso para a tela principal do sistema, a tela de login
		.antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		/*redireciona ou da um retorno para o index quando desloga do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*mapeia o logout do sistema*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		/*filtra as requisicoes para login de jwt*/
		.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()), 
				UsernamePasswordAuthenticationFilter.class)
		
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	//Irá consultar o usuario no banco com spring security
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(ImplementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		 
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso","/deleteAcesso/","/obterAcesso").
		//antMatchers(HttpMethod.POST,"/salvarAcesso", "/deleteAcesso","/obterAcesso");
		/*ignorando URL no momento para nao autenticar*/
	}
	
}
