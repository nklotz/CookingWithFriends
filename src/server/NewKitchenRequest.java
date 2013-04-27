package server;

import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Kitchen;

public class NewKitchenRequest implements Runnable {

	ClientHandler _ch;
	Request _request;
	DBHelper _helper;
	KitchenPool _kitchens;
	
	public NewKitchenRequest(ClientHandler ch, Request request, DBHelper helper, KitchenPool kitchens){
		_ch = ch;
		_request = request;
		_helper = helper;
		_kitchens = kitchens;
	}
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(2);
		
		Kitchen k = new Kitchen(_request.getKitchenName());
		k.setId(_helper.createKitchenId());
		k.addUser(_ch.getID());
		
		toReturn.setKitchen(k);
		
		_kitchens.addKitchen(k);
		
		_ch.send(toReturn);
	}

}
