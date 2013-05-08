package ClientServerRequests;

import server.AutocorrectEngines;
import server.ClientHandler;
import server.KitchenPool;
import Database.DBHelper;
import UserInfo.Account;

public class NewAccountRequest implements Runnable {

	ClientHandler _ch;
	Request _request;
	DBHelper _helper;
	AutocorrectEngines _autocorrect;
	KitchenPool _activeKitchens;
	
	
	public NewAccountRequest(ClientHandler ch, Request request, DBHelper helper, AutocorrectEngines autocorrect, KitchenPool kitchens){
		_ch = ch;
		_request = request;
		_helper = helper;
		_autocorrect = autocorrect;
		_activeKitchens = kitchens;
	}
	@Override
	public void run() {	
		RequestReturn toReturn = new RequestReturn(1);
		if(!_helper.inDatabase(_request.getUsername())){
			Account account = new Account(_request.getUsername());
			_helper.storeUsernamePassword(_request.getUsername(), _request.getPassword());
			_helper.storeAccount(account);
			toReturn.setCorrect(true);
			toReturn.setAccount(_helper.getAccount(_request.getUsername()));
			System.out.println("accoutn request: " + toReturn.getAccount());
			toReturn.setAPIInfo(_autocorrect);
			//toReturn.setPass(_helper.getPassword(toReturn.getAccount().getID()));
			if(toReturn.getAccount()!= null){
				_activeKitchens.addAccount(toReturn.getAccount());
				toReturn.setKitchenMap(_activeKitchens.getAllUserKitchens(_request.getUsername()));
				_ch.setID(_request.getUsername());
				_ch.send(toReturn);
			}
		}
		else{
			toReturn.setCorrect(false);
			toReturn.setErrorMessage("User Name unavailable; try another!");
		}
		_ch.send(toReturn);
	}

}
