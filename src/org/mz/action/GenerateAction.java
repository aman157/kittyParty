package org.mz.action;

import java.util.ArrayList;

import org.mz.modal.KittyPartyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class GenerateAction extends ActionSupport implements ModelDriven<KittyPartyModal>  {
	
	
	private KittyPartyModal kittyPartyModal=new KittyPartyModal();
	

	private static final long serialVersionUID = 1L;

	public String execute(){
		
		int res=5000;
		KittyService kittyService=new KittyService();
		ArrayList<String> totalRecipient=kittyService.TotalRecipient();
		int s=totalRecipient.size();
		if(s<1){
			kittyPartyModal.setKittyStatus("Please Add Some Friends");
			return SUCCESS;
		}
		kittyPartyModal=kittyService.generateInfo(kittyPartyModal);
		String w=kittyPartyModal.getBudget();
		if(w==null){
			kittyPartyModal.setKittyStatus("Organize Kittyparty First");
			return SUCCESS;
		}
		else{
		int q=Integer.parseInt(w);
		res=q/s;
		String res1=""+res;
		kittyPartyModal.setBudget(res1);
		return SUCCESS;
		}
		
	}

	public KittyPartyModal getKittyPartyModal() {
		return kittyPartyModal;
	}

	public void setKittyPartyModal(KittyPartyModal kittyPartyModal) {
		this.kittyPartyModal = kittyPartyModal;
	}

	@Override
	public KittyPartyModal getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
