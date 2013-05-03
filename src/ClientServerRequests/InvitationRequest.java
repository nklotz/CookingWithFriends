package ClientServerRequests;

import server.ClientHandler;
import server.ClientPool;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Invitation;

public class InvitationRequest implements Runnable {

	ClientHandler _ch;
	DBHelper _helper;
	Invitation _invite;
	ClientPool _clients;
	
	public InvitationRequest(ClientHandler ch, ClientPool clients, DBHelper helper, Invitation invite){
		_ch = ch;
		_helper = helper;
		_invite = invite;
		_clients = clients;
	}
	@Override
	public void run() {
		RequestReturn toReturn = new RequestReturn(3);
		toReturn.setInvitation(_invite);
		
		if(_clients.isActiveClient(_invite.getToID())){
			System.out.println("CLIENT IS ACTIVE OIJEWROIJEWR IJOW. i shat myself");
		}
		else{
			System.out.println("invitinggggg");
			Account acc = _helper.getAccount(_invite.getToID());
			acc.addInvitation(_invite);
			_helper.storeAccount(acc);
		}
		
	}

}
