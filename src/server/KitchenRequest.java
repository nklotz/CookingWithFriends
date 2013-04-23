package server;

public class KitchenRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	
	public KitchenRequest(ClientHandler ch, String kitchenID){
		_ch = ch;
		_ID = kitchenID;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(3);
		//toReturn.setAccount(Request Kitchen from data base);
		_ch.send(toReturn);
	}

}
