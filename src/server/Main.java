package server;

import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		if (args.length != 1)
			usage();
		
		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException ex) {
			usage();
		}
		
		try{
			Server s = new Server(port);
			s.run();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private static void usage() {
		System.out.println("Usage: server <port>");
	}

}
