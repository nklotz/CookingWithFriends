package ClientServerRequests;

import java.io.Serializable;

import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Kitchen;
import UserInfo.Recipe;

public class Request implements Serializable {

	private String _kUserID, _username, _password, _kID, _kName, _ingredient;
	private int _requestType;
	private Account _account;
	private Kitchen _kitchen;
	private Event _kEvent;
	private Recipe _recipe;
	
	
	/*
	 * 1 = verify account 
	 * 2 = get kitchen
	 * 3 = add kitchen user
	 * 4 = remove kitchen user
	 * 5 = add event
	 * 6 = remove event
	 * 7 = add recipe
	 * 8 = remove recipe
	 * 9 = store add ingredient to kitchen fridge
	 * 10 = remove ingredient from kitchen fridge
	 * 11 = store account
	 * 12 = close client
	 * 13 = make account
	 * 14 = make kitchen
	 */
	
	
	
	public Request(int request){
		_requestType = request;
	}
	
	public int getType(){
		return _requestType;
	}
	
	public String getIngredient(){
		return _ingredient;
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
	
	public void setKitchenID(String kID){
		_kID = kID;
	}
	
	public String getKitchenID(){
		return _kID;
	}
	
	public void setKitchenName(String name){
		_kName = name;
	}
	
	public String getKitchenName(){
		return _kName;
	}
	
	public void setIngredient(String ing){
		_ingredient = ing;
	}
	
	public void setKitchenUserID(String kuID){
		_kUserID = kuID;
	}
	
	public String getKitchenUserID(){
		return _kUserID;
	}
	
	public void setEvent(Event e){
		_kEvent = e;
	}
	
	public Event getEvent(){
		return _kEvent;
	}
	
	
	public void setUsername(String uID){
		_username = uID;
	}
	
	public String getUsername(){
		return _username;
	}
	
	public void setRecipe(Recipe recipe){
		_recipe = recipe;
	}
	
	public Recipe getRecipe(){
		return _recipe;
	}
	
	public void setPasword(String p){
		_password = p;
	}
	
	public String getPassword(){
		return _password;
	}
	
	
}
