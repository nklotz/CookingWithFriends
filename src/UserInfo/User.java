/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @author hacheson
 * The class for a user, has address information about the user.
 */
public class User implements Serializable{

	private HashSet<Kitchen> _kitchens;
	private String _address;
	private String _name;
	private String _password;
	private Preferences _preferences;
	
	public User(HashSet<Kitchen> kitchens, String address, String name, String password, Preferences pref){
		_kitchens = kitchens;
		_address = address;
		_name = name;
		_password = password;
		_preferences = pref;
	}
	
	public User(String name, String password){
		_name = name;
		_password = password;
		_kitchens = new HashSet<Kitchen>();
	}
	
	/**
	 * Returns the user's address.
	 * @return String user's address.
	 */
	public String getAddress(){
		return _address;
	}
	
	/**
	 * Returns the user name.
	 * @return String the user's name.
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Returns the password.
	 * @return String password.
	 */
	public String getPassword(){
		return _password;
	}
	
	/**
	 * Adds a kitchen to the user.
	 * @param k Kitchen the user's kitchen.
	 */
	public void addKitchen(Kitchen k){
		_kitchens.add(k);
	}
	
	/**
	 * Removes a kitchen from the user.
	 * @param k Kitchen kitchen to remove.
	 */
	public void removeKitchen(Kitchen k){
		_kitchens.remove(k);
	}
	
	/**
	 * Sets the user's preferences.
	 * @param pref Preferences for the user to set.
	 */
	public void setPreferences(Preferences pref){
		_preferences = pref;
	}
	
	public void setAddress(String add){
		_address = add;
	}
	
	
}
