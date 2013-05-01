package ClientServerRequests;

import server.AutocorrectEngines;
import server.ClientHandler;
import server.KitchenPool;
import API.Wrapper;
import Database.DBHelper;

public class AccountRequest implements Runnable {

	private ClientHandler _ch;
	private String _ID;
	private DBHelper _helper;
	private KitchenPool _activeKitchens;
	private AutocorrectEngines _info;
	private Wrapper _wrapper;
	
	public AccountRequest(ClientHandler ch, String accountID, DBHelper helper, KitchenPool kitchens, AutocorrectEngines info, Wrapper wrapper){
		_ch = ch;
		_ID = accountID;
		_helper = helper;
		_activeKitchens = kitchens;
		_info = info;
		_wrapper = wrapper;
	}
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(1);
		toReturn.setCorrect(true);
		toReturn.setAccount(_helper.getAccount(_ID));
		System.out.println("accoutn request: " + toReturn.getAccount());
		toReturn.setAPIInfo(_info);
		toReturn.setWrapper(_wrapper);
		if(toReturn.getAccount()!= null){
			_activeKitchens.addAccount(toReturn.getAccount());
			toReturn.setKitchenMap(_activeKitchens.getAllUserKitchens(_ID));
			_ch.setID(_ID);
			_ch.send(toReturn);
		}
	}

}
