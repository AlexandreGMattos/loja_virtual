package br.com.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		
		//usado para ver no Postman par teste
		response.getWriter().write("{\"Authorization\": \""+ token +"\"}");
	}
	
}

















