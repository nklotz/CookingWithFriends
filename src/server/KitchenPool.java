package server;

import java.util.HashMap;
import java.util.HashSet;

import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
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
	ClientPool _clients;
	
	public KitchenPool(DBHelper helper, ClientPool clients){
		_kIDtoUsers = new HashMap<String, HashSet<String>>();
		_userToKitchens = new HashMap<String, HashSet<String>>();
		_idToKitchen = new HashMap<String, Kitchen>();
		_helper = helper;
		_clients = clients;
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
		String userName = account.getUserId();
		HashSet<String> kitchenIDs = account.getKitchens();
		_userToKitchens.put(userName, kitchenIDs);
		if (kitchenIDs != null){
			for(String k: kitchenIDs){
				if(!_idToKitchen.containsKey(k)){
					Kitchen kit = _helper.getKitchen(k);
					addKitchen(kit);
				}
			}	
		}
	}
	
	/**
	 * Removes references to a user. If the user was the only active user of kitchen
	 * that kitchen is removed from memory.
	 */
	public void removeUser(String userID){
		HashSet<String> kitchens = _userToKitchens.get(userID);
		if (kitchens != null){
			for(String k: kitchens){
				HashSet<String> users = _kIDtoUsers.get(k);
				if(!hasActiveUser(users, userID)){
					removeKitchen(k);
				}
			}
			_userToKitchens.remove(userID);
		}
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
	
	
	public HashMap<String, Kitchen> getAllUserKitchens(String userID){
		HashMap<String, Kitchen> kitchens = new HashMap<String, Kitchen>();
		HashSet<String> kIDS = _userToKitchens.get(userID);
		if (kIDS != null){
			for(String k: kIDS){
				kitchens.put(k, _idToKitchen.get(k));
			}
		}
		return kitchens;	
	}
	
	public void updateKitchen(Request request){
		if(request.getKitchenID()==null){
			return;
		}
		Kitchen k = getKitchen(request.getKitchenID());
		if(k==null){
			return;
		}
		
		switch (request.getType()){
			case 3: //add user to kitchen
				k.addUser(request.getKitchenUserID());
				break;
			case 4: //remove user from kitchen
				k.removeUser(request.getKitchenUserID());
				break;
		  	case 5: //add event to kitchen
		  		k.addEvent(request.getKitchenEvent());
		  		break;
		  	case 6: //remove event from kitchen
		  		k.removeEvent(request.getKitchenEvent());
		  		break;
		  	case 7: //add recipe to kitchen
		  		k.addRecipe(request.getRecipe());
		  		break;
		  	case 8: //remove recipe from kitchen
		  		k.removeRecipe(request.getRecipe());
		  		break;
	  		case 9: //added ingredient to fridge!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		  		break;	
	  		case 10: //remove ingredient from fridge	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		  		break;
  			default: 
  				return;
		}
		
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setKitchen(k);
		_clients.broadcastList(_kIDtoUsers.get(request.getKitchenID()), toReturn);
		
	}
}
