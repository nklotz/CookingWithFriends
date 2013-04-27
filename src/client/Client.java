package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;


import server.Request;
import server.RequestReturn;



import GUI.HomeWindow;
import GUI.LoginWindow;
import UserInfo.Kitchen;

/**
 * Client class opens login window and then home window. Contains socket for communication with server.
 * @author esfriedm
 *
 */
public class Client extends Thread {
	
    private Socket _kkSocket = null;
    private ObjectOutputStream _out = null;
    private ObjectInputStream _in = null;
    private LoginWindow _login = null;
    private HomeWindow _gui = null;
    private HashMap<String, Kitchen> _kitchens;
    private boolean _running;
	
    public Client(int port) throws IOException {

        try {
            _kkSocket = new Socket("localhost", port);
            //_out = new PrintWriter(_kkSocket.getOutputStream(), true);
            _out = new ObjectOutputStream(_kkSocket.getOutputStream());
            //_in = new BufferedReader(new InputStreamReader(_kkSocket.getInputStream()));
            _in = new ObjectInputStream(_kkSocket.getInputStream());
            _login = new LoginWindow(this);
            _running = true;
            this.run();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }       

    }
    
    /**
     * Sends object to server.
     * @param o : object to send to server.
     * @throws IOException
     */
    public void send(Object o) throws IOException{
    	_out.writeObject(o);
    	_out.flush();
    }
    
    /**
     * Sends username and password to server to retrieve existing account or create new one.
     * @param username
     * @param password
     * @throws IOException
     */
    public void checkPassword(String username, String password) throws IOException{
    	Request userPass;
    	if (_login.isNewAccount()){
    		userPass = new Request(13);
    	} else {
    		userPass = new Request(1);
    	}
    	userPass.setUsername(username);
    	userPass.setPasword(password);
    	System.out.println("about to send " + username + " x " + password);
    	send(userPass);
    }

    /**
     * Loop to receive communication from the server.
     */
    public void run(){
    	try {
			RequestReturn response;
			String input;
			boolean verified = false;
			//Wait to receive account verification
			while (!verified){
				//Response will have 
				response = (RequestReturn) _in.readObject();
				System.out.println("received response");
				if (response != null){
					int type = response.getType();
					assert(type == 1);
					if (response.getCorrect()){
						if (_login.isNewAccount()){
							System.out.println("read as new account");
							_login.dispose();
							_login = new LoginWindow(this);
						} else {
							verified = true;	//successful login
							System.out.println("read as login");
							_login.dispose();
							_gui = new HomeWindow(response.getAccount(), this);
							_kitchens = response.getKitchenMap();
						}
					} else {
						_login.displayIncorrect();
					}
					
				} else {
					//TODO: Server disconnected. What do we do?
				}
			}
			while(_running){
				response = (RequestReturn) _in.readObject();
				if (response != null){
					int type = response.getType();
					assert(type == 2);
					Kitchen k = response.getKitchen();
					String id = k.getId();
					_kitchens.put(id, k);
				}	
			}
			
			//Now receive general messages.
			response = (RequestReturn) _in.readObject();
			while(response != null){
				int type = response.getType();
			}
			/*_out.println("Get Account " + username);
			try {
				Account account = (Account) _objIn.readObject();
				System.out.println("account: " + account.getUser().getID());
				_gui = new HomeWindow(account);
				System.out.println("GUI NEW ACCOUTN!!!!");
			} catch (ClassNotFoundException e) {
				System.err.println("ERROR: CLASS (ACCOUNT) NOT FOUND!");
			}*/
			
		
			while(_gui.isActive()){
			}
			close();	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	
    }
    
    /**
     * Method to shut down streams and socket.
     */
    public void close(){
        try {
        	//_out.println("exit");//TODO: how does request object askt to exit?
            _out.close();
            _in.close();
			_kkSocket.close();
		} catch (IOException e) {
			System.err.println("ERROR trying to close client resources");
			System.exit(1);
		}
    }
    
    private class KitchenThread extends Thread{
    	private boolean _updates; 
    	public KitchenThread(){
    		boolean _updates = true;
    	}
    	
    	public void run(){
    		while(_updates){
    			
    		}
    	}
    }
}

