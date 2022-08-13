package ua.edu.sumdu.nefodov.sheltered.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailSenderService (JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void send(String to, String subject, String text) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper mmh = new MimeMessageHelper(msg, "utf-8");
            mmh.setTo(to);
            mmh.setSubject(subject);
            mmh.setText(text);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new IllegalStateException("Неможливо надіслати листа");
        }
    }
}
