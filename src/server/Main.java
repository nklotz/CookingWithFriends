package server;

import java.io.IOException;

public class Main {
	
	private final static int DEFAULT_PORT = 9876;

	public static void main(String[] args) {
		try{
			Server s = new Server(DEFAULT_PORT);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
