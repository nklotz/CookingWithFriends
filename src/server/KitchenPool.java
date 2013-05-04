package server;

import java.util.HashMap;
import java.util.HashSet;

import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;

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
	
	public void removeUserIngredient(String userID, Ingredient ing){
		for(KitchenName kn: _userToKitchens.get(userID)){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!K: " +  _idToKitchen.get(kn.getID()));
			Kitchen k = _idToKitchen.get(kn.getID());
			k.removeIngredient(userID, ing);
			broadCastKitchen(k);
		}
	}
	
	public void removeUserDietRestriction(String userID, String restric){
		for(KitchenName kn: _userToKitchens.get(userID)){
			Kitchen k = _idToKitchen.get(kn.getID());
			k.removeDietaryRestriction(restric, userID);
			broadCastKitchen(k);
		}
	}
	
	public void removeUserAllergy(String userID, String allergy){
		for(KitchenName kn: _userToKitchens.get(userID)){
			Kitchen k = _idToKitchen.get(kn.getID());
			k.removeAllergy(allergy, userID);
			broadCastKitchen(k);
		}
	}
	
	public void addUserDietRestriction(String userID, String restric){
		for(KitchenName kn: _userToKitchens.get(userID)){
			Kitchen k = _idToKitchen.get(kn.getID());
			k.addDietaryRestriction(restric, userID);
			broadCastKitchen(k);
		}
	}
	
	public void addUserAllergy(String userID, String allergy){
		System.out.println("userID " + userID + " has allergy to " + allergy);
		for(KitchenName kn: _userToKitchens.get(userID)){
			System.out.println("addding to kitchen " + kn.getName());	
			_idToKitchen.get(kn.getID()).addAllergy(allergy, userID);
			System.out.println(_idToKitchen.get(kn.getID()));
			broadCastKitchen(_idToKitchen.get(kn.getID()));
		}
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
		
		_idToKitchen.put(kitchen.getID(), kitchen);
		_kIDtoUsers.put(kitchen.getKitchenName(), kitchen.getActiveUsers());
		System.out.println("kid to users contains " + kitchen.getKitchenName() + ": " + _kIDtoUsers.get(kitchen.getKitchenName()));
		
		KitchenName n = new KitchenName(kitchen.getName(), kitchen.getID());
		System.out.println("kid to users contains " + n + ": " + _kIDtoUsers.get(n));


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
					removeKitchen(k);
				}
			}
			_userToKitchens.remove(userID);
		}
	}
	
	/**
	 * Stores kitchen in data base before removing kitchen pool references to it.
	 */
	public void removeKitchen(KitchenName kName){
		System.out.println("KITCHEN ID: " + _idToKitchen.get(kName.getID()));
		_helper.storeKitchen(_idToKitchen.get(kName.getID()));
		_kIDtoUsers.remove(kName);
		_idToKitchen.remove(kName.getID());	
	}
	
	/**
	 * Checks if any user of a kitchen, other than userToDelete, is currently logged in
	 * (if logged in they will be in 
	 */
	public boolean hasActiveUser(HashSet<String> kitchenUsers, String userToDelete){
		for(String u: kitchenUsers){
			//if the user is not the one we're about to delete and it is in the hashmap,
			//then the kitchen has an active user
			if(!u.equals(userToDelete) && _userToKitchens.containsKey(u)){
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
				k.addActiveUser(request.getAccount());
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
		  		k.addIngredient(request.getUsername(), request.getIngredient());
	  			break;	
	  		case 10: //remove ingredient from fridge	!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		  		k.removeIngredient(request.getUsername(), request.getIngredient());
	  			break;
	  		case 11:
	  			System.out.println("requested to add user " +  request.getUsername());
	  			k.addRequestedUser(request.getUsername());
	  			break;
	  		case 12:
	  			k.removeRequestedUser(request.getUsername());
	  			break;
  			default: 
  				return;
		}
		
		System.out.println("well i handeled that kitchen request");
		
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setKitchen(k);
		_clients.broadcastList(_kIDtoUsers.get(k.getKitchenName()), toReturn);
		
	}
	
	
	public void broadCastKitchen(Kitchen k){
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setKitchen(k);
		_clients.broadcastList(_kIDtoUsers.get(k.getKitchenName()), toReturn);
	}
}
