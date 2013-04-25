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
	private HashSet<User> _users;
	private HashSet<Event> _events;
	private HashSet<Recipe> _recipes;
	private String _id;
	//TODO: Create a message board.
	//private ArrayList<String> _messageBoard;
	
	public Kitchen(HashSet<User> users, HashSet<Event> events, HashSet<Recipe> recipes){
		_users = users;
		_events = events;
		_recipes = recipes;
	}
	
	public Kitchen(){
		_users = new HashSet<User>();
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
	public void addUser(User u){
		_users.add(u);
	}
	
	/**
	 * Removes a user from the kitchen.
	 * @param u User to remove from kitchen.
	 */
	public void removeUser(User u){
		_users.remove(u);
	}
	
	/**
	 * Adds an event to the kitchen.
	 * @param e Event e to add to kitchen.
	 */
	public void addKitchen(Event e){
		_events.add(e);
	}
	
	/**
	 * Removes an event from the kitchen.
	 * @param e Event to remove from kitchen.
	 */
	public void removeKitchen(Event e){
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
		return "Kitchen [_users=" + _users + ", _events=" + _events
				+ ", _recipes=" + _recipes + ", _id=" + _id + "]";
	}
	
	public HashSet<User> getUsers(){
		return _users;
	}
	
}
