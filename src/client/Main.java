package client;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Main {

	public static void main(String[] args) {
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
			e.printStackTrace();
		}
	}
	
	private static void usage() {
		System.out.println("Usage: client <hostname> <port>");
		System.exit(1);
	}
	
}


