package org.mz.action;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;

public class SendAction extends ActionSupport {

	private KittyModal kittyModal=new KittyModal();
	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public KittyModal getKittyModal() {
		return kittyModal;
	}

	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}

	private static final long serialVersionUID = -3905167121571383746L;

	public String execute()
	{
		InetAddress ip;
		String myIp;
		KittyService kittyService=new KittyService();
		ArrayList<String> totalRecipient=kittyService.send();

		for(int i=0;i<totalRecipient.size();i++){
			Properties properties =new Properties();
			 properties.put("mail.smtp.host", "smtp.gmail.com");  
			  properties.put("mail.smtp.socketFactory.port", "465");  
			  properties.put("mail.smtp.socketFactory.class",  
			            "javax.net.ssl.SSLSocketFactory");  
			  properties.put("mail.smtp.auth", "true");  
			  properties.put("mail.smtp.port", "465");

			try {
				
				ip=InetAddress.getLocalHost();
				myIp=ip.getHostAddress();
				
			Session session = Session.getDefaultInstance(properties,	new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
						 String username, password;
						 username="organize.kittyparty@gmail.com";
						  password="kittyparty";
						  return new PasswordAuthentication(username,password);
				}
			});
			HttpSession ses=ServletActionContext.getRequest().getSession();
			Object obj=ses.getAttribute("name");
			String sessionName=(String)obj;
			
			
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("organize.kittyparty@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(totalRecipient.get(i)));
				message.setSubject("KittyParty Invitation");
				message.setText("Click The Link to Check Your Invitation http://"+myIp+":8080/Kittyparty/invi?adminName="+sessionName+"  TO GET MORE DETAILS Login As Member With Your Email And PhoneNumber To Get The Details http://"+myIp+":8080/Kittyparty/");
				Transport.send(message);
				setMail("Mail Successfully Send");

			} 
			catch(javax.mail.SendFailedException e){
				
				setMail(" Error!!Check Connection");
			}
			catch (MessagingException e)
			{
				throw new RuntimeException(e);
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;


	}

}
