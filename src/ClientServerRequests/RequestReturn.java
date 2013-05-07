package ClientServerRequests;

import java.io.Serializable;
import java.util.HashMap;

import server.AutocorrectEngines;
import API.Wrapper;
import UserInfo.Account;
import UserInfo.Invitation;
import UserInfo.Kitchen;
import UserInfo.KitchenName;

public class RequestReturn implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int _type;
	private Account _account;
	private Kitchen _kitchen;
	private boolean _checkPass;
	private HashMap<KitchenName, Kitchen> _kitchenMap;
	private Invitation _invite;
	private AutocorrectEngines _info;
	private Wrapper _wrapper;
	private boolean _userInDatabase;		//Whether or not the user already exists in the database.
	private boolean _passwordsMatch;
	
	/*
	 * Types:
	 *   1 -- Password Check (extract getCorrect())
	 *   2 -- Singular Kitchen (extract getKitchen)
	 *   3 -- Invitation
	 *   4 -- Valid user.
	 */
	
	public RequestReturn(int type){
		_type = type;
	}
	
	public int getType(){
		return _type;
	}
	
	public boolean getUserInDatabase(){
		return _userInDatabase;
	}
	
	public void setUserInDatabase(boolean valid){
		_userInDatabase = valid;
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
	
	public void setKitchenMap(HashMap<KitchenName, Kitchen> hashMap){
		_kitchenMap = hashMap;
	}
	
	public HashMap<KitchenName, Kitchen> getKitchenMap(){
		return _kitchenMap;
	}
	
	public void setInvitation(Invitation invite){
		_invite = invite;
	}
	
	public Invitation getInvitation(){
		return _invite;
	}
	
	public AutocorrectEngines getAPIInfo(){
		return _info;
	}
	
	public void setAPIInfo(AutocorrectEngines info){
		_info = info;
	}

	public Wrapper getWrapper(){
		return _wrapper;
	}
	
	public boolean getPasswordsMatch(){
		return _passwordsMatch;
	}
	
	public void setPasswordMatch(boolean match){
		_passwordsMatch = match;
	}
}
