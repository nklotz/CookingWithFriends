/**
 * 
 */
package server;

import java.util.HashMap;

/**
 * @author hacheson
 * Fake database storage system.
 */
public class Database {

	HashMap<String, String> userToPassword_;
	public Database(){
		userToPassword_ = new HashMap<String, String>();
		userToPassword_.put("Hannah", "Hannah");
		userToPassword_.put("Natalie", "Natalie");
		userToPassword_.put("Jon", "Jon");
		userToPassword_.put("Eddie", "Beyonce");
		
	}
	/**
	 * Returns whether a password is valid given a username.
	 * @param user User to check.
	 * @return 
	 */
	public String getPasswordFromUser(String user){
		System.out.println( "returning " + userToPassword_.get(user));
		return userToPassword_.get(user);
	}
	
	public void addUser(String user, String password){
		userToPassword_.put(user, password);
	}
	
	public void removeUser(String user, String password){
		userToPassword_.remove(user);
	}
	
	
}
