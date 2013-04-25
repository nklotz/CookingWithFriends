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

import Database.DBHelper;

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
	
	/**
	 * Thread for the client. Handles input and launches requests.
	 */
	public ClientHandler(ClientPool pool, Socket client, ExecutorService taskPool, KitchenPool kitchens, DBHelper helper) throws IOException {
		if (pool == null || client == null) {
			throw new IllegalArgumentException("Cannot accept null arguments.");
		}
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
				Request request;
				String input;
				while(_client.isConnected() && (request = (Request) _objectIn.readObject()) != null){
					input = request.getRequest();
					if(input.equals("Close me")){
						kill();
						break;
					}
					else if(input.startsWith("My ID:")){
						String[] line = input.split("\\t");
						if(line.length == 2){
							_clientID = line[1];
							_pool.addID(line[1]);
						}
					}
					
					else if(input.startsWith("Check user/password:")){
						//call to data base for userpassword
					}
					
					else if(input.startsWith("Get account:")){
						getAccount(input);
					}
				
					else if(input.startsWith("Get kitchen:")){
						getKitchen(input);
					}
					
					else if(input.startsWith("Store account:")){
						storeAccount(request);
					}
					
					else if(input.startsWith("Store kitchen:")){
						storeKitchen(request);
					}
					
					else{
						continue;			
					}
			}
		} catch (IOException | ClassNotFoundException  e) {
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
		if(toReturn != null){
			try {
				_objectOut.writeObject(toReturn);
				_objectOut.flush();
				_objectOut.reset();
			} catch (IOException e) {
				try {
					kill();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Close this socket and its related streams.
	 */
	public void kill() throws IOException {
		_objectIn.close();
		try{
			_objectOut.close();
		} catch(SocketException e){
			
		}
		_activeKitchens.removeUser(_clientID);
		_pool.remove(this);
		_client.close();
	}	
	
	
	public void checkPassword(String input){
		// 1. Query database.
		// 2. Check if password = password form database.
	}
	
	public void getAccount(String input){
		String[] line = input.split("\\t");
		if(line.length==2){
			_taskPool.execute(new AccountRequest(this, line[1], _helper, _activeKitchens));
		}
	}
	
	public void getKitchen(String input){
		String[] line = input.split("\\t");
		if(line.length==2){
			_taskPool.execute(new KitchenRequest(this, line[1], _activeKitchens));
		}
	}

	private void storeAccount(Request request) {
		_taskPool.execute(new StoreAccountRequest(this, request.getAccount(), _helper));
	}
	
	private void storeKitchen(Request request) {
		_taskPool.execute(new StoreKitchenRequest(this, request.getKitchen(), _helper));
	}
	
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return new String( Base64.encode( baos.toByteArray() ) );
    }
	
	public String getID(){
		return _clientID;
	}
	
}