package server;

import UserInfo.Account;
import UserInfo.Kitchen;

public class Request {

	String _request;
	Account _account;
	Kitchen _kitchen;
	
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
	
}
