package CookingWithFriends;

import java.io.IOException;

import Server.KnockKnockClient;
import Server.KnockKnockServer;

public class MainServerHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			KnockKnockClient c = new KnockKnockClient(3456);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
