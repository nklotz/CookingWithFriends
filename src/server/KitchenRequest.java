package server;

import Database.DBHelper;

public class KitchenRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	DBHelper _helper;
	
	public KitchenRequest(ClientHandler ch, String kitchenID, DBHelper helper){
		_ch = ch;
		_ID = kitchenID;
		_helper = helper;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(3);
		toReturn.setKitchen(_helper.getKitchen(_ID));
		_ch.send(toReturn);
	}

}
