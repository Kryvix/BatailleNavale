import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class Email {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 
	public Email(boolean gagne) throws AddressException, MessagingException {
		generateAndSendEmail(gagne);
		//System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}
 
	public static void generateAndSendEmail(boolean gagne) throws AddressException, MessagingException {
 
//Step1		
		//System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		//System.out.println("Mail Server Properties have been setup successfully..");
 
//Step2		
		//System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("Julien.Emmanuel@insa-lyon.fr"));
		String emailBody;
		if(gagne)
		{
			generateMailMessage.setSubject("Zaya a gagné une partie !");
			emailBody = "Zaya a gagné une partie de Bataille Navale !";
		}
		else
		{
			generateMailMessage.setSubject("Zaya a perdu une partie.");
			emailBody = "Zaya a perdu une partie de Bataille Navale.";
		}
		generateMailMessage.setContent(emailBody, "text/html");
		//System.out.println("Mail Session has been created successfully..");
 
//Step3		
		//System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
		
		// Enter your correct gmail UserID and Password (XXXApp Shah@gmail.com)
		transport.connect("smtp.gmail.com", "BatailleNavaleG1P58INSA", "CharlotteAlexisThomas");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}