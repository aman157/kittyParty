package org.mz.action;

import java.util.ArrayList;

import org.mz.modal.KittyPartyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class InvitationInfoAction extends ActionSupport implements ModelDriven<KittyPartyModal> {
	
	private KittyPartyModal kittyPartyModal=new KittyPartyModal();
	public KittyPartyModal getKittyPartyModal() {
		return kittyPartyModal;
	}


	public void setKittyPartyModal(KittyPartyModal kittyPartyModal) {
		this.kittyPartyModal = kittyPartyModal;
	}


	private String adminName;
	

	private static final long serialVersionUID = 1L;

	public String execute(){
		int res=5000;
		KittyService kittyService=new KittyService();
		String admin=getAdminName();
		ArrayList<String> totalRecipientCard=kittyService.totalRecipientCard(admin);
		int s=totalRecipientCard.size();
		System.out.println("THe size="+s);
		KittyPartyModal kpModal=kittyService.InvitationInfo(admin,kittyPartyModal);
		String w=kittyPartyModal.getBudget();
		int q=Integer.parseInt(w);
		res=q/s;
		String res1=""+res;
		kittyPartyModal.setBudget(res1);
		return SUCCESS;
	}
	
	
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	@Override
	public KittyPartyModal getModel() {
		// TODO Auto-generated method stub
		return kittyPartyModal;
	}
}
