package org.mz.action;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ForgetPasswordAction extends ActionSupport implements ModelDriven<KittyModal>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal=new KittyModal();
	private String mailStatus;
	
	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public KittyModal getKittyModal() {
		return kittyModal;
	}

	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}

	public String execute(){
		
		String name;
		String password;
		KittyService kittyService=new KittyService();
		boolean result=kittyService.forgetPassword(kittyModal);
		kittyModal.getName();
		kittyModal.getPassword();
		
		if(result)
		{
			Properties properties =new Properties();
			 properties.put("mail.smtp.host", "smtp.gmail.com");  
			  properties.put("mail.smtp.socketFactory.port", "465");  
			  properties.put("mail.smtp.socketFactory.class",  
			            "javax.net.ssl.SSLSocketFactory");  
			  properties.put("mail.smtp.auth", "true");  
			  properties.put("mail.smtp.port", "587");  

			try {
				
				name=kittyModal.getName();
				password=kittyModal.getPassword();
			Session session = Session.getDefaultInstance(properties,	new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
						 String username, password;
						  username="organize.kittyparty@gmail.com";
						  password="kittyparty";					  
						  return new PasswordAuthentication(username,password);
				}
			});
				Message message = new MimeMessage(session);
				System.out.println("authenticating. . ");
				message.setFrom(new InternetAddress("organize.kittyparty@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(name));
				message.setSubject("KittyParty Admin Password Change");
				message.setText("Your Password is:-"+password);
				Transport.send(message);
				System.out.println("sent mail");
				setMailStatus("Mail Sent!!");

			} 
			catch (MessagingException e)
			{
				throw new RuntimeException(e);
			}
			
		}
		return SUCCESS;
		
		
		
	}

	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return kittyModal;
	}
	
}
