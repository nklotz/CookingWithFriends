/**
 * 
 */
package Database;

import java.io.IOException;
import java.net.UnknownHostException;

import UserInfo.Account;
import UserInfo.Kitchen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * @author hacheson
 *
 */

public class DBHelper implements DBHelperInterface{
	private Mongo mongo_;
	private DBCollection userCollection_;
	private DB userDb_;
	public DBHelper(){
		String s = "mongod --port 27017 -dbpath /home/hacheson/course/cs032/MongoData/";
		String[] args = s.split(" ");
		userDb_ = mongo_.getDB("users");
		userCollection_ = userDb_.getCollection("userCollection");
		Process p = null;
		try{
			p = Runtime.getRuntime().exec(s);
		} catch(IOException e){
			e.printStackTrace();
		}
		try {
			mongo_ = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			System.err.println("ERROR: Could not connect to mongodb, unknown host.");
			e.printStackTrace();
		} catch (MongoException e) {
			System.err.println("ERROR: Could not connect to mongodb.");
			e.printStackTrace();
		}
	}

	@Override
	public Account getAccount(String username) {
		System.out.println("Making object.");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		DBCursor cursor = userCollection_.find(searchQuery);
		
		while (cursor.hasNext()) {
			 //System.out.println(cursor.next().get("account"));
			System.out.println(cursor.next());
		}
		return null;
	}

	@Override
	public void storeAccount(Account a) {
		BasicDBObject document = new BasicDBObject();
		document.put("username", "Hannah");
		document.put("password", 1234);
		document.put("account", "sample account");
		userCollection_.insert(document);
	}

	@Override
	public Kitchen getKitchen(String id) {
		return null;
	}

	@Override
	public void storeKitchen(Kitchen k) {
		//TODO: Make sure username is unique.
		
	}
	
	public String createKitchenId(){
		//TODO: Generate number, then see if /k/number is in db, and keep doing
		//that until find a unique number.
		return null;
	}
}
