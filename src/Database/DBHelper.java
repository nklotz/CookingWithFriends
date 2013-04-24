/**
 * 
 */
package Database;

import java.io.IOException;

import UserInfo.Account;
import UserInfo.Kitchen;

import com.mongodb.Mongo;

/**
 * @author hacheson
 *
 */
public class DBHelper implements DBHelperInterface{

	public DBHelper(){
		String s = "mongod --port 27017 -dbpath /home/hacheson/course/cs032/MongoData/";
		String[] args = s.split(" ");
		Process p = null;
		try{
			p = Runtime.getRuntime().exec(s);
		} catch(IOException e){
			e.printStackTrace();
		}
		Mongo mongo = new Mongo("localhost", 27017);
		
	}

	@Override
	public Account getAccount(String username) {
		return null;
	}

	@Override
	public void storeAccount(Account a) {
		
	}

	@Override
	public Kitchen getKitchen(String id) {
		return null;
	}

	@Override
	public void storeKitchen(Kitchen k) {
		
	}
}
