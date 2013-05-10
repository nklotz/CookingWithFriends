package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javafx.event.Event;

import ClientServerRequests.Request;
import ClientServerRequests.RequestReturn;
import Database.DBHelper;
import UserInfo.Account;
import UserInfo.KitchenEvent;
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

	ConcurrentHashMap<KitchenName, HashSet<String>> _kIDtoUsers;
	ConcurrentHashMap<String, HashSet<KitchenName>>_userToKitchens ;
	ConcurrentHashMap<String, Kitchen> _idToKitchen;
	DBHelper _helper;
	ClientPool _clients;
	
	public KitchenPool(DBHelper helper, ClientPool clients){
		_kIDtoUsers = new ConcurrentHashMap<KitchenName, HashSet<String>>();
		_userToKitchens = new ConcurrentHashMap<String, HashSet<KitchenName>>();
		_idToKitchen = new ConcurrentHashMap<String, Kitchen>();
		_helper = helper;
		_clients = clients;
	}
	
	public void removeUserIngredient(String userID, Ingredient ing){
		for(KitchenName kn: _userToKitchens.get(userID)){
			Kitchen k = _idToKitchen.get(kn.getID());	
			k.removeIngredient(userID, ing);
			updateKitchenReferences(k);
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
		for(KitchenName kn: _userToKitchens.get(userID)){
			_idToKitchen.get(kn.getID()).addAllergy(allergy, userID);
			broadCastKitchen(_idToKitchen.get(kn.getID()));
		}
	}
	
	public void addRequestedUser(KitchenName kn, String userID){
		Kitchen k = _idToKitchen.get(kn.getID());
		k.addRequestedUser(userID);
		broadCastKitchen(k);
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
	
	public void removeUserFromKitchen(String userID, String kID){		
		//removing a user from a kitchen
		Kitchen k = _idToKitchen.get(kID);
		
		//remove ingredients that user contributed
		HashMap<Ingredient, HashSet<String>> imap = k.getIngredientsMap();
		
		HashSet<Ingredient> iToRemove = new HashSet<Ingredient>();
		
		for(Ingredient i: imap.keySet()){
			HashSet<String> users = imap.get(i);
			if(users.contains(userID)){
				iToRemove.add(i);
			}
		}
		
		for(Ingredient i: iToRemove){
			k.removeIngredient(userID, i);
		}
		
		//remove restrictions that user contributed
		HashMap<String, HashSet<String>> dmap = k.getDietaryRestrictionsMap();
		
		HashSet<String> dToRemove = new HashSet<String>();

		for(String d: dmap.keySet()){
			HashSet<String> users = dmap.get(d);
			if(users.contains(userID)){
				dToRemove.add(d);
			}
		}
		
		for(String d: dToRemove){
			k.removeDietaryRestriction(d, userID);
		}
		
		//remove allergies that user contributed
		HashMap<String, HashSet<String>> amap = k.getAllergiesMap();
		HashSet<String> aToRemove = new HashSet<String>();

		for(String a: amap.keySet()){
			HashSet<String> users = amap.get(a);
			if(users.contains(userID)){
				aToRemove.add(a);
			}
		}
		
		for(String a: aToRemove){
			k.removeAllergy(a, userID);
		}
		
		k.removeActiveUser(userID);
			
		_userToKitchens.get(userID).remove(k.getKitchenName());

		updateKitchenReferences(k);
		broadCastKitchen(k);
		
	}
	
	
	/**
	 * Adds a kitchen and extracts users.
	 */
	public void addKitchen(Kitchen kitchen){
		
		_idToKitchen.put(kitchen.getID(), kitchen);
		_kIDtoUsers.put(kitchen.getKitchenName(), kitchen.getActiveUsers());
		
		KitchenName n = new KitchenName(kitchen.getName(), kitchen.getID());

	}
	
	/**
	 * Adds a newly created kitchen and extracts users and updates kitchen list of 
	 * active users
	 */
	public void addNewKitchen(Kitchen kitchen){
		
		_idToKitchen.put(kitchen.getID(), kitchen);
		_kIDtoUsers.put(kitchen.getKitchenName(), kitchen.getActiveUsers());

		for(String s: kitchen.getActiveUsers()){
			if(_userToKitchens.containsKey(s)){
				_userToKitchens.get(s).add(kitchen.getKitchenName());
			}
		}
		_helper.storeKitchen(kitchen);
	}
	
	/**
	 * Adds a user and opens up all non-opened kitchens
	 */
	public void addAccount(Account account){
		String userName = account.getID();
		HashSet<KitchenName> kitchenIDs = account.getKitchens();
		_userToKitchens.put(userName, kitchenIDs);
		if (kitchenIDs != null){
			for(KitchenName k: kitchenIDs){
				if(!_idToKitchen.containsKey(k)){
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
	
		HashSet<KitchenName> kitchens = _userToKitchens.get(userID);
		if (kitchens != null){
			for(KitchenName k: kitchens){
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
	public void removeKitchen(KitchenName kName){
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
		if(request.getKitchenID()==null ){
			return;
		}
		Kitchen k = getKitchen(request.getKitchenID());
		if(k==null){
			k = _helper.getKitchen(request.getKitchenID());
			if(k==null){
				return;
			}
		}
		
		switch (request.getType()){
			case 3: //add user to kitchen
				k.addActiveUser(request.getAccount());
				break;
			case 4: //remove user from kitchen
				k.removeActiveUser(request.getKitchenUserID());
				break;
		  	case 5: //add event to kitchen
		  		k.removeEvent(new KitchenEvent(request.getEvent().getName(), null, null, k));
		  		k.addEvent(request.getEvent());
		  		break;
		  	case 6: //remove event from kitchen
		  		k.removeEvent(new KitchenEvent(request.getEvent().getName(), null, null, k));
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
	  		case 16:
	  			k.removeRequestedUser(request.getUsername());
	  			break;
	  		case 17:
	  			KitchenEvent e = k.getEvent(new KitchenEvent(request.getEventName(), null, k));
	  			e.addShoppingIngredient(request.getIngredient());
	  			k.addEvent(e);
	  			break;
	  		case 20:
	  			KitchenEvent messagEvent = k.getEvent(new KitchenEvent(request.getEventName(), null, k));
	  			messagEvent.addMessages(request.getNewMessages());
	  			k.addEvent(messagEvent);
	  			break;
	  		case 34:
	  			for(Ingredient i: request.getIngredientList()){
	  				k.addIngredient(request.getUsername(), i);
	  			}
	  			break;
  			default: 
  				return;
		}
		
		
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setKitchen(k);
		updateKitchenReferences(k);
		
		
		_clients.broadcastList(_kIDtoUsers.get(k.getKitchenName()), toReturn);
		
	}
	
	
	public void broadCastKitchen(Kitchen k){
		RequestReturn toReturn = new RequestReturn(2);
		toReturn.setKitchen(k);
		_clients.broadcastList(_kIDtoUsers.get(k.getKitchenName()), toReturn);
	}
	
	public void updateKitchenReferences(Kitchen kitchen){
	
		_idToKitchen.put(kitchen.getID(), kitchen);
		_kIDtoUsers.put(kitchen.getKitchenName(), kitchen.getActiveUsers());

		for(String s: kitchen.getActiveUsers()){
			if(_userToKitchens.containsKey(s)){
				_userToKitchens.get(s).add(kitchen.getKitchenName());
			}
		}
		_helper.storeKitchen(kitchen);
	}
}
