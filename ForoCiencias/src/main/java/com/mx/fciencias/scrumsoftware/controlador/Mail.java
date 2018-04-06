/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;

/**
 * A singleton class for sending mail messages.
 * @author raziel
 */


public class Mail{
    
     public static String toToken(String text) {
        byte[] encoded = Base64.encodeBase64(text.getBytes());
        return new String(encoded);
      
    }

    public void sendMail(String token, String Subject, String To) {
        String Username = "scrumsoftwareis";
        String PassWord = "scrumsoftware2018";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
 
        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
              
            String contenido = "<a href="
                    + "http://localhost:8080/ForoCiencias/faces/ActivacionUsuarioIH.xhtml>"
                 + "Haga click aquí para activar tu cuenta.</a> <br> Ingresa el siguiente token: </br><b>"
                 + toToken(token) + " </b>";
     
         message.setContent(contenido, "text/html");
 
            Transport.send(message);
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}