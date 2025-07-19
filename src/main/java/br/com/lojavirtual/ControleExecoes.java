package br.com.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lojavirtual.model.dto.ObjetoErroDto;
import br.com.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControleExecoes extends ResponseEntityExceptionHandler {

	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@ExceptionHandler(ExceptionMentoriaJava.class)
	public ResponseEntity<Object> handleExceptionCustom (ExceptionMentoriaJava ex){
		ObjetoErroDto objetoErroDto = new ObjetoErroDto();
		
		objetoErroDto.setErro(ex.getMessage());
		objetoErroDto.setCode(HttpStatus.BAD_REQUEST.toString());
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.BAD_REQUEST);
	}
	
	
	
	//Captura as excecoes do projeto
	//@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ObjetoErroDto objetoErroDto = new ObjetoErroDto();
		
		String msg = "";
		if(ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "\n";
			}
		}else if(ex instanceof HttpMessageNotReadableException) {
			msg = "Não esta sendo enviado dados para o corpo da requisição";
		} else {
			msg = ex.getMessage();
		}
		
		objetoErroDto.setErro(msg);
		objetoErroDto.setCode(status.value() + " -> " + status.getReasonPhrase());
		
		ex.printStackTrace();
		try {
			serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "alexandregmattos@gmail.com");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//Captura execoes dos bancos
	@ExceptionHandler({DataIntegrityViolationException.class, 
		ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
		
		ObjetoErroDto objetoErroDto = new ObjetoErroDto();
		String msg = "";
		
		if(ex instanceof DataIntegrityViolationException) {
			msg = "Erro de integridade do banco: " + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		}else if(ex instanceof ConstraintViolationException) {
			msg = "Erro de chave estrangeira: " + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		}else if(ex instanceof SQLException) {
			msg = "Erro de SQL do banco: " + ((SQLException) ex).getCause().getCause().getMessage();
		}else {
			msg = ex.getMessage();
		}
		
		objetoErroDto.setErro(msg);
		objetoErroDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		ex.printStackTrace();
		
		try {
			serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "alexandregmattos@gmail.com");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
















