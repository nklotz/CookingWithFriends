package server;

import Database.DBHelper;
import UserInfo.Kitchen;

public class UpdateKitchenRequest implements Runnable {

	ClientHandler _ch;
	KitchenPool _kitchens;
	DBHelper _helper;
	Request _request;
	
	public UpdateKitchenRequest(KitchenPool kitchens, Request request){
		_kitchens = kitchens;
		_request = request;
	}
	
	@Override
	public void run() {
		_kitchens.updateKitchen(_request);
	}

}