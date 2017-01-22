package org.mz.action;

import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AddFriendAction extends ActionSupport implements ModelDriven<KittyModal>  {

	
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
		int addFriends=kittyService.addFriend(kittyModal);
		if( addFriends>0){
			kittyModal.setStatus("Friend added Successfully");
			return "success";
		}
		else{
			kittyModal.setStatus("Friend Not Added");
			return "fail";
		}
		
		
	}
	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return kittyModal;
	}

}
