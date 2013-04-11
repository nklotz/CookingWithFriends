/**
 * 
 */
package UserInfo;

import java.util.HashSet;

/**
 * @author hacheson
 * This class represents an event: contains a date, name, and list of user.
 */
public class Event {

	private String _date;
	private String _name;
	private HashSet<User> _users;
	
	public Event(String name, String date, HashSet<User> users){
		_name = name;
		_date = date;
		_users = users;
	}
	
	/**
	 * Adds a user to the event.
	 * @param u
	 */
	public void addUser(User u){
		_users.add(u);
	}
	
	/**
	 * Removes a user from the event.
	 * @param u User The user to remove.
	 */
	public void removeUser(User u){
		_users.remove(u);
	}
	
	/**
	 * Sets the name of the event.
	 * @param name String name to set.
	 */
	public void setName(String name){
		_name = name;
	}
	
	/**
	 * Returns the event's name.
	 * @return String the name to return.
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Sets this event's date.
	 * @param date String date to set.
	 */
	public void setDate(String date){
		_date = date;
	}
	
	/**
	 * Returns this event's date.
	 * @return String date to return.
	 */
	public String getDate(){
		return _date;
	}
	
}
