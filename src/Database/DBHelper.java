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
	private DB userDB_;
	private DB kitchenDB_;
	private DBCollection kitchenCollection_;
	
	public DBHelper(){
		String s = "mongod --port 27017 -dbpath /home/hacheson/course/cs032/MongoData/";
		String[] args = s.split(" ");
		Process p = null;
		try{
			p = Runtime.getRuntime().exec(s);
		} catch(IOException e){
			e.printStackTrace();
		}
		try {
			mongo_ = new Mongo("localhost", 27017);
			userDB_ = mongo_.getDB("users");
			userCollection_ = userDB_.getCollection("userCollection");
			kitchenDB_ = mongo_.getDB("kitchens");
			kitchenCollection_ = kitchenDB_.getCollection("kitchenCollection");
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
		System.out.println("Making object in db helper get account.");
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
		System.out.println("storing accoutn");
		BasicDBObject document = new BasicDBObject();
		document.put("username", "Hannah");
		document.put("password", 1234);
		document.put("account", "sample account");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", a.getUser().getName());
		//Adds it if it doesn't exist  currently.
		if(userCollection_.find(searchQuery).length() == 0){
			userCollection_.insert(document);
		}
		//userCollection_.remove(searchQuery);
	}

	@Override
	public Kitchen getKitchen(String id) {
		System.out.println("Making object in db helper get kitchen.");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", id);
		DBCursor cursor = kitchenCollection_.find(searchQuery);
		
		while (cursor.hasNext()) {
			 //System.out.println(cursor.next().get("account"));
			System.out.println(cursor.next());
		}
		return null;
	}

	@Override
	public void storeKitchen(Kitchen k) {
		BasicDBObject document = new BasicDBObject();
		document.put("id", "/k/0");
		document.put("kitchen", "sample kitchen");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", k.getId());
		//Adds it if it doesn't exist  currently.
		if(kitchenCollection_.find(searchQuery).length() == 0){
			kitchenCollection_.insert(document);
		}
	}
	
	/**
	 * Creates a new, unique kitchen id by finding if an id is in the db already.
	 * @return String id The new, unique kitchen id.
	 */
	public String createKitchenId(){
		//Generate a random number and see if it's not in the db already.
		while(true){
			int num = (int)(Math.random()*1000000);
			BasicDBObject searchQuery = new BasicDBObject();
			String id = "/k/" + num;
			searchQuery.put("id", id);
			if(searchQuery.isEmpty()){
				return id;
			}
		//	DBCursor cursor = kitchenCollection_.find(searchQuery);
		//	if(!cursor.hasNext()){
		//		return id;
		//	}	
		}
	}
	
	
}
