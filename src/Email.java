import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Cette classe définit l'objet Email, mais cet objet n'est pas directement, il est juste instancié pour envoyer un mail.
 * @author Julien EMMANUEL, Charlotte RICHAD, Thomas DUTOUR, Alexis SAGET, Crunchify.com
 */
public class Email 
{
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 
	/**
	 * Ce constructeur ne définit pas vraiment un objet mais appelle la méthode d'envoi de mail.
	 * @param gagne Vrai si Zaya a gagné, Faux sinon.
	 * @param bateauxRestants Le nombre de bateaux restants du gagnant.
	 * @param commentaire Le commentaire éventuellement laissé par le joueur.
	 * @throws AddressException Erreur dans l'envoi d'email.
	 * @throws MessagingException Erreur dans l'envoi d'email.
	 */
	public Email(boolean gagne, int bateauxRestants, String commentaire) throws AddressException, MessagingException 
	{
		generateAndSendEmail(gagne, bateauxRestants, commentaire);
	}
	/**
	 * Envoie un mail pour indiquer l'issue de la partie.
	 * @param gagne Vrai si Zaya a gagné, Faux sinon.
	 * @param bateauxRestants Le nombre de bateaux restants du gagnant.
	 * @param commentaire Le commentaire éventuellement laissé par le joueur.
	 * @throws AddressException Erreur dans l'envoi d'email.
	 * @throws MessagingException Erreur dans l'envoi d'email.
	 */
	public static void generateAndSendEmail(boolean gagne, int bateauxRestants, String commentaire) throws AddressException, MessagingException 
	{
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("Julien.Emmanuel@insa-lyon.fr"));
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("Charlotte.Richard@insa-lyon.fr"));
		String emailBody;
		if(gagne)
		{
			generateMailMessage.setSubject("Zaya a gagné une partie !", "UTF-8");
			emailBody = "Zaya a gagné une partie de Bataille Navale ! \r\n \r\nBateaux restants de Zaya : "+bateauxRestants+"\r\n \r\nCommentaire du joueur : \r\n"+commentaire;
		}
		else
		{
			generateMailMessage.setSubject("Zaya a perdu une partie.", "UTF-8");
			emailBody = "Zaya a perdu une partie de Bataille Navale. \r\n \r\nBateaux restants du joueur : "+bateauxRestants+"\r\n \r\nCommentaire du joueur : \r\n"+commentaire;
		}
		generateMailMessage.setText(emailBody, "UTF-8");

		Transport transport = getMailSession.getTransport("smtp");
		
		transport.connect("smtp.gmail.com", "BatailleNavaleG1P58INSA", "CharlotteThomasAlexis");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}