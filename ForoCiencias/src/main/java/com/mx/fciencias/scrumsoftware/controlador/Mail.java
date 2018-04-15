
package com.mx.fciencias.scrumsoftware.controlador;

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
- *  La clase <code>Mail</code> 
- *
- * Creado o modificado: martes 27 de marzo de 2018.
- *
- * @author <a href="mailto:razielmcr1@ciencias.unam.mx"></a>
- * @version 1.1
  */


public class Mail{
    
    
    /**
     * Envia correo al usuario.
     * @param text - Usuario que se pasará a base 64 (tokne)
     * @return <code>String</code> - El token segun el usuario.
     */
    public String toToken(String text) {
        byte[] encoded = Base64.encodeBase64(text.getBytes());
        return new String(encoded);
      
    }
     
    /**
     * Envia correo al usuario.
     * @param token - Token que se envía al usuario
     * @param subject - Subject del mil a enviar.
     * @param to - A quién se enviará
     */
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
                    + "http://localhost:8080/ForoCiencias/faces/ActivacionUsuarioIH.xhtml?token="
                    + toToken(token) + ">"
                 + "Haga click aquí para activar tu cuenta.</a> <br> Ingresa el siguiente token: </br><b>"
                 + toToken(token) + " </b>";
     
         message.setContent(contenido, "text/html");
 
            Transport.send(message);
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}