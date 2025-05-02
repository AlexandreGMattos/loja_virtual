package br.com.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

//Filtro onde todas as requisicoes serao capturadas para AUTenticar
public class JwtApiAutenticacaoFilter extends GenericFilterBean{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			
			// estabelece a autenticacao do usuario

			Authentication authentication = new JWTTokenAutenticacoService()
					.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

			// coloca o processo de autenticacao para o spring security
			SecurityContextHolder.getContext().setAuthentication(authentication);

			chain.doFilter(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Ocorreu um erro no sistema: \n" + e.getMessage());
		}
	}

}
