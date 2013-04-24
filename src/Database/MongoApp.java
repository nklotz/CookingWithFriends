package Database;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			 
			 Mongo mongo = new Mongo("localhost", 27017);
			 DB db = mongo.getDB("quotesdb");
			 DBCollection collection = db.getCollection("quotesCollection");
			// BasicDBObject <span id="IL_AD10" class="IL_AD">document</span> = new BasicDBObject();
			 BasicDBObject document = new BasicDBObject();
			 document.put("id", 8888);
			// document.put("quote", "Long range planning does not deal with future decisions, but with the future of <span id="IL_AD5" class="IL_AD">present</span> decisions.");
			 document.put("quote", "Long range planning does not deal with future decisions, but with the future of.");
			 document.put("author", "- Peter F. Drucker ");
			 
			 collection.insert(document);
			 BasicDBObject searchQuery = new BasicDBObject();
			 searchQuery.put("id", 8888);
			 DBCursor cursor = collection.find(searchQuery);
			 while (cursor.hasNext()) {
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
