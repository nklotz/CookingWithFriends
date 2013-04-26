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
 
 public static Account makeAccount(){
	//Hannah
		User hann = new User("Hannah", "Hannah");
		hann.setAddress("LittleField");
		HashSet<String> i = new HashSet<String>();
		i.add("apples");
		i.add("pears");
		i.add("articokes");
		String[] arr = {"chicken", "bread", "cheese"};
		ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(arr));
		
		HashSet<String> sl = new HashSet<String>();
		sl.add("2 apples");
		sl.add("2 pears");
		sl.add("2 articokes");

//		Recipe rec = new Recipe("Chicken Kiev", "111", "Place stuff in a bowl and mix it.", ingredients); HANNAH: I made a recipe interface and implemented it for the yummly recipe. There's no constructor like this anymore, but you could create a new class for testing if you want. 
//		Recipe rec2 = new Recipe("Chicken Kiev2", "2111", "2Place stuff in a bowl and mix it.", ingredients);
		HashSet<Recipe> recipes = new HashSet<Recipe>();
		//recipes.add(rec);
		//recipes.add(rec2);
		
		Account acc = new Account(hann);
		acc.setRecipes(recipes);
		acc.setIngredients(i);
		acc.setShoppingList(sl);
		return acc;
 }
 
}
