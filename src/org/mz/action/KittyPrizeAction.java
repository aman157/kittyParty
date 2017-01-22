package org.mz.action;

import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class KittyPrizeAction extends ActionSupport implements ModelDriven<KittyModal>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal=new KittyModal();

	public KittyModal getKittyModal() {
		return kittyModal;
	}
	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}
	public String execute(){
		
		KittyService kittyService=new KittyService();
		kittyService.generatePrize(kittyModal);
		
		String kitter=kittyModal.getName();
		boolean check=kittyService.appendKittyWinner(kitter,kittyModal);
		if(check){
			return SUCCESS;
		}
		else{
			
		return ERROR;
	}

		
		
		
	}
	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}
