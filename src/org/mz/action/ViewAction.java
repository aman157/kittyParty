package org.mz.action;

import java.util.ArrayList;

import org.mz.modal.AddFriendModal;
import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ViewAction extends ActionSupport implements ModelDriven<KittyModal>  {
	
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<KittyModal> arrayList;



	public ArrayList<KittyModal> getArrayList() {
		return arrayList;
	}


	public void setArrayList(ArrayList<KittyModal> arrayList) {
		this.arrayList = arrayList;
	}


	public String execute(){
		
		KittyService kittyService=new KittyService();
		arrayList=kittyService.view();
		return "success";
		
	}


	@Override
	public KittyModal getModel() {
		// TODO Auto-generated method stub
		return null;
	}




}
