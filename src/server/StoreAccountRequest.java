package server;

import Database.DBHelper;
import UserInfo.Account;

public class StoreAccountRequest implements Runnable {

	ClientHandler _ch;
	Account _account;
	DBHelper _helper;
	
	public StoreAccountRequest(ClientHandler ch, Account acount, DBHelper helper){
		_ch = ch;
		_account = account;
		_helper = helper;
	}
	
	@Override
	public void run() {
		_helper.storeAccount(_account);
	}

}