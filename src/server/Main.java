package server;

import java.io.IOException;

public class Main {
	
	private final static int DEFAULT_PORT = 9882;

	public static void main(String[] args) {
		try{
			System.out.println("server main");
			Server s = new Server(DEFAULT_PORT);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
