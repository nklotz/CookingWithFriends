package server;

public class AccountRequest implements Runnable {

	ClientHandler _ch;
	String _ID;
	
	public AccountRequest(ClientHandler ch, String accountID){
		_ch = ch;
		_ID = accountID;
	}
	
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(2);
		//toReturn.setAccount(Request Account from data base);
		_ch.send(toReturn);
	}

}
