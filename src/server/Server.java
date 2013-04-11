package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Test.SerializableTest;
import UserInfo.Account;

public class Server {
	private Database db_;
	private PrintWriter _out;
	private ObjectOutputStream  _oos;
	
	public Server(int port) throws IOException {
		System.out.println("creating server");
		db_ = new Database();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }

        while(true){
	        Socket clientSocket = null;
	        try {
	            clientSocket = serverSocket.accept();

	        } catch (IOException e) {
	            System.err.println("Accept failed.");
	            System.exit(1);
	        }
	
	        _out = new PrintWriter(clientSocket.getOutputStream(), true);
	        OutputStream outStream = clientSocket.getOutputStream();
	        
	        _oos = new ObjectOutputStream(outStream);
	        
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                clientSocket.getInputStream()));
	        String inputLine, outputLine;
	        
	        while ((inputLine = in.readLine()) != null) {
		        
	        	if (inputLine.equals("Exit")){
	        		clientSocket.close();
	        		 break;
	        	}
	        	else if (inputLine.equals("Close server")){
	        		clientSocket.close();
	                _out.close();
	                in.close();
	                serverSocket.close();
	        		break;
	        	}
	        	
	        	else{
	        		parseInput(inputLine);
	        	}
	        	
        	//System.out.println("called");
	        //SerializableTest test = new SerializableTest();
	        //test.setA(inputLine);
	        //ArrayList<String> list = new ArrayList();
	        //list.add("hi");
	       //list.add("crap");
	        //test.setB(list);
        	
        	//oos.writeObject(test);
	        	
	        	//outputLine = kkp.processInput(inputLine);
//	        	String[] arr = inputLine.split(",");
//	        	if(!checkPassword(arr[0].trim(), arr[1].trim())){
//	        		out.println("Invalid user password");
//	        	}
//	        	else{
//	        		out.println("Welcome " + arr[0].trim());
//	        	}
	        	
	        }
        }
    }
	
	public boolean checkPassword(String username, String password){
		// 1. Query database.
		// 2. Check if password = password form database.
		System.out.println("checking user: " + username + " password: " + password);
		return db_.getPasswordFromUser(username).equals(password);
	}
	
	public Account getAccount(String username){
		return db_.getAccountFromUser(username);
	}
	
	public void parseInput(String input){
		if(input.startsWith("Check Password")){
			System.out.println("check pass");
			String[] inputArr = input.split(" ");
			if(checkPassword(inputArr[2], inputArr[3])){
				_out.println("True");
			}
			else{
				_out.println("False");
			}
		}
		else if(input.startsWith("Get Account")){
			System.out.println("GETTING ACCOUNT");
			String[] inputArr = input.split(" ");
			Account account = getAccount(inputArr[2]);
			try {
				_oos.writeObject(account);
			} catch (IOException e) {
				System.err.println("ERROR: couldn't write account to client");
			}
		}
	}
}
