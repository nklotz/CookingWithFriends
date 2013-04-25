/**
 * 
 */
package Database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import UserInfo.Account;
import UserInfo.Kitchen;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import sun.security.provider.SecureRandom;
//import org.apache.commons.codec.binary.Base6;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
	private DB userPassDB_;
	private DBCollection userPassCollection_;
	
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
			userPassDB_ = mongo_.getDB("username/passwords");
			userPassCollection_ = userPassDB_.getCollection("username/passwordsCollection");
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
		document.put("username", a.getUser().getID());
		document.put("password", a.getUser().getPassword());
		document.put("account", getObjectString(a));
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", a.getUser().getID());
		//Adds it if it doesn't exist  currently.
		if(userCollection_.find(searchQuery).length() == 0){
			userCollection_.insert(document);
		}
		else{
			userCollection_.remove(searchQuery);
			userCollection_.insert(document);
		}
	}

	@Override
	public Kitchen getKitchen(String id) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", id);
		
		DBCursor cursor = kitchenCollection_.find(searchQuery);
		searchQuery.remove("/k/0");
		
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		return null;
	}

	@Override
	public void storeKitchen(Kitchen k) {
		System.out.println("storing kitchen: " + k);
		BasicDBObject document = new BasicDBObject();
		document.put("id", "/k/0");
		document.put("kitchen", "sample kitchen");
		BasicDBObject searchQuery = new BasicDBObject();
		String id = createKitchenId();
		k.setId(id);
		searchQuery.put("id", id);
		//Adds it if it doesn't exist  currently.
		if(kitchenCollection_.find(searchQuery).length() == 0){
			kitchenCollection_.insert(document);
		}
		//Otherwise remove the current object, and add the new kitchen.
		else{
			kitchenCollection_.remove(searchQuery);
			kitchenCollection_.insert(document);
		}
		
	}
	
	public void storeUsernamePassword(String username, String password){
		System.out.println("storing username");
		BasicDBObject document = new BasicDBObject();
		document.put("username", username);
		document.put("password", getEncrypted(password));
		document.put("encryptKey", getEncryptedKey(password));
		//Adds it if it doesn't exist  currently.
		if(validUsername(username)){
			userCollection_.insert(document);
		}
	}
	
	@Override
	public boolean checkUsernamePassword(String username, String password) {
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		DBCursor cursor = kitchenCollection_.find(searchQuery);
		//Username doesn't exist in database.
		if(!cursor.hasNext()){
			return false;
		}
		else{
			System.out.println(cursor.next());
			if(cursor.next().get("password").equals(getEncrypted(password))){
				return true;
			}
		}
		

		
		//encode the password that you're given and check if it matches.
		
		
	}
	
	public String getEncryptedKey(String password){
		int saltLen = 32;
	    byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
	    // store the salt with the password
	    return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
	}
	
	/** Computes a salted PBKDF2 hash of given plaintext password
    suitable for storing in a database. 
    Empty passwords are not supported. */
	public static String getSaltedHash(String password) throws Exception {
		int saltLen = 32;
	    byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
	    // store the salt with the password
	    return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
	}
	
	public String getEncrypted(String password){
		byte[] salt = new byte[16];
		//Random.nextBytes(salt);
		random.nextBytes(salt);
		KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		System.out.println("salt: " + new BigInteger(1, salt).toString(16));
		System.out.println("hash: " + new BigInteger(1, hash).toString(16));
	}
	
	@Override
	public boolean validUsername(String username){
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		DBCursor cursor = kitchenCollection_.find(searchQuery);
		
		if(cursor.hasNext()) {
			return false;
		}
		return true;
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
			System.out.println("sq: " + searchQuery);
			DBCursor cursor = kitchenCollection_.find(searchQuery);
			if(!cursor.hasNext()){
				return "/k/0";
			//	return id;
			}	
		
		}
	}
	
	/**
	 * Returns the serialized object in string form.
	 * @param o Serializable object o.
	 * @return String form of object.
	 */
	public static String getObjectString(Serializable o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
	        oos.close();
		} catch (IOException e) {
			System.out.println("ERROR: Could not make serializable object." + e.getMessage());
		}
        return new String(Base64.encode(baos.toByteArray()));
    }

	
	
}
