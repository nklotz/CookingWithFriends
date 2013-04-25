package server;

import Database.DBHelper;

public class AccountRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	DBHelper _helper;
	
	public AccountRequest(ClientHandler ch, String accountID, DBHelper helper){
		_ch = ch;
		_ID = accountID;
		_helper = helper;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setAccount(_helper.getAccount(_ID));
		_ch.send(toReturn);
	}

}
