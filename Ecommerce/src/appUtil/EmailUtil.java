package appUtil;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	//Method for sending email messages.
	public static void sendEmail(String recipient, String sender,
			String subject, String body, boolean bodyIsHTML)
			throws MessagingException {

		// Get a mail session
		Properties properties = new Properties();

		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtps.host", "localhost");
		properties.put("mail.smtps.port", 25);
		Session session = Session.getDefaultInstance(properties);
		session.setDebug(true);

		// Create a message
		Message message = new MimeMessage(session);
		message.setSubject(subject);

		if (bodyIsHTML) {
			message.setContent(body, "text/html");
		} else {
			message.setText(body);
		}

		// Here we set the addresses
		Address fromAddress = new InternetAddress(sender);
		Address toAddress = new InternetAddress(recipient);
		message.setRecipient(Message.RecipientType.TO, toAddress);
		message.setFrom(fromAddress);
		Transport.send(message);

	}
}
