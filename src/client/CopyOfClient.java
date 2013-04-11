package client;

import java.io.*;
import java.net.*;

import GUI.LoginWindow;
import Test.SerializableTest;

public class CopyOfClient {
	
    public CopyOfClient(int port) throws IOException {
    
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            kkSocket = new Socket("localhost", port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            //LoginWindow login = new LoginWindow(this);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer, fromUser;

        //System.out.println("Try: " + kkSocket.
        //Recipe r = Converter.toRecipe(output.readLine());
        //
        
        ObjectInputStream ois = new ObjectInputStream(kkSocket.getInputStream());
        try {
        	SerializableTest test;

        while ((test = (SerializableTest) ois.readObject()) != null) {
            //System.out.println("Server: " + fromServer);

			System.out.println("test");
			System.out.println(test.getA());
			for(String b: test.getB()){
				System.out.println(b);
			}
            //if (fromServer.equals("Bye."))
              //  break;
		    
            fromUser = stdIn.readLine();
		    if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
		    }
        }
        
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
    
    public void checkPassword(String username, String password){
    	
    }
}