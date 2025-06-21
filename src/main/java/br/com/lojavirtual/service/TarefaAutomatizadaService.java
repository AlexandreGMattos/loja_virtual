package br.com.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.lojavirtual.model.Usuario;
import br.com.lojavirtual.repository.UsuarioRepository;

@Component
@Service
public class TarefaAutomatizadaService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Scheduled(initialDelay = 200, fixedDelay = 86400000)/*Roda a cada 24hrs. InitialDelay: demora 2 segundos para subir e fixed equivale a 24hrs*/
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")/*Vai rodar todos os dias as 11hrs da manha cron= seg, minuto, hora,dia, mes, dia da semana*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		System.out.println("Tarefa automatizadas");
		
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		for (Usuario usuario : usuarios) {
			
			
			StringBuilder msg = new StringBuilder();
			msg.append("Olá ").append(usuario.getPessoa().getNome()).append(" </br>");
			msg.append("Está na hora de trocar a senha, já passou 90 dias de validade </br>");
			msg.append("Troque sua senha da loja virtual");
			
			serviceSendEmail.enviarEmailHtml("Troca de senha", msg.toString(), usuario.getLogin());
			
			System.out.println("Usuario: "+ usuario.getLogin());
			
			Thread.sleep(3000);
		}
	}
}
