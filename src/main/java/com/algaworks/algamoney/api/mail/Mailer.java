package com.algaworks.algamoney.api.mail;

import java.util.HashMap;
//import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Usuario;
import com.algaworks.algamoney.api.repository.LancamentoRepository;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	//usada para processar o template HTML
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private LancamentoRepository repo;
	
	
  //Este teste funciona SE o remetente for um email meu e no application properties for colocado o meu user e password
	/* @EventListener
	private void teste(ApplicationReadyEvent event) {
	this.enviarEmail("testesrenuncio@gmail.com", 
				Arrays.asList("danielarenuncio@gmail.com"), 
				"Testando", "Olá!<br/>Teste ok.");
	System.out.println("Terminado o envio de e-mail...");
	}
	
	@EventListener
	private void teste(ApplicationReadyEvent event) {
		
		String template = "mail/aviso-lancamentos-vencidos";
	
	    List<Lancamento> lista = repo.findAll();
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", lista);
		 
	    this.enviarEmail("testesrenuncio@gmail.com", 
		Arrays.asList("danielarenuncio@gmail.com"), 
		   "Testando", template, variaveis);
	System.out.println("Terminado o envio de e-mail...");
	
	}*/
	
	
	public void avisarSobreLancamentosVencidos(
			List<Lancamento> vencidos, List<Usuario> destinatarios) {
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream()
				.map(u -> u.getEmail())
				.collect(Collectors.toList());
		
		this.enviarEmail("testesrenuncio@gmail.com", 
				emails, 
				"Lançamentos vencidos", 
				"mail/aviso-lancamentos-vencidos", 
				variaveis);
	}
	
	public void enviarEmail(String remetente, 
			List<String> destinatarios, String assunto, String template, 
			Map<String, Object> variaveis) {
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(
				e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void enviarEmail(String remetente, 
			List<String> destinatarios, String assunto, String mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e); 
		}
	}
}