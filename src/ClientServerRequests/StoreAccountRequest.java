package ClientServerRequests;

import Database.DBHelper;
import UserInfo.Account;

public class StoreAccountRequest implements Runnable {

	Account _account;
	DBHelper _helper;
	
	public StoreAccountRequest(Account account, DBHelper helper){
		_account = account;
		_helper = helper;
	}
	
	@Override
	public void run() {
		_helper.storeAccount(_account);
	}

}