package ClientServerRequests;

import server.KitchenPool;
import Database.DBHelper;
import UserInfo.Account;

public class StoreAccountRequest implements Runnable {

	Account _account;
	DBHelper _helper;
	KitchenPool _kitchens;
	Request _request;
	
	public StoreAccountRequest(Account account, DBHelper helper, KitchenPool kitchens, Request request){
		_account = account;
		_helper = helper;
		_kitchens = kitchens;
		_request = request;
	}
	
	@Override
	public void run() {
		_helper.storeAccount(_account);
		
		switch(_request.getChangeType()){
			case 1: // removed ingreint
				break;
			case 2: // add a dietary restriction
				break;
			case 3: // removed a dietary restriction
				break;
			case 4: // add a allergy 
				break;
			case 5: // removed a allergy
				break;
			default:
				break;
		}
	}

}