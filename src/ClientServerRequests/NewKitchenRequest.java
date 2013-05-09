package ClientServerRequests;

import server.ClientHandler;
import server.KitchenPool;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Kitchen;

public class NewKitchenRequest implements Runnable {

	ClientHandler _ch;
	Request _request;
	DBHelper _helper;
	KitchenPool _kitchens;
	Account _account;
	
	public NewKitchenRequest(ClientHandler ch, Request request, DBHelper helper, KitchenPool kitchens, Account account){
		_ch = ch;
		_request = request;
		_helper = helper;
		_kitchens = kitchens;
		_account = account;
	}
	@Override
	public void run() {
		System.out.println("NEW KITCHEN");
		
		RequestReturn toReturn = new RequestReturn(2);
		
		Kitchen k = new Kitchen(_request.getKitchenName());
		k.setID(_helper.createKitchenId());
		System.out.println("NEW Kitchen's ID is" + k.getID());
		
		k.addActiveUser(_helper.getAccount(_ch.getID()));
		_kitchens.addNewKitchen(k);
		
		//_account.addKitchen(k.getKitchenName());
		//_helper.storeAccount(_account);
		
		System.out.println("acc: " + _account);
		
		toReturn.setKitchen(k);
		
		_ch.send(toReturn);
	}

}
