package CookingWithFriends;

import java.io.IOException;

import Server.KnockKnockClient;
import Server.KnockKnockServer;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			KnockKnockServer s = new KnockKnockServer(3456);
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}

}
