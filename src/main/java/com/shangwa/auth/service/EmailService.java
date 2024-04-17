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
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    // JavaMailSender emailSender = new JavaMailSenderImpl();
    
    @Autowired
    private JavaMailSender emailSender;


    private String generateEmailText(String token, User user) {

        
        String emailText = String.format("""
            <div>
            <h1>Hello %s!</h1>
            <p>Click the link below to verify your email</p>
            <a href="%s" >Verify Email</a>
            </div>
            """, user.getName(), token);

        return emailText;
    }

    /** Attempts to send an email and throws a MessagingException if it fails */
    public void sendVerficationEmail(String token, User user) throws MessagingException {

        MimeMessage email = emailSender.createMimeMessage();
        MimeMessageHelper emailBuilder = new MimeMessageHelper(email);
        
        emailBuilder.setTo(user.getEmail());
        emailBuilder.setFrom("noreply@simple.auth.dev");
        emailBuilder.setSubject("Email Varification Code");
        emailBuilder.setText(generateEmailText(token, user), true);
        // emailBuilder.setFrom("tshangmgs@gmail.com");
        emailSender.send(email);

    }

}
