package server;

import java.util.HashMap;
import java.util.HashSet;

import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Kitchen;
import UserInfo.User;

/**
 * This class keeps track of the active kitchens and users. When a user logs in
 * it's account is set and all unopened kitchens it is a part of are opened up.
 * 
 * When a user logs off, removeUser is called. If a kitchen's only active user
 * is the user who just logged off, then that kitchen is removed from memory and 
 * stored in the DB.
 * 
 * @author nklotz
 *
 */

public class KitchenPool {

	HashMap<String, HashSet<String>> _kIDtoUsers, _userToKitchens ;
	HashMap<String, Kitchen> _idToKitchen;
	DBHelper _helper;
	
	public KitchenPool(DBHelper helper){
		_kIDtoUsers = new HashMap<String, HashSet<String>>();
		_userToKitchens = new HashMap<String, HashSet<String>>();
		_idToKitchen = new HashMap<String, Kitchen>();
		_helper = helper;
	}
	
	/**
	 * Returns an active kitchen
	 */
	public Kitchen getKitchen(String kID){
		if(_idToKitchen.containsKey(kID)){
			return _idToKitchen.get(kID);
		}
		return null;
	}
	
	
	/**
	 * Adds a kitchen and extracts users.
	 */
	public void addKitchen(Kitchen kitchen){
		_idToKitchen.put(kitchen.getId(), kitchen);
		HashSet<String> users = new HashSet<String>();
		for(String u: kitchen.getUsers()){
			users.add(u);
		}
		_kIDtoUsers.put(kitchen.getId(), users);
	}
	
	/**
	 * Adds a newly created kitchen and extracts users and updates kitchen list of 
	 * active users
	 */
	public void addNewKitchen(Kitchen kitchen){
		_idToKitchen.put(kitchen.getId(), kitchen);
		HashSet<String> users = new HashSet<String>();
		for(String u: kitchen.getUsers()){
			if(_userToKitchens.containsKey(u)){
				_userToKitchens.get(u).add(kitchen.getId());
			}
			users.add(u);
		}
		_kIDtoUsers.put(kitchen.getId(), users);
	}
	
	/**
	 * Adds a user and opens up all non-opened kitchens
	 */
	public void addAccount(Account account){
		String userName = account.getUser().getID();
		HashSet<String> kitchenIDs = account.getUser().getKitchens();
		_userToKitchens.put(userName, kitchenIDs);
		for(String k: kitchenIDs){
			if(!_idToKitchen.containsKey(k)){
				Kitchen kit = _helper.getKitchen(k);
				addKitchen(kit);
			}
		}	
	}
	
	/**
	 * Removes references to a user. If the user was the only active user of kitchen
	 * that kitchen is removed from memory.
	 */
	public void removeUser(String userID){
		HashSet<String> kitchens = _userToKitchens.get(userID);
		for(String k: kitchens){
			HashSet<String> users = _kIDtoUsers.get(k);
			if(!hasActiveUser(users, userID)){
				removeKitchen(k);
			}
		}
		_userToKitchens.remove(userID);
	}
	
	/**
	 * Stores kitchen in data base before removing kitchen pool references to it.
	 */
	public void removeKitchen(String kID){
		_helper.storeKitchen(_idToKitchen.get(kID));
		_kIDtoUsers.remove(kID);
		_idToKitchen.remove(kID);	
	}
	
	/**
	 * Checks if any user of a kitchen, other than userToDelete, is currently logged in
	 * (if logged in they will be in 
	 */
	public boolean hasActiveUser(HashSet<String> kitchenUsers, String userToDelete){
		for(String u: kitchenUsers){
			//if the user is not the one we're about to delete and it is in the hashmap,
			//then the kitchen has an active user
			if(! u.equals(userToDelete) && _userToKitchens.containsKey(u)){
				return true;
			}
		}
		//if got here without finding an active user, than user to delete is only active user.
		return false;
	}
}
