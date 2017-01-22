package org.mz.action;

import java.util.ArrayList;

import org.mz.modal.KittyPartyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ViewKitties extends ActionSupport implements ModelDriven<KittyPartyModal>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<KittyPartyModal> arrayList;



	public String execute(){
		
		KittyService kittyService=new KittyService();
		arrayList=kittyService.viewKitties();
			return SUCCESS;
	}
	
	public ArrayList<KittyPartyModal> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<KittyPartyModal> arrayList) {
		this.arrayList = arrayList;
	}
	
	@Override
	public KittyPartyModal getModel() {
		return null;
	}
	

}
