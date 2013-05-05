package ClientServerRequests;

import server.ClientHandler;
import Database.DBHelper;
import UserInfo.Account;

public class NewAccountRequest implements Runnable {

	ClientHandler _ch;
	Request _request;
	DBHelper _helper;
	
	public NewAccountRequest(ClientHandler ch, Request request, DBHelper helper){
		_ch = ch;
		_request = request;
		_helper = helper;
	}
	@Override
	public void run() {	
		RequestReturn toReturn = new RequestReturn(1);
		if(_helper.inDatabase(_request.getUsername())){
			Account account = new Account(_request.getUsername());
			_helper.storeUsernamePassword(_request.getUsername(), _request.getPassword());
			_helper.storeAccount(account);
			toReturn.setCorrect(true);
		}
		else{
			toReturn.setCorrect(false);
		}
		_ch.send(toReturn);
	}

}
