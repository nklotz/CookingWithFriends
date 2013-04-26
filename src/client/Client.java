package client;

import java.io.*;
import java.net.*;

import javax.xml.bind.ParseConversionEvent;

import server.Request;
import server.RequestReturn;

import GUI.HomeWindow;
import GUI.LoginWindow;
import Test.SerializableTest;
import UserInfo.Account;

public class Client extends Thread {
	
    private Socket _kkSocket = null;
    private ObjectOutputStream _out = null;
    private ObjectInputStream _in = null;
    private ObjectInputStream _objIn = null;
    private LoginWindow _login = null;
    private HomeWindow _gui = null;
	
    public Client(int port) throws IOException {

        try {
            _kkSocket = new Socket("localhost", port);
            //_out = new PrintWriter(_kkSocket.getOutputStream(), true);
            _out = new ObjectOutputStream(_kkSocket.getOutputStream());
            //_in = new BufferedReader(new InputStreamReader(_kkSocket.getInputStream()));
            _in = new ObjectInputStream(_kkSocket.getInputStream());
            _login = new LoginWindow(this);
            this.run();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }       

    }
    
    public void checkPassword(String username, String password) throws IOException{
    	Request userPass = new Request(1);
    	
    	_out.writeObject(userPass);
    }
    
    public void run(){
    	try {
			RequestReturn response;
			String input;
			boolean verified = false;
			//Wait to receive account verification
			while (!verified){
				response = (RequestReturn) _in.readObject();
				if (response != null){
					int type = response.getType();
					assert(type == 1);
					if (response.getCorrect()){
						verified = true;
						_gui = new HomeWindow(response.getAccount());
					} else {
						_login.displayIncorrect();
					}
				} else {
					//TODO: Server disconnected. What do we do?
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
}