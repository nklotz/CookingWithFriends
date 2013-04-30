package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
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

	HashMap<KitchenName, HashSet<String>> _kIDtoUsers;
	HashMap<String, HashSet<KitchenName>>_userToKitchens ;
	HashMap<String, Kitchen> _idToKitchen;
	DBHelper _helper;
	ClientPool _clients;
	
	public KitchenPool(DBHelper helper, ClientPool clients){
		_kIDtoUsers = new HashMap<KitchenName, HashSet<String>>();
		_userToKitchens = new HashMap<String, HashSet<KitchenName>>();
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
		System.out.println("adding kitchen: " + kitchen);
		System.out.println("active users: " + kitchen.getActiveUsers());
		
		_idToKitchen.put(kitchen.getID(), kitchen);
		_kIDtoUsers.put(kitchen.getKitchenName(), kitchen.getActiveUsers());
		
		System.out.println(_kIDtoUsers.containsKey(kitchen.getKitchenName()));
		System.out.println(kitchen.getKitchenName().hashCode());
		System.out.println(new KitchenName(kitchen.getName(), kitchen.getID()).hashCode());
		System.out.println("check that kidtousers has kitchenName " + _kIDtoUsers.get(kitchen.getKitchenName()));
		System.out.println("check 2: " + _kIDtoUsers.get(new KitchenName(kitchen.getName(), kitchen.getID())));
	}
	
	/**
	 * Adds a newly created kitchen and extracts users and updates kitchen list of 
	 * active users
	 */
	public void addNewKitchen(Kitchen kitchen){
		_idToKitchen.put(kitchen.getID(), kitchen);
		for(String u: kitchen.getActiveUsers()){
			if(_userToKitchens.containsKey(u)){
				_userToKitchens.get(u).add(kitchen.getKitchenName());
			}
		}
		_kIDtoUsers.put(kitchen.getKitchenName(),  kitchen.getActiveUsers());
	}
	
	/**
	 * Adds a user and opens up all non-opened kitchens
	 */
	public void addAccount(Account account){
		System.out.println("oepning account " + account.getID());
		String userName = account.getID();
		HashSet<KitchenName> kitchenIDs = account.getKitchens();
		System.out.println("HERE ARE THE account's kitchens " + kitchenIDs);
		_userToKitchens.put(userName, kitchenIDs);
		if (kitchenIDs != null){
			for(KitchenName k: kitchenIDs){
				if(!_idToKitchen.containsKey(k)){
					System.out.println("find kitchen: " + k.getName());
					Kitchen kit = _helper.getKitchen(k.getID());
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
		System.out.println("removing user " + userID + " from Kitchen pool");
	
		HashSet<KitchenName> kitchens = _userToKitchens.get(userID);
		System.out.println("kitchens: " + kitchens);
		if (kitchens != null){
			for(KitchenName k: kitchens){
				HashSet<String> users = _kIDtoUsers.get(k);
				System.out.println("checking if kitchen " + k.getName() + " has active users");
				System.out.println("users: " + users);
				if(!hasActiveUser(users, userID)){
					System.out.println("it doesn't! remove!");				
					removeKitchen(k.getID());
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
	
	
	public HashMap<KitchenName, Kitchen> getAllUserKitchens(String userID){
		HashMap<KitchenName, Kitchen> kitchens = new HashMap<KitchenName, Kitchen>();
		HashSet<KitchenName> kIDS = _userToKitchens.get(userID);
		if (kIDS != null){
			for(KitchenName k: kIDS){
				kitchens.put(k, _idToKitchen.get(k.getID()));
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
				k.addActiveUser(request.getKitchenUserID());
				break;
			case 4: //remove user from kitchen
				k.removeActiveUser(request.getKitchenUserID());
				break;
		  	case 5: //add event to kitchen
		  		k.addEvent(request.getEvent());
		  		break;
		  	case 6: //remove event from kitchen
		  		k.removeEvent(request.getEvent());
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
