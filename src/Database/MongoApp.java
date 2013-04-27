package Database;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import UserInfo.Account;
import UserInfo.Recipe;
import UserInfo.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ReflectionDBObject;
 //username, password, account
public class MongoApp {
 
 public static void main(String[] args) {
 
	 try {
	 
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("users");
		DBCollection collection = db.getCollection("userCollection");
		//ReflectionDBObject document = new ReflectionDBObject();
		BasicDBObject document = new BasicDBObject();
		document.put("username", "Hannah");
		document.put("password", 1234);
		 
		Account acc = makeAccount();
		document.put("account", "sample account");
		//document.put("author", "- Peter F. Drucker");
		collection.insert(document);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", "Hannah");
		DBCursor cursor = collection.find(searchQuery);
		
	 while (cursor.hasNext()) {
		 //System.out.println(cursor.next().get("account"));
		// cursor.next().
		 System.out.println(cursor.next());
	 }
	 	System.out.println("Done");
	 } catch (UnknownHostException e) {
		 e.printStackTrace();
	 } catch (MongoException e) {
		 e.printStackTrace();
 }
 
 }
 
}
