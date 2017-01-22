package org.mz.action;

import java.util.ArrayList;

import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DeleteAction extends ActionSupport implements ModelDriven<KittyModal> {

	
	private ArrayList<KittyModal> arrayList;
	public ArrayList<KittyModal> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<KittyModal> arrayList) {
		this.arrayList = arrayList;
	}

	private String email;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute(){
		KittyService kittyService=new KittyService();
		kittyService.delete(email);
		arrayList=kittyService.view();
		return SUCCESS;
		
	}

	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
