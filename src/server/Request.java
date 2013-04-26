package server;

import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Kitchen;

public class Request {

	String _kUserID, _username, _password;
	int _requestType;
	Account _account;
	Kitchen _kitchen;
	Event _kEvent;
	int _kUpdateType;
	
	
	/*
	 * 1 = verify account 
	 * 2 = get kitchen
	 * 3 =  add kitchen user
	 * 4 = remove kitchen user
	 * 5 = add event
	 * 6 = remove event
	 * 7 =add recipe
	 * 8 = remove recipe
	 */
	
	
	
	public Request(int request){
		_requestType = request;
	}
	
	public int getType(){
		return _requestType;
	}
	
	public void setAccount(Account account){
		_account = account;
	}
	
	public Account getAccount(){
		return _account;
	}
	
	public void setKitchen(Kitchen kitchen){
		_kitchen = kitchen;
	}
	
	public Kitchen getKitchen(){
		return _kitchen;
	}
	
	public void setKitchenUserID(String kuID){
		_kUserID = kuID;
	}
	
	public String getKitchenUserID(){
		return _kUserID;
	}
	
	public void setKitchenEvent(Event e){
		_kEvent = e;
	}
	
	public Event getKitchenEvent(){
		return _kEvent;
	}
	
	public void setKitchenUpdateType(int updateType){
		_kUpdateType = updateType;
	}
	
	public int getKitchenUpdateType(){
		return _kUpdateType;
	}
	
	public void setUsername(String uID){
		_username = uID;
	}
	
	public String getUsername(){
		return _username;
	}
	
	public void setPasword(String p){
		_password = p;
	}
	
	public String getPassword(){
		return _password;
	}
	
	
}
