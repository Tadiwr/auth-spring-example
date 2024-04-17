package com.shangwa.auth.service;

import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    // JavaMailSender emailSender = new JavaMailSenderImpl();
    
    @Autowired
    private JavaMailSender emailSender;


    private String generateEmailText(String token, User user) {

        
        String emailText = String.format("""
            <div>
            <h1>Hello there %s!</h1>
            <p>We are glad to have you on board to complete your registration on the our app please verify that this is your email</p>
            <p>Click the link below to verify your email</p>
            <a href="http://localhost:8080/auth/email/verify?token=%s" >Verify Email</a>

            <p>If you did not sign up to our platform just ignore or delete this email</p>
            </div>
            """, user.getName(), token, token);

        return emailText;
    }

    /** Attempts to send an email and throws a MessagingException if it fails */
    public void sendVerficationEmail(String token, User user) throws MessagingException {

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper emailBuilder = new MimeMessageHelper(email);
        
        emailBuilder.setTo(user.getEmail());
        emailBuilder.setSubject("Simple Spring Auth | Email Varification Code");
        emailBuilder.setText(generateEmailText(token, user), true);
        emailBuilder.setFrom("codapt@outlook.com");
        emailSender.send(email);
    }

}
