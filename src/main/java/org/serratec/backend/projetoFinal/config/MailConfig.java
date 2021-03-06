package org.serratec.backend.projetoFinal.config;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailConfig {

	@Autowired
	JavaMailSender javaMail;

	public String sendEmail(String para, String assunto, String texto) throws MessagingException {

		MimeMessage mail = javaMail.createMimeMessage();

		mail.setSubject(assunto);

		MimeMessageHelper helper = new MimeMessageHelper(mail, true);

		helper.setFrom("grupo5.serratec@gmail.com");

		helper.setTo(para);

		helper.setText(texto, true);

		javaMail.send(mail);

		return "E-mail enviado com sucesso";

	}
}