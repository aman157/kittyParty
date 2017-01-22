package org.mz.action;

import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SaveChangesAction extends ActionSupport implements ModelDriven<KittyModal> {
	
	

	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal = new KittyModal();
	


	public String execute(){
		
		KittyService kittyService=new KittyService();
		
		 int result=kittyService.saveChanges(kittyModal);
		if(result>0){
		return SUCCESS;
		}
		return ERROR;
		
	}

	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return kittyModal;
	}

	
	public KittyModal getKittyModal() {
		return kittyModal;
	}

	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}
}
