package Email;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {
	public static void send(String toEmail, String emailMessage){
	  // Sender's email ID needs to be mentioned
	  // String from = "test@gmail.com";
	  if(!isValidEmail(toEmail)){
		  System.out.println("ERROR: You must enter a valid email.");
		  return;
	  }
	  String from = "cookingwfriend@gmail.com";
      String pass ="cs032_eclipse";
	  // Recipient's email ID needs to be mentioned.
	  String to = toEmail;
	  String host = "smtp.gmail.com";
	  // Get system properties
	  Properties properties = System.getProperties();
	  // Setup mail server
	  properties.put("mail.smtp.starttls.enable", "true");
	  properties.put("mail.smtp.host", host);
	  properties.put("mail.smtp.user", from);
	  properties.put("mail.smtp.password", pass);
	  properties.put("mail.smtp.port", "587");
	  properties.put("mail.smtp.auth", "true");
	
	  // Get the default Session object.
	  Session session = Session.getDefaultInstance(properties);
	
	  try{
	      // Create a default MimeMessage object.
	  MimeMessage message = new MimeMessage(session);
	
	  // Set From: header field of the header.
	  message.setFrom(new InternetAddress(from));
	   
	  message.addRecipient(MimeMessage.RecipientType.TO,
		                               new InternetAddress(to));
	
	      // Set Subject: header field
	  message.setSubject("This is the Subject Line!");
	
	  // Now set the actual message
	  message.setText(emailMessage);
	
	  // Send message
	  Transport transport = session.getTransport("smtp");
	      transport.connect(host, from, pass);
	      transport.sendMessage(message, message.getAllRecipients());
	      transport.close();
	   }catch (MessagingException mex) {
		   System.out.println("ERROR: Could not send email.");
	      mex.printStackTrace();
	      return;
	   }
	
	}
	
	public static boolean isValidEmail(String email){
		if(email.trim().length()!=0){
			String[] atSplit = email.split("\\@");
			if(atSplit.length==2){
				String[] dotSplit = atSplit[1].split("\\.");
				if(dotSplit.length>1){
					return true;
				}
			}
		}
		return false;
	}
}
