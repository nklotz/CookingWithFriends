package client;

import java.io.*;
import java.net.*;

import GUI.LoginWindow;
import Test.SerializableTest;

public class Client {
	
    private Socket _kkSocket = null;
    private PrintWriter _out = null;
    private BufferedReader _in = null;
    private ObjectInputStream _objIn = null;
    private LoginWindow _login = null;
	
    public Client(int port) throws IOException {


        try {
            _kkSocket = new Socket("localhost", port);
            _out = new PrintWriter(_kkSocket.getOutputStream(), true);
            _in = new BufferedReader(new InputStreamReader(_kkSocket.getInputStream()));
            _objIn = new ObjectInputStream(_kkSocket.getInputStream());
            _login = new LoginWindow(this);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }       
        
        while(_login.isActive()){
        			
        }
        close();

    }
    
    public void checkPassword(String username, String password) throws IOException{
    	String messageToServer = "Check Password " + username + " " + password;
    	_out.println(messageToServer);	
		String result = _in.readLine();
		if(result.equals("True")){
			_login.dispose();
		}
		else{
			_login.displayLoginError();
		}

    	
    }
    
    public void close(){
        try {
        	_out.println("exit");
            _out.close();
            _in.close();
			_kkSocket.close();
		} catch (IOException e) {
			System.err.println("ERROR trying to close client resources");
			System.exit(1);
		}
    }
}