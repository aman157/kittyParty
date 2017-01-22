package org.mz.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.mz.modal.KittyModal;
import org.mz.service.KittyService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction<HttpServletRequest> extends ActionSupport implements ModelDriven<KittyModal>{




	private static final long serialVersionUID = 1L;
	private KittyModal kittyModal=new KittyModal();
	public KittyModal getKittyModal() {
		return kittyModal;
	}

	public void setKittyModal(KittyModal kittyModal) {
		this.kittyModal = kittyModal;
	}

	HttpSession session;

	public String execute() throws Exception{

		String loginChk=kittyModal.getChkClient();

		if(loginChk.equals("admin"))
		{
				KittyService kittyService=new KittyService();
				if(kittyService.myLogin(kittyModal))
				{
					session=ServletActionContext.getRequest().getSession();
					session.setAttribute("name",kittyModal.getName());
					return SUCCESS;
				}
				else
				{
					kittyModal.setStatus("invalid User !! SignUp first");
					return ERROR;
				}
		}
		if(loginChk.equals("member"))
		{
				KittyService kittyService=new KittyService();
				if(kittyService.profile(kittyModal))
				{
					session=ServletActionContext.getRequest().getSession();
					session.setAttribute("name",kittyModal.getName());
					return "profile";
				}
				else
				{
					kittyModal.setStatus("invalid User !! SignUp first");
					return ERROR;
				}
		}
		return loginChk;
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

	public String check(){

		return SUCCESS;

	}

	public String checkUser(){

		KittyService kittyService=new KittyService();
		boolean result=kittyService.profileInfo(kittyModal);
		if(result){
			return "profile";
		}
		else{
			return ERROR;
		}
	}
}

