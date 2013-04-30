/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hacheson
 * Represents a kitchen object. Kitchens have users, events, and a menu.
 */
public class Kitchen implements Serializable, Nameable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashSet<Event> _events = new HashSet<Event>();
	private HashSet<Recipe> _recipes = new HashSet<Recipe>();
	private HashMap<Ingredient, List<String>> _ingToUsers = new HashMap<Ingredient, List<String>>();
	
	private String _id = "", _name = "";
	private HashSet<String> _activeUsers = new HashSet<String>();
	private HashSet<String> _requestedUsers = new HashSet<String>();
	//TODO: Create a message board.
	//private ArrayList<String> _messageBoard;
	
	public Kitchen(String name){
		_name = name;
	}
	
	public Kitchen(String name, String id){
		_id = id;
		_name = name;
	}
	
	public String getName(){
		return _name;
	}
	
	public KitchenName getKitchenName(){
		return new KitchenName(_name, _id);
	}
	
	
	public void addActiveUser(String user){
		removeRequestedUser(user);
		_activeUsers.add(user);
	}
	
	public void addRequestedUser(String user){
		_requestedUsers.add(user);
	}
	
	public void removeActiveUser(String user){
		_activeUsers.remove(user);
	}
	
	public void removeRequestedUser(String user){
		_requestedUsers.remove(user);
	}
	
	public HashSet<String> getActiveUsers(){
		return _activeUsers;
	}
	
	public HashSet<String> getRequestedUsers(){
		return _requestedUsers;
	}
	
	public Set<Ingredient> getIngredients() {
		return _ingToUsers.keySet();
	}
	
	
	/**
	 * Sets the id of the kitchen object.
	 * @param id
	 */
	public void setID(String id){
		_id = id;
	}
	
	/**
	 * Returns the string id.
	 * @return
	 */
	public String getID(){
		return _id;
	}
	
	/**
	 * Adds an event to the kitchen.
	 * @param e Event e to add to kitchen.
	 */
	public void addEvent(Event e){
		_events.add(e);
	}
	
	/**
	 * Removes an event from the kitchen.
	 * @param e Event to remove from kitchen.
	 */
	public void removeEvent(Event e){
		_events.remove(e);
	}
	
	/**
	 * Adds a recipe to the panel.
	 * @param r Recipe to remove from panel.
	 */
	public void addRecipe(Recipe r){
		_recipes.add(r);
	}
	
	/**
	 * Removes a recipe from the panel.
	 * @param r
	 */
	public void removeRecipe(Recipe r){
		_recipes.remove(r);
	}
	
	/**
	 * Removes all recipes from the kitchen.
	 */
	public void removeAllRecipes(){
		_recipes = new HashSet<Recipe>();
	}

	@Override
	public String toString() {
		return "Kitchen [_users=" + _activeUsers + ", _events=" + _events
				+ ", _recipes=" + _recipes + ", _id=" + _id + "]";
	}
	
}
