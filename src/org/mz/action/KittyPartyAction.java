package org.mz.action;

import org.mz.modal.KittyPartyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class KittyPartyAction extends ActionSupport implements ModelDriven<KittyPartyModal>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KittyPartyModal kittyPartyModal=new KittyPartyModal();
	
	
	public String execute(){
		
		KittyService kittyService=new KittyService();
		int result=kittyService.kittyParty(kittyPartyModal);
		if(result>0){
		return "success";	
		}
		return "fail";
	}


	@Override
	public KittyPartyModal getModel() {
		// TODO Auto-generated method stub
		return kittyPartyModal;
	}

}
