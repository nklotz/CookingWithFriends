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
import GUI2.GUI2Frame;
import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Ingredient;
import UserInfo.Invitation;
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
    private GUI2Frame _gui = null;
    private HashMap<KitchenName, Kitchen> _kitchens;
    private HashMap<String,KitchenName> _kitchenIdToName;
    private boolean _running;
    private AutocorrectEngines _autocorrect;
    private KitchenName _currentKitchen;
    private String _id;
	
    public Client(String hostname, int port) throws IOException {
    	System.out.println("IN CLIENT CONSTRUCTOR");
        try {
            _kkSocket = new Socket(hostname, port);
            _out = new ObjectOutputStream(_kkSocket.getOutputStream());
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
    		System.out.println("SENDING: " + o);
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
				System.out.println("received  password response");
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
							_login.dispose();
							_kitchens = response.getKitchenMap();
							_kitchenIdToName = kitchenIdMap(_kitchens);
							_id = response.getAccount().getID();
							System.out.println("SHOULD CREATE NEW GUI");
							_gui = new GUI2Frame(this, response.getAccount(), _kitchens, _autocorrect);

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
							System.out.println("got new kitchen: " + k.getName());
							_kitchens.put(k.getKitchenName(), k);
							_kitchenIdToName.put(k.getKitchenName().getID(), k.getKitchenName());
							_gui.updateKitchenDropDown();
							if(_currentKitchen != null){
								System.out.println("curr k: " + _currentKitchen);
								System.out.println("is that the same as: " + k);
								if(_currentKitchen.equals(k.getKitchenName())){
									System.out.println("new kitchen is gui's current!!!");
									_gui.updateKitchen();
								}
							}
						}
					}
				}
				catch(SocketException se){
					//Socket closed, probably due to close being called!
					//If on error, server will handle clean up
				}
			}
			
    	} catch (Exception e) {
    		//TODO: Write message to login screen and home screen if server goes down.
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
    
    public void addActiveKitchenUser(String id, Account account){
    	Request r = new Request(3);
    	r.setKitchenID(id);
    	r.setKitchenUserID(_id);
    	r.setAccount(account);
    	send(r);
    }
    
    public void removeActiveKitchenUser(String id){
    	Request r = new Request(4);
    	r.setKitchenID(id);
    	r.setKitchenUserID(_id);
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
    
    public void addIngredient(String id, Ingredient ing){
    	Request r = new Request(9);
    	r.setKitchenID(id);
    	r.setIngredient(ing);
    	r.setUsername(_id);
    	send(r);
    }
    
    public void removeIngredient(String id, Ingredient ing){
    	Request r = new Request(10);
    	r.setKitchenID(id);
    	r.setIngredient(ing);
    	r.setUsername(_id);
    	send(r);
    }
    
    public void addRequestedKitchenUser(String toId, String fromName, KitchenName kitchenName){
    	Request r = new Request(15);
    	r.setKitchenID(kitchenName.getID());
    	Invitation invite = new Invitation(_id, fromName, toId, kitchenName); 	
    	r.setInvitation(invite);
    	send(r);
    }
    
    public void removeRequestedKitchenUser(String id){
    	System.out.println("WHAT AM I DOING???????!?!?!?!?!");
    	Request r = new Request(16);
    	r.setKitchenID(id);
    	r.setKitchenUserID(_id);
    	send(r);
    }
    
    
    /**
     * Types:
     * 2--Added dietary restriction
     * 3--removed dietary restriction
     * 4--add allergy
     * 5--removed allergy
     */
    public void storeAccount(Account account, int type, String restricAl){
    	System.out.println("making account store of type: " + type);
    	System.out.println("change item: " + restricAl);
    	Request r = new Request(11);
    	r.setAccount(account);
    	r.setChangeType(type);
    	r.setRestrictAllergy(restricAl);
    	send(r);
    }
    
    public void storeAccount(Account account, Ingredient ing){
    	Request r = new Request(11);
    	r.setAccount(account);
    	r.setChangeType(1);
    	r.setIngredient(ing);
    	send(r);
    }
    
    public void storeAccount(Account account){
    	Request r = new Request(11);
    	r.setAccount(account);
    	r.setChangeType(0);
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
    
    public void setCurrentKitchen(KitchenName kitchen){
    	_currentKitchen = kitchen;
    }
    
    public KitchenName getCurrentKitchen(){
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
    
    public HashMap<KitchenName, Kitchen> getKitchens(){
    	return _kitchens;
    }
    
    public HashMap<String,KitchenName> getKitchenIdMap(){
    	return _kitchenIdToName;
    }
    
    private HashMap<String,KitchenName> kitchenIdMap(HashMap<KitchenName, Kitchen> kitchenMap){
    	HashMap<String,KitchenName> map = new HashMap<String,KitchenName>();
    	for (KitchenName item : kitchenMap.keySet()){
    		map.put(item.getID(), item);
    	}
    	return map; 
    }
}

