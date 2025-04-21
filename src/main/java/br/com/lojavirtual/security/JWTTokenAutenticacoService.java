package br.com.lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.lojavirtual.ApplicationContextLoad;
import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

// criar e retornar a autenticacao JWT
@Service
@Component
public class JWTTokenAutenticacoService {

	//Token de validade para 30 dias
	private static final long EXPIRATION_TIME = 959990000;
	
	//chave secreta, pode ser qualquer coisa. É uma chave que sera usada junto com o JWT
	private static final String SECRET = "sdvsdsdggsfwe64646465sdf8845sdfsdfs";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	//Gera o token e da a resposta para o cliente com o JWT
	public void addAuthentication(HttpServletResponse response, String username) throws Exception{
		
		//Montagem do Token
		
		String JWT = Jwts.builder(). //chama o gerador de token
				setSubject(username). //adiciona o user
				setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)). // tempo de expiração. System.currentTimeMillis() data de hoje
				signWith(SignatureAlgorithm.HS512, SECRET).compact();// gera todo o token
		
		//Exemplo de como vai ficar o token. Bearer 45454dfsdfsdf45f4wef5.w54ef64fwffsdffw3.effff/sdf5sdf4sdf
		String token = TOKEN_PREFIX + " " + JWT;
		
		
		//da a resposta para a tela e para o cliente, navegador, api, javascript ou qualquer outra coisa que chamou ela
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		//usado para ver no Postman par teste
		response.getWriter().write("{\"Authorization\": \""+ token +"\"}");
	}
	
	
	//Retorna o usuario validado atravez do token caso contrario retorna null
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_STRING);
		
		try {
			
			if(token != null) {
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim(); // remove a palavra Bearer do token
				
				// faz a validacao do token do usuario na requisicao e obtem o USER
				String user = Jwts.parser(). //parser -> converte
						setSigningKey(SECRET). // pode ser um certificado digital ou outra coisa
						parseClaimsJws(tokenLimpo).
						getBody().getSubject();
				
				if(user != null) {
					
					Usuario usuario = ApplicationContextLoad.
							getApplicationContext().
							getBean(UsuarioRepository.class).findUserByLogin(user);
					
					if(usuario != null) {
						return new UsernamePasswordAuthenticationToken(usuario.getLogin(),usuario.getPassword(), usuario.getAuthorities());
					}
				}
						
			}
		}catch (SignatureException e) {
			response.getWriter().write("Token inválido.");
		}catch (ExpiredJwtException e) {
			response.getWriter().write("Token expirado, efetue o login novamente.");
		}catch (MalformedJwtException e) {
			response.getWriter().write("Token inválido.");
		}
		finally {
			liberacaoCors(response);
		}
		
		return null;
	}
	
	
	//Faz liberação contra erros de Cors no navegador
	private void liberacaoCors(HttpServletResponse response) {
		
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
	
}

















