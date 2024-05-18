package com.maace.connectEtec.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailDeValidacao(String destinatario, int numeroDeValicao) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("Código de Validação");

            String mensagemHtml = "<html><body style='text-align:center;'>" +
                    "<p style='font-size:24px;'>Seu número de validação é:</p>" +
                    "<p style='font-size:36px; font-weight:bold;'>" + numeroDeValicao + "</p>" +
                    "</body></html>";

            helper.setText(mensagemHtml, true);

            javaMailSender.send(mimeMessage);
            return "E-mail de validação enviado com sucesso";
        } catch (MessagingException e) {
            return "Erro ao enviar e-mail de validação: " + e.getMessage();
        }
    }
}
