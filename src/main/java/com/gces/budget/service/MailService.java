package com.gces.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by minamrosh on 11/25/15.
 */

@Service
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public void setJavaMailSender(){

    }

    public void sedMail(){

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress("np.msushil@gmail.com"));

                mimeMessage.setFrom(new InternetAddress("np.msushil@gmail.com"));
                mimeMessage.setText("Test Message from Budget Visualization");
            }

        };

        try {
            this.mailSender.send(preparator);
        }
        catch (MailException mailEx){
            System.err.println(mailEx.getMessage());
        }



    }
}
