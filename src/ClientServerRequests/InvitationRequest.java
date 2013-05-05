package ClientServerRequests;

import server.ClientHandler;
import server.ClientPool;
import server.KitchenPool;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Invitation;

public class InvitationRequest implements Runnable {

	ClientHandler _ch;
	DBHelper _helper;
	Invitation _invite;
	ClientPool _clients;
	KitchenPool _kitchens;
	
	public InvitationRequest(ClientHandler ch, ClientPool clients, DBHelper helper, Invitation invite, KitchenPool kitchens){
		_ch = ch;
		_helper = helper;
		_invite = invite;
		_clients = clients;
		_kitchens = kitchens;
	}
	@Override
	public void run() {
		
		if(_clients.isActiveClient(_invite.getToID())){		
			System.out.println("CLIENT IS ACTIVE ");
			_clients.sendInviteToClient(_invite);
		}
		else{
			System.out.println("invitinggggg");
			Account acc = _helper.getAccount(_invite.getToID());
			acc.addInvitation(_invite);
			_helper.storeAccount(acc);
		}
		_kitchens.addRequestedUser(_invite.getKitchenID(), _invite.getToID());
		
	}

}
