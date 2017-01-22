package org.mz.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DrawnKittyAction extends ActionSupport implements ModelDriven<KittyModal>{

	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal=new KittyModal();

	HttpSession session;
	
	public String execute() {

			session=ServletActionContext.getRequest().getSession();
			session.setAttribute("name",kittyModal.getName());
			KittyService kittyService=new KittyService();
			boolean result=kittyService.myLogin(kittyModal);			
			if(result){
				return "drawn";
			}
			else{
				return ERROR;
			}
	}

	public String logOut()
	{
		ServletActionContext.getRequest().getSession().invalidate();
		return SUCCESS;
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

	public String check1(){
		return  SUCCESS;
	}
}
