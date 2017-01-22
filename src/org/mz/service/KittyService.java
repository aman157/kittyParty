package org.mz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.mz.common.KittyCommon;
import org.mz.modal.KittyModal;
import org.mz.modal.KittyPartyModal;

import com.mysql.jdbc.Statement;


public class KittyService  {
	
	
	public int mySignup(KittyModal kittyModal){

		int rowInserted=0;
		String name=kittyModal.getName();
		String email=kittyModal.getEmail();
		String password=kittyModal.getPassword();
		String dateofbirth=kittyModal.getDateofbirth();

		try{	
			Class.forName(KittyCommon.DRIVER);  
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);    
			PreparedStatement pstatement=connection.prepareStatement("insert into signup values(?,?,?,?)");  
			pstatement.setString(1,name);  
			pstatement.setString(2,email);  
			pstatement.setString(3,password);  
			pstatement.setString(4,dateofbirth); 
			rowInserted=pstatement.executeUpdate();  
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rowInserted;
	}

	public boolean myLogin(KittyModal kittyModal){

		boolean status=false;
		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="select * from signup where email='"+kittyModal.getName()+"' and password='"+kittyModal.getPassword()+"'";
			result=statement.executeQuery(query);
			status=result.next();
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			System.out.println("Enter The Valid Types");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return status;
	}



