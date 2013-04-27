package ClientServerRequests;

import Database.DBHelper;
import UserInfo.Kitchen;

public class StoreKitchenRequest implements Runnable {

	Kitchen _kitchen;
	DBHelper _helper;
	
	public StoreKitchenRequest(Kitchen kitchen, DBHelper helper){
		_kitchen = kitchen;
		_helper = helper;
	}
	
	@Override
	public void run() {
		_helper.storeKitchen(_kitchen);
	}

}