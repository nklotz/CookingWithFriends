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
		_helper = helper;
		_kitchens = kitchens;
		_request = request;
		_account = account;
	}
	
	@Override
	public void run() {
		_helper.storeAccount(_account);
		
		switch(_request.getChangeType()){
			case 1: // removed ingreint
				_kitchens.removeUserIngredient(_account.getID(), _request.getIngredient());
				break;
			case 2: // add a dietary restriction
				_kitchens.addUserDietRestriction(_account.getID(), _request.getRestrictAllergy());
				break;
			case 3: // removed a dietary restriction
				_kitchens.removeUserDietRestriction(_account.getID(), _request.getRestrictAllergy());
				break;
			case 4: // add a allergy
				_kitchens.addUserAllergy(_account.getID(), _request.getRestrictAllergy());
				break;
			case 5: // removed a allergy
				_kitchens.removeUserAllergy(_account.getID(), _request.getRestrictAllergy());
				break;
			default:
				break;
		}
	}

}