	public int kittyParty(KittyPartyModal kittyPartyModal) {

		HttpSession session=  ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String id=(String) obj;
		int rowInserted=0;
		String kittyParty=kittyPartyModal.getKittyParty();
		String place=kittyPartyModal.getPlace();
		String date=kittyPartyModal.getDate();
		String time=kittyPartyModal.getTime();
		String budget=kittyPartyModal.getBudget();
		String accountId=id;

		try{

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("insert into kittyparty (kittyparty, place, DATE,TIME,budget,AccountId) values ('"+kittyParty+"','"+place+"', '"+date+"','"+time+"','"+budget+"','"+accountId+"') on duplicate key update kittyparty=values(kittyparty), place=values(place), date=values(date), time=values(time),budget=values(budget)");
			rowInserted=pstatement.executeUpdate();  
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rowInserted;

	}

	public int addFriend(KittyModal kittyModal) {

		HttpSession session=  ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String id=(String) obj;

		int rowInserted=0;
		String name=kittyModal.getName();
		String mobile=kittyModal.getMobile();
		String email=kittyModal.getEmail();

		try{

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("insert into addfriend (name, mobile, email,adminId) values ('"+name+"','"+mobile+"', '"+email+"','"+id+"') on duplicate key update name=values(name), mobile=values(mobile), email=values(email)");
			rowInserted=pstatement.executeUpdate();  
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return rowInserted;
	}

	public KittyPartyModal generateInfo(KittyPartyModal kittyPartyModal) {


		HttpSession session=  ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String id=(String) obj;


		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="select * from kittyparty where AccountId='"+id+"'";
			result=statement.executeQuery(query);

			if(result.next()){

				String kittyParty=result.getString(1);
				String place=result.getString(2);
				String date=result.getString(3);
				String time=result.getString(4);
				String budget=result.getString(5);


				kittyPartyModal.setKittyParty(kittyParty);
				kittyPartyModal.setPlace(place);
				kittyPartyModal.setDate(date);
				kittyPartyModal.setTime(time);
				kittyPartyModal.setBudget(budget);

			}
			connection.close();
		}
		catch(NumberFormatException e){
			System.out.println("Organize KittyParty to generate Invitation");
		}

		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return kittyPartyModal;
	}


	public ArrayList<String> TotalRecipient(){

		ArrayList<String> totalRecipient=new ArrayList<>();
		HttpSession session=  ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String id=(String) obj;

		try{

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("select * FROM addfriend where email in (select email from addfriend where adminId='"+id+"')");
			ResultSet result=pstatement.executeQuery();  
			while(result.next())
			{ 
				totalRecipient.add(result.getString(3));

			}
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return totalRecipient;

	}

	public ArrayList<String> send() {

		ArrayList<String> totalRecipient=new ArrayList<>();
		HttpSession session=  ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String id=(String) obj;

		try{

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("select * FROM addfriend where email in (select email from addfriend where adminId='"+id+"')");
			ResultSet result=pstatement.executeQuery();  
			while(result.next())
			{ 
				totalRecipient.add(result.getString(3));

			}
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return totalRecipient;

	}

	public KittyPartyModal InvitationInfo(String admin, KittyPartyModal kittyPartyModal) {

		try{
			

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("select kittyparty,place,date,time,budget from kittyparty where accountid='"+admin+"'");
			ResultSet result=pstatement.executeQuery();  
			if(result.next()){
				
				String kittyParty=result.getString(1);
				String place=result.getString(2);
				String date=result.getString(3);
				String time=result.getString(4);
				String budget=result.getString(5);


				kittyPartyModal.setKittyParty(kittyParty);
				kittyPartyModal.setPlace(place);
				kittyPartyModal.setDate(date);
				kittyPartyModal.setTime(time);
				kittyPartyModal.setBudget(budget);
					
			}
	
		connection.close();
	}
	catch (ClassNotFoundException e) 
	{
		e.printStackTrace();
	}
	catch (SQLException e) 
	{
		e.printStackTrace();
	}

	return kittyPartyModal ;

}
	

	public boolean profile(KittyModal kittyModal) {
		
		boolean status=false;

		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="select * from addfriend where email='"+kittyModal.getName()+"' and mobile="+kittyModal.getPassword()+"";
			result=statement.executeQuery(query);
			status=result.next();
			
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return status;
}
	
	public boolean profileInfo(KittyModal kittyModal) {
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String sessionName=(String)obj;
		
		
		boolean status=false;

		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="select * from addfriend where email='"+sessionName+"'";
			result=statement.executeQuery(query);
			status=result.next();
			if(status){
			
			String name=result.getString(1);
			String mobile=result.getString(2);
			String address=result.getString(5);
			String hobby=result.getString(6);
			

			kittyModal.setName(name);
			kittyModal.setMobile(mobile);
			kittyModal.setAddress(address);
			kittyModal.setHobbies(hobby);
			
			}
			
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return status;
}

	public ArrayList<KittyModal> view() {
		ArrayList<KittyModal> arrayList=null;
		HttpSession session= ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String sessionName=(String)obj;
		
		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="select name,mobile,email from addfriend where adminId='"+sessionName+"'";
			result=statement.executeQuery(query);
			arrayList=new ArrayList<>();
			while(result.next()){
				
				KittyModal kittyModal=new KittyModal();
				String name=result.getString(1);
				String mobile=result.getString(2);
				String email=result.getString(3);
				
				kittyModal.setName(name);
				kittyModal.setMobile(mobile);
				kittyModal.setEmail(email);
				arrayList.add(kittyModal);
			}
			
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return arrayList;
	}

	public int delete(String email) {
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String sessionName=(String)obj;
		int rowDelete=0;

		try{
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("delete from addfriend where email='"+email+"' and adminid='"+sessionName+"'");
			rowDelete= pstatement.executeUpdate();
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return rowDelete;

	}	
		

	public ArrayList<KittyPartyModal> viewKitties() {
		
		ArrayList<KittyPartyModal> arrayList=null;
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String sessionName=(String)obj;
		
		

		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement = (Statement) connection.createStatement();
			String query="SELECT * FROM kittyparty INNER JOIN addfriend ON kittyparty.`AccountId` = addfriend.`adminId` WHERE email='"+sessionName+"' AND accountid IN(SELECT adminid FROM addfriend WHERE email='"+sessionName+"') ORDER BY DATE DESC";
			result=statement.executeQuery(query);
			arrayList=new ArrayList<>();
			while(result.next()){
			
			KittyPartyModal kittyPartyModal=new KittyPartyModal();
			String kittyParty=result.getString(1);
			String place=result.getString(2);
			String date=result.getString(3);
			String time=result.getString(4);
			String kittyStatus=result.getString(13);
			
			kittyPartyModal.setKittyParty(kittyParty);
			kittyPartyModal.setPlace(place);
			kittyPartyModal.setDate(date);
			kittyPartyModal.setTime(time);
			kittyPartyModal.setKittyStatus(kittyStatus);
			arrayList.add(kittyPartyModal);
			}
			
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return arrayList;

		
		
	}

	public  int saveChanges(KittyModal kittyModal) {
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object obj=session.getAttribute("name");
		String sessionName=(String)obj;
		
		String name=kittyModal.getName();
		String mobile=kittyModal.getMobile();
		String address=kittyModal.getAddress();
		String hobbies=kittyModal.getHobbies();
		int rowDelete=0;

		try{
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("update addfriend set name='"+name+"',mobile='"+mobile+"',address='"+address+"',hobbies='"+hobbies+"' WHERE email='"+sessionName+"'");
			rowDelete= pstatement.executeUpdate();
			
			
			connection.close();
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return rowDelete;
			
	}

	public void generatePrize(KittyModal kittyModal) {
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object ob=session.getAttribute("name");
		String sessionName=(String)ob;
		
		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement=(Statement) connection.createStatement();
			String query="select name from addfriend where adminid='"+sessionName+"' ORDER BY RAND() LIMIT 1";
			result=statement.executeQuery(query);
			if(result.next()){
				
				String name=result.getString(1);
				kittyModal.setName(name);
			}
		}
	catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
		e.printStackTrace();
	}
	catch(ClassNotFoundException e){
		e.printStackTrace();	
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return;
		
}

	public boolean appendKittyWinner(String kitter, KittyModal kittyModal) {
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		Object ob=session.getAttribute("name");
		String sessionName=(String)ob;
		boolean insertRecord=false;
		try{
			
			int result1 = 0;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("UPDATE addfriend SET kittyStatus = '"+kitter+"' WHERE adminId = '"+sessionName+"'");
			result1= pstatement.executeUpdate();
			if(result1>0){
				
				insertRecord=true;
				
			}
		}
	catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
		e.printStackTrace();
	}
	catch(ClassNotFoundException e){
		e.printStackTrace();	
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return insertRecord;
		
			
		}

	public boolean forgetPassword(KittyModal kittyModal) {
		
		try{
			Statement statement = null;
			ResultSet result = null;
			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			statement=(Statement) connection.createStatement();
			String query="Select password from signup WHERE email='"+kittyModal.getName()+"'";
			result=statement.executeQuery(query);
			if(result.next()){
				
				String password=result.getString(1);
				kittyModal.setPassword(password);
			}
		}
	catch(com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
		e.printStackTrace();
	}
	catch(ClassNotFoundException e){
		e.printStackTrace();	
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return true;
		
		
		
	}

	public ArrayList<String> totalRecipientCard(String adminName) {
		
		ArrayList<String> totalRecipient=new ArrayList<>();
		
		System.out.println(adminName);
		
		try{

			Class.forName(KittyCommon.DRIVER);
			Connection connection=DriverManager.getConnection(KittyCommon.URL+KittyCommon.DBNAME,KittyCommon.USER,KittyCommon.PASSWORD);
			PreparedStatement pstatement=connection.prepareStatement("select * FROM addfriend where adminId='"+adminName+"'");
			ResultSet result=pstatement.executeQuery();  
			while(result.next())
			{ 
				totalRecipient.add(result.getString(3));

			}
			connection.close();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return totalRecipient;
	}
		
		
	}
