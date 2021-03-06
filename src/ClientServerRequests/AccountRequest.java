package ClientServerRequests;

import server.AutocorrectEngines;
import server.ClientHandler;
import server.KitchenPool;
import Database.DBHelper;

public class AccountRequest implements Runnable {

	private ClientHandler _ch;
	private String _ID;
	private DBHelper _helper;
	private KitchenPool _activeKitchens;
	private AutocorrectEngines _autocorrect;
	
	
	public AccountRequest(ClientHandler ch, String accountID, DBHelper helper, KitchenPool kitchens, AutocorrectEngines autocorrect){
		_ch = ch;
		_ID = accountID;
		_helper = helper;
		_activeKitchens = kitchens;
		_autocorrect = autocorrect;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(1);
		toReturn.setCorrect(true);
		toReturn.setAccount(_helper.getAccount(_ID));
		toReturn.setAPIInfo(_autocorrect);
		if(toReturn.getAccount()!= null){
			_activeKitchens.addAccount(toReturn.getAccount());
			toReturn.setKitchenMap(_activeKitchens.getAllUserKitchens(_ID));
			_ch.setID(_ID);
			_ch.send(toReturn);
		}
	}

}
