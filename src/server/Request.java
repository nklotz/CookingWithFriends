package server;

import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Kitchen;

public class Request {

	String _request, _kUserID;
	Account _account;
	Kitchen _kitchen;
	Event _kEvent;
	int _kUpdateType;
	
	public Request(String request){
		_request = request;
	}
	
	public String getRequest(){
		return _request;
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
	
}
