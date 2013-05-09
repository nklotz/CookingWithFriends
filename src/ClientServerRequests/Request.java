package ClientServerRequests;

import java.io.Serializable;

import UserInfo.Account;
import UserInfo.KitchenEvent;
import UserInfo.Ingredient;
import UserInfo.Invitation;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;

public class Request implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _kUserID, _username, _password, _kID, _kName, _eventName;
	private int _requestType, _accountChangeType;
	private Account _account;
	private Kitchen _kitchen;
	private KitchenEvent _kEvent;
	private Recipe _recipe;
	private Invitation _invite;
	private Ingredient _ingredient;
	private String _restrictAllergy;
	private KitchenName _kitchenNameObj;
	private String _newMessages;
	private String _passToCheck;
	
	
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
	 * 15 = invite User ToKitchen
	 * 16 = declineInvitation
	 * 17 = add Ingredient to kitchen Event
	 * 18 = change user password
	 * 19 = user in database
	 * 20 = post message
	 * 21 = check password
	 */
	
	
	
	public Request(int request){
		_requestType = request;
	}
	
	public int getType(){
		return _requestType;
	}
	
	public Ingredient getIngredient(){
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
	
	public void setKitchenNameObj(KitchenName kitchenName){
		_kitchenNameObj = kitchenName;
	}
	
	public KitchenName getKitchenNameObj(){
		return _kitchenNameObj;
	}
	
	public void setIngredient(Ingredient ing){
		_ingredient = ing;
	}
	
	public void setKitchenUserID(String kuID){
		_kUserID = kuID;
	}
	
	public String getKitchenUserID(){
		return _kUserID;
	}
	
	public void setEvent(KitchenEvent e){
		_kEvent = e;
	}
	
	public KitchenEvent getEvent(){
		return _kEvent;
	}
	
	
	public void setUsername(String uID){
		_username = uID;
	}
	
	public String getUsername(){
		return _username;
	}
	
	public void setEventName(String eventName){
		_eventName = eventName;
	}
	
	public String getEventName(){
		return _eventName;
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
	
	public void setInvitation(Invitation invite){
		_invite = invite;
	}
	
	public Invitation getInvitation(){
		return _invite;
	}
	
	public void setChangeType(int type){
		_accountChangeType = type;
	}
	
	public int getChangeType(){
		return _accountChangeType;
	}
	
	public void setRestrictAllergy(String ra){
		_restrictAllergy = ra;
	}
	
	public String getRestrictAllergy(){
		return _restrictAllergy;
	}
	
	public String getNewMessages(){
		return _newMessages;
	}
	
	public void setMessages(String messages){
		_newMessages = messages;
	}
	
	public void setPassToCheck(String passToCheck){
		_passToCheck = passToCheck;
	}
	
	public String getPassToCheck(){
		return _passToCheck;
	}
	
}
