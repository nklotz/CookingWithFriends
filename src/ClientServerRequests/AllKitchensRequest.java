package ClientServerRequests;

import server.ClientHandler;
import server.KitchenPool;
import Database.DBHelper;

public class AllKitchensRequest implements Runnable {

	ClientHandler _ch;
	KitchenPool _activeKitchens;
	
	public AllKitchensRequest(ClientHandler ch, KitchenPool kitchens){
		_ch = ch;
		_activeKitchens = kitchens;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(4);
		toReturn.setKitchenMap(_activeKitchens.getAllUserKitchens(_ch.getID()));
		if(toReturn.getKitchenMap()!=null){
			_ch.send(toReturn);
		}
	}

}
