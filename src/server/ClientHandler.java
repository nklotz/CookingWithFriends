package server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

import API.Wrapper;
import ClientServerRequests.AccountRequest;
import ClientServerRequests.InvitationRequest;
import ClientServerRequests.KitchenRequest;
import ClientServerRequests.NewAccountRequest;
import ClientServerRequests.NewKitchenRequest;
import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
import ClientServerRequests.StoreAccountRequest;
import ClientServerRequests.StoreKitchenRequest;
import ClientServerRequests.UpdateKitchenRequest;
import Database.DBHelper;
import Email.Sender;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * Encapsulate IO for the given client.
 * Runs an input loop and adds task to taskPool
 * based on the request. 
 * Sends RequestReturn object to handler.
 * Modified ClientHandler from lab4
 */
public class ClientHandler extends Thread {
	private ClientPool _pool;
	private Socket _client;
	private ObjectInputStream _objectIn;
	private ObjectOutputStream _objectOut;
	private ExecutorService _taskPool;
	private String _clientID;
	private DBHelper _helper;
	private KitchenPool _activeKitchens;
	private boolean _running;
	private AutocorrectEngines _autocorrect;
	
	/**
	 * Thread for the client. Handles input and launches requests.
	 */
	public ClientHandler(ClientPool pool, Socket client, ExecutorService taskPool, KitchenPool kitchens, DBHelper helper, AutocorrectEngines autocorrect) throws IOException {
		if (pool == null || client == null) {
			throw new IllegalArgumentException("Cannot accept null arguments.");
		}
		_autocorrect = autocorrect;
		_helper = helper;
		_pool = pool;
		_client = client;
		_taskPool = taskPool;
		_activeKitchens = kitchens;
		_objectIn = new ObjectInputStream(client.getInputStream());
		_objectOut = new ObjectOutputStream(client.getOutputStream()) {
			@Override
			public void close() {
				try {
					super.close();
				} catch (IOException e) {
					//TODO??
				}
			}
		};
	}
	
	/**
	 * Receive data from the client. Input is in the form 
	 * of a string. Launch request accordingly.
	 */
	public void run(){
		try {
				_running = true;
				Request request;
				int type;
				while(_running && _client.isConnected()) {
					if((request = (Request) _objectIn.readObject()) != null){
						type = request.getType();
						System.out.println("recieved request type: " + type);
						switch (type){
							case 1:  //verify account
								System.out.println("SHOULD CHECK PASSWORD");
								checkPassword(request);
								break;
							case 2:  //getKitchen
								getKitchen(request);
								break;
							//case 3 -- 10, and 17 are update kitchens (handled by default
							case 11: //store Account
								System.out.println("client handler recieved store acount request!");
								storeAccount(request);
								break;
							case 12: //close client
								kill();
								break;
							case 13: //create new account	
								System.out.println("SHOULD CREATE NEW USER");
								createNewUser(request);
								break;
							case 14: //create new Kitchen
								createNewKitchen(request);	
								break;
							case 15: //invite to kitchen
								invite(request);
								break;
							//CHANGE THE PASSWORD.
							case 18:
								System.out.println("IN CASE 18");
								changePassword(request);
								break;
							//IS VALID USER NAME.	
							case 19:
								System.out.println("CASE 19 USER IN DATABASE");
								userInDatabase(request);
								break;
							case 21:
								System.out.println("IN CLIENT HANDLER 21");
								passwordsMatch(request);
								break;
							case 22:
								System.out.println("CASE 22: updating password");
								updatePassword(request);
								break;
								
							default:
								updateKitchen(request);
								break;
						}
					}

			}
		} catch (Exception e) {
				try {
					kill();
				} catch (IOException e1) {
					//try again
					try {
						kill();
					} catch (IOException e2) {
						//Ignore
					}
				}
		} 
	}


	/**
	 * Send a RequestReturn to the client via the socket
	 */
	public synchronized void send(RequestReturn toReturn) {
		System.out.println("SENDDDDDDDD");
		if(toReturn != null){
			System.out.println("SERVER SENDING TO CLIENT: sending request of type: " + toReturn.getType());
			
			try {
				_objectOut.writeObject(toReturn);
				_objectOut.flush();
				_objectOut.reset();
			} catch (IOException e) {
//				try {
//					kill();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
			}
		}
	}

