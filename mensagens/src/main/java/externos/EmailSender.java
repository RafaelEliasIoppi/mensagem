package externos;

import entidades.Mensagem;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static final String USERNAME = "rafaelioppi@gmail.com"; // Coloque o seu email
    private static final String PASSWORD = "aotg adit ujkj fxfx"; // Coloque a sua senha

    public static void enviarEmail(Mensagem mensagem, String conteudoEmail ) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mensagem.getEmail()));
            message.setSubject("Nova Mensagem Cadastrada");
            message.setText(mensagem.getMensagem()); // Usa o conteúdo da mensagem já montado
            message.setText(conteudoEmail); // Usa o conteúdo do email passado como parâmetro

            Transport.send(message);
            System.out.println("Email enviado com sucesso!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}