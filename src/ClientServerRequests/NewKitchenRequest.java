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
		RequestReturn toReturn = new RequestReturn(2);
		
		Kitchen k = new Kitchen(_request.getKitchenName());
		k.setID(_helper.createKitchenId());
		
		k.addActiveUser(_helper.getAccount(_ch.getID()));
		_kitchens.addNewKitchen(k);		
		toReturn.setKitchen(k);
		_ch.send(toReturn);
	}

}
