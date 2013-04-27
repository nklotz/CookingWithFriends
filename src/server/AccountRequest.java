package server;

import Database.DBHelper;

public class AccountRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	DBHelper _helper;
	KitchenPool _activeKitchens;
	
	public AccountRequest(ClientHandler ch, String accountID, DBHelper helper, KitchenPool kitchens){
		_ch = ch;
		_ID = accountID;
		_helper = helper;
		_activeKitchens = kitchens;
	}
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(1);
		toReturn.setCorrect(true);
		toReturn.setAccount(_helper.getAccount(_ID));
		if(toReturn.getAccount()!= null){
			_activeKitchens.addAccount(toReturn.getAccount());
			toReturn.setKitchenMap(_activeKitchens.getAllUserKitchens(_ID));
			_ch.send(toReturn);
		}
	}

}
