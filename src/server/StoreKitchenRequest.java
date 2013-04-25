package server;

import Database.DBHelper;
import UserInfo.Kitchen;

public class StoreKitchenRequest implements Runnable {

	ClientHandler _ch;
	Kitchen _kitchen;
	DBHelper _helper;
	
	public StoreKitchenRequest(ClientHandler ch, Kitchen kitchen, DBHelper helper){
		_ch = ch;
		_kitchen = kitchen;
		_helper = helper;
	}
	
	@Override
	public void run() {
		_helper.storeKitchen(_kitchen);
	}

}