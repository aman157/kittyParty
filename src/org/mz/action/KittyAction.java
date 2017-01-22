package org.mz.action;

import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class KittyAction extends ActionSupport implements ModelDriven<KittyModal> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal=new KittyModal();
	
	
	public void validate(){
		if(kittyModal.getName().isEmpty()){
			kittyModal.setStatus("Name cant be null");
			
		}
		if(kittyModal.getEmail().trim().equals("")){
			kittyModal.setStatus("email cant be null");
		}
		if(kittyModal.getPassword().trim().equals("")){
			kittyModal.setStatus("password cant be null");
		}
		if(kittyModal.getDateofbirth().trim().equals("")){
			kittyModal.setStatus("Date of Birth cant be null");
		}
	}
	
	public String execute(){
		
		KittyService kittyService=new KittyService();
		int result=kittyService.mySignup(kittyModal);
		if(result==1){
			kittyModal.setStatus("SignUp Successfully");
			return "success";
		}
		else
		{
			kittyModal.setStatus("Signup Failed!!* User Already Register or Database Not Avaliable");
			return "success";
		}
		
	}

	public KittyModal getKittyModal() {
		return kittyModal;
	}

	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}

	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return kittyModal;
	}

}
