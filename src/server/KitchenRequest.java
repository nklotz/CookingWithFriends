package server;

import Database.DBHelper;

public class KitchenRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	KitchenPool _activeKitchens;
	
	public KitchenRequest(ClientHandler ch, String kitchenID, KitchenPool kitchens){
		_ch = ch;
		_ID = kitchenID;
		_activeKitchens = kitchens;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(3);
		toReturn.setKitchen(_activeKitchens.getKitchen(_ID));
		if(toReturn.getKitchen()!=null){
			_ch.send(toReturn);
		}
	}

}