	/**
	 * Close this socket and its related streams.
	 */
	public void kill() throws IOException {
		System.out.println("killing myself (client handler of clinet " + _clientID + ")" );
		_objectIn.close();
		try{
			_objectOut.close();
		} catch(SocketException e){
			
		}
		_activeKitchens.removeUser(_clientID);
		_pool.remove(this);
		_client.close();
	}	
	
	
	public void checkPassword(Request request){
		if(_helper.checkUsernamePassword(request.getUsername(), request.getPassword())){
			if(!_pool.isActiveClient(request.getUsername())){
				System.out.println("executing task");
				_taskPool.execute(new AccountRequest(this, request.getUsername(), _helper, _activeKitchens, _autocorrect));
			}
			else{
				RequestReturn toReturn = new RequestReturn(1);
				toReturn.setCorrect(false);
				toReturn.setErrorMessage("User is currently logged in elsewhere");
				send(toReturn);
			}
		}
		else{
			RequestReturn toReturn = new RequestReturn(1);
			toReturn.setCorrect(false);
			toReturn.setErrorMessage("Incorrect username or password");
			send(toReturn);
		}
	}

	/**
	 * 
	 * @param request
	 */
	private void changePassword(Request request){
		System.out.println("CHANGING PASSWORD");
		System.out.println("CHANGE PASSWORD IN CLIENT HANDLER");
		_helper.changePasswordGivenPassword(request.getUsername(), request.getPassword());
	}
	
	/**
	 * This is when the database generates a new password, ie when the user forgets their password.
	 * @param request
	 */
	private void updatePassword(Request request){
	
		System.out.println("SERVER UPDATING PASS");
		RequestReturn req = new RequestReturn(1);
	    req.setCorrect(false);
		if(_helper.inDatabase(request.getUsername())){
			System.out.println("IS A VALID ACCOUNT");
			String pass = _helper.changePassword(request.getUsername());
			String message = "Your new password is: " + pass;
			Sender.send(request.getUsername(), message);
		    String error = "We have sent you an email containing your new password.";
		    req.setErrorMessage(error);
		}
		else{
			System.out.println("NOT A VALID ACCOUNT");
			String error = "That is not a valid account. You must create an account.";
			req.setErrorMessage(error);
		}
		send(req);
		
	}
	
	public void getKitchen(Request request){
		_taskPool.execute(new KitchenRequest(this, request.getKitchenID(), _activeKitchens));
	}
	
	public void updateKitchen(Request request){
		System.out.println("upadting kitchen!");
		_taskPool.execute(new UpdateKitchenRequest(_activeKitchens, request));
	}
	
	public void createNewUser(Request request){
		_taskPool.execute(new NewAccountRequest(this, request, _helper, _autocorrect, _activeKitchens));
	}
	
	private void createNewKitchen(Request request) {
		_taskPool.execute(new NewKitchenRequest(this, request, _helper, _activeKitchens, request.getAccount()));
	}

	private void storeAccount(Request request) {
		_taskPool.execute(new StoreAccountRequest(request.getAccount(), _helper, _activeKitchens, request));
	}
	
	private void storeKitchen(Request request) {
		_taskPool.execute(new StoreKitchenRequest(request.getKitchen(), _helper));
	}
	
	private void invite(Request request) {
		System.out.println("OOO an invite!");
		_taskPool.execute(new InvitationRequest(this, _pool, _helper, request.getInvitation(), _activeKitchens));
		
		// TODO Auto-generated method stub
		
	}
	
	public void userInDatabase(Request request){
		boolean inDB = _helper.inDatabase(request.getUsername());
		System.out.println("USER IN DATABASE CLIENT HANDLER: " + inDB);
		//If it's a unique user ie if it's not already in the data base.
		RequestReturn req = new RequestReturn(4);
		req.setUserInDatabase(inDB);
		send(req);
	}
	
	public void passwordsMatch(Request request){
		System.out.println("PASSING PASS MATCH IN SERVER");
		boolean passwordsMatch = _helper.passwordsMatch(request.getUsername(), request.getPassToCheck());
		RequestReturn req = new RequestReturn(5);
		req.setPasswordMatch(passwordsMatch);
		send(req);
	}
	
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject(o);
        oos.close();
        return new String( Base64.encode( baos.toByteArray() ) );
    }
	
	public String getID(){
		return _clientID;
	}
	
	public void setID(String id){
		_clientID = id;
		_pool.addID(id, this);
	}
	
}