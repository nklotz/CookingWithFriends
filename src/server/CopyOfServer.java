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

public class CopyOfServer {
	private Database db_;
	public CopyOfServer(int port) throws IOException {
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
	
	        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	        OutputStream outStream = clientSocket.getOutputStream();
	        
	        ObjectOutputStream oos = new ObjectOutputStream(outStream);
	        
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                clientSocket.getInputStream()));
	        String inputLine, outputLine;
	        //out.println("Hello! Please enter username and password separated by a comma.");
	
            SerializableTest test1 = new SerializableTest();
	        test1.setA("FIRST");
	        ArrayList<String> list1 = new ArrayList();
	        list1.add("hi");
	        list1.add("crap");
	        test1.setB(list1);
        	oos.writeObject(test1);

	        
	        while ((inputLine = in.readLine()) != null) {
		        
	        	if (inputLine.equals("Exit")){
	        		clientSocket.close();
	        		 break;
	        	}
	        	if (inputLine.equals("Close server")){
	        		clientSocket.close();
	                out.close();
	                in.close();
	                serverSocket.close();
	        		break;
	        	}
	        	
	        	System.out.println("called");
		        SerializableTest test = new SerializableTest();
		        test.setA(inputLine);
		        ArrayList<String> list = new ArrayList();
		        list.add("hi");
		        list.add("crap");
		        test.setB(list);
	        	oos.writeObject(test);
	        	
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
}
