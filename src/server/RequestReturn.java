package server;

import java.io.Serializable;

import UserInfo.Account;
import UserInfo.Kitchen;

public class RequestReturn implements Serializable {
	
	private static final long serialVersionUID = 1L;

	int _type;
	Account _account;
	Kitchen _kitchen;
	boolean _checkPass;
	
	/*
	 * Types:
	 *   1 -- Password Check (extract getCorrect())
	 *   2 -- Account Request (extract getAccount)
	 *   3 -- Kitchen Request (extract getKitchen)
	 * 
	 */
	
	public RequestReturn(int type){
		_type = type;
	}
	
	public int getType(){
		return _type;
	}
	
	public void setCorrect(boolean correct){
		_checkPass = correct;
	}
	
	public boolean getCorrect(){
		return _checkPass;
	}
	
	public void setAccount(Account acc){
		_account = acc;
	}
	
	public Account getAccount(){
		return _account;
	}
	
	public void setKitchen(Kitchen kit){
		_kitchen = kit;
	}
	
	public Kitchen getKitchen(){
		return _kitchen;
	}
}
