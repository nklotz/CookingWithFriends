package client;

import java.io.IOException;

import Email.Sender;

//import sun.rmi.transport.Transport;

public class Main {

	public static void main(String[] args) {
		//Sender.send("hannah_acheson-field@brown.edu", "Email message");
		if (args.length != 2)
			usage();
		
		int port = 0;
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException ex) {
			usage();
		}
		try{
			new Client(args[0], port);
		} catch(IOException e){
			//e.printStackTrace();
		}
	}
	
	private static void usage() {
		System.exit(1);
	}
	
	
		


}


