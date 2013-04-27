/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @author hacheson
 * Represents a kitchen object. Kitchens have users, events, and a menu.
 */
public class Kitchen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashSet<String> _userIDs;
	private HashSet<Event> _events;
	private HashSet<Recipe> _recipes;
	private String _id;
	private HashSet<String> _activeUsers;
	//TODO: Create a message board.
	//private ArrayList<String> _messageBoard;
	
	public Kitchen(HashSet<String> users, HashSet<Event> events, HashSet<Recipe> recipes, HashSet<String> activeUsers){
		_userIDs = users;
		_events = events;
		_recipes = recipes;
		_activeUsers = activeUsers;
	}
	
	public Kitchen(){
		_userIDs = new HashSet<String>();
		_events = new HashSet<Event>();
		_recipes = new HashSet<Recipe>();
	}
	
	/**
	 * Sets the id of the kitchen object.
	 * @param id
	 */
	public void setId(String id){
		_id = id;
	}
	
	/**
	 * Returns the string id.
	 * @return
	 */
	public String getId(){
		return _id;
	}
	/**
	 * Adds a user to the kitchen.
	 * @param u User to add to the kitchen.
	 */
	public void addUser(String u){
		_userIDs.add(u);
	}
	
	/**
	 * Removes a user from the kitchen.
	 * @param u User to remove from kitchen.
	 */
	public void removeUser(String u){
		_userIDs.remove(u);
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
		return "Kitchen [_users=" + _userIDs + ", _events=" + _events
				+ ", _recipes=" + _recipes + ", _id=" + _id + "]";
	}
	
	public HashSet<String> getUsers(){
		return _userIDs;
	}
	
}
