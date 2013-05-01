package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import server.AutocorrectEngines;
import API.Wrapper;
import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
import GUI.GUIFrame;
import GUI.LoginWindow;
import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;

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
    //private HomeWindow _gui = null;
    private GUIFrame _gui = null;
    private HashMap<KitchenName, Kitchen> _kitchens;
    private boolean _running;
    private AutocorrectEngines _autocorrect;
    private Wrapper _wrapper;
    private String _currentKitchen;
	
    public Client(String hostname, int port) throws IOException {

        try {
            _kkSocket = new Socket(hostname, port);
            //_out = new PrintWriter(_kkSocket.getOutputStream(), true);
            _out = new ObjectOutputStream(_kkSocket.getOutputStream());
            //_in = new BufferedReader(new InputStreamReader(_kkSocket.getInputStream()));
            _in = new ObjectInputStream(_kkSocket.getInputStream());
            _login = new LoginWindow(this);
            _running = true;
            this.run();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
            System.exit(1);
        }       

    }
    
    /**
     * Sends object to server.
     * @param o : object to send to server.
     * @throws IOException
     */
    public void send(Object o){
    	try{
    		_out.writeObject(o);
        	_out.flush();
        	_out.reset();
    	} catch(IOException e){
    		System.out.println("ERROR: Could not send request from client to server. " + e.getMessage());
    	}
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
				//TODO: Deal with catching if someone tries to open the same account from somewhere else.
				response = (RequestReturn) _in.readObject();
				System.out.println("received response");
				if (response != null){
					int type = response.getType();
					System.out.println("got response");
					assert(type == 1);
					if (response.getCorrect()){
						if (_login.isNewAccount()){
							System.out.println("read as new account");
							_login.dispose();
							_login = new LoginWindow(this);
						} else {
							verified = true;	//successful login
							
							System.out.println("read as login");
							_autocorrect = response.getAPIInfo();
							_wrapper = response.getWrapper();
							_login.dispose();
							_kitchens = response.getKitchenMap();
							_gui = new GUIFrame(this, response.getAccount(), _kitchens, _autocorrect);
							//_gui = new GUIFrame(this, response.getAccount(), _autocorrect);
						}
					} else {
						_login.displayIncorrect();
					}
					
				} else {
					System.out.println("is server disconnected??");
					//TODO: Server disconnected. What do we do?
				}
			}
			while(_running){
				try{
					response = (RequestReturn) _in.readObject();
					if (response != null){
						int type = response.getType();
						if(type == 2){
							Kitchen k = response.getKitchen();
							_kitchens.put(k.getKitchenName(), k);
						}
					}
				}
				catch(SocketException se){
					//Socket closed, probably due to close being called!
					//If on error, server will handle clean up
				}
			}
			
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	
    }
    
    /*
     * Below are methods to create requests to send to the server.
     * The number scheme below represents requests numberings.
	 * 1 = verify account 
	 * 2 = get kitchen
	 * 3 = add kitchen user
	 * 4 = remove kitchen user
	 * 5 = add event
	 * 6 = remove event
	 * 7 = add recipe
	 * 8 = remove recipe
	 * 9 = store add ingredient to kitchen fridge
	 * 10 = remove ingredient from kitchen fridge
	 * 11 = store account
	 * 12 = close client
	 * 13 = make account
	 * 14 = make kitchen
	 */
    
    public void getKitchen(String id){
    	Request r = new Request(2);
    	r.setKitchenID(id);
    	send(r);
    }
    
    public void addKitchenUser(String id, String user){
    	Request r = new Request(3);
    	r.setKitchenID(id);
    	r.setKitchenUserID(user);
    	send(r);
    }
    
    public void removeKitchenUser(String id, String user){
    	Request r = new Request(4);
    	r.setKitchenID(id);
    	r.setKitchenUserID(user);
    	send(r);
    }
    
    public void addEvent(String id, Event event){
    	Request r = new Request(5);
    	r.setKitchenID(id);
    	r.setEvent(event);
    	send(r);
    }
    
    public void removeEvent(String id, Event event){
    	Request r = new Request(6);
    	r.setKitchenID(id);
    	r.setEvent(event);
    	send(r);
    }
    
    public void addRecipe(String id, Recipe rec){
    	Request r = new Request(7);
    	r.setKitchenID(id);
    	r.setRecipe(rec);
    	send(r);
    }
    
    public void removeRecipe(String id, Recipe rec){
    	Request r = new Request(8);
    	r.setKitchenID(id);
    	r.setRecipe(rec);
    	send(r);
    }
    
    public void addIngredient(String id, String ing){
    	Request r = new Request(9);
    	r.setKitchenID(id);
    	r.setIngredient(ing);
    	send(r);
    }
    
    public void removeIngredient(String id, String ing){
    	Request r = new Request(10);
    	r.setKitchenID(id);
    	r.setIngredient(ing);
    	send(r);
    }
    
    public void storeAccount(String id, Account account){
    	Request r = new Request(11);
    	r.setKitchenID(id);
    	r.setAccount(account);
    	send(r);
    }
    
    public void closeAccount(){
    	Request r = new Request(12);
    	send(r);
    }
    
    public void makeKitchen(String id){
    	Request r = new Request(14);
    	r.setKitchenID(id);
    	send(r);
    }
    
    public void setCurrentKitchen(String kitchen){
    	_currentKitchen = kitchen;
    }
    
    public String getCurrentKitchen(){
    	return _currentKitchen;
    	
    }
    
    /**
     * Method to shut down streams and socket.
     */
    public void close(){
    	System.out.println("CLOSING CLINET");
    	_running = false;
        try {
        	closeAccount();
        	_out.close();
            _in.close();
			_kkSocket.close();
		} catch (IOException e) {
			//System.err.println("ERROR trying to close client resources");
			System.exit(1);
		}
    }
}

