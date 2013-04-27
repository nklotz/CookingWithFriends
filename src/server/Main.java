package server;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	
	private final static int DEFAULT_PORT = 9882;

	public static void main(String[] args) throws URISyntaxException {
		try{
			System.out.println("server main");
			Server s = new Server(DEFAULT_PORT);
			s.run();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
