/**
 * 
 */
package UserInfo;

import java.io.Serializable;
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
	private Set<Recipe> _recipes = new HashSet<Recipe>();
	private HashMap<Ingredient, HashSet<String>> _ingToUsers = new HashMap<Ingredient, HashSet<String>>();
	
	private String _id = "", _name = "";
	private HashSet<String> _activeUsers = new HashSet<String>();
	private HashSet<String> _requestedUsers = new HashSet<String>();
	private HashMap<String, HashSet<String>> _dietRestricts = new HashMap<String, HashSet<String>>();
	private HashMap<String, HashSet<String>> _allergies = new HashMap<String, HashSet<String>>();

	//TODO: Create a message board.
	//private ArrayList<String> _messageBoard;
	
	public Kitchen(String name){
		_name = name;
	}
	
	public Kitchen(String name, String id){
		_id = id;
		_name = name;
	}
	
	/**
	 * Gets the event if it equals the event given the event name.
	 * @param eventName
	 * @return
	 */
	public Event getEvent(Event event){
		for(Event e: _events){
			if(e.equals(event))
				return e;
		}
		System.out.println("UNABLE TO GET EVENT IN KITCEHN");
		return null;
	}
	public String getName(){
		return _name;
	}
	
	public Set<Recipe> getRecipes(){
		return _recipes;
	}
	
	public void setRecipes(Set<Recipe> rs){
		_recipes = rs;
	}
	
	public KitchenName getKitchenName(){
		return new KitchenName(_name, _id);
	}
	
	
	public void addActiveUser(Account user){
		System.out.println("trying to add active user: " + user.getName() + " to myself (" + _id + ")");
		removeRequestedUser(user.getID());
		for(String r: user.getDietaryRestrictions()){
			addDietaryRestriction(r, user.getID());
		}
		for(String r: user.getAllergies()){
			addAllergy(r, user.getID());
		}
		_activeUsers.add(user.getID());
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
	
	public Set<String> getDietaryRestrictions() {
		return _dietRestricts.keySet();
	}
	
	public Set<String> getAllergies() {
		return _allergies.keySet();
	}
	
	public void addIngredient(String user, Ingredient ing) {
		System.out.println("adding " + ing.getName() + " from " + user);
		if(!_ingToUsers.containsKey(ing)){
			_ingToUsers.put(ing, new HashSet<String>());
		}
		_ingToUsers.get(ing).add(user);
	}
	
	public void removeIngredient(String user, Ingredient ing){
		System.out.println("removing " + ing.getName() + " by " + user);
		if(_ingToUsers.containsKey(ing)){
			HashSet<String> hs = _ingToUsers.get(ing);
			hs.remove(user);
			if(hs.size()==0){
				_ingToUsers.remove(ing);
			}
		}
	}

	public void setIngredients(Set<Ingredient> set){
		//TODO: write this_ingToUsers.
	}
	
	public void addDietaryRestriction(String restric, String userID){
		if(!_dietRestricts.containsKey(restric)){
			_dietRestricts.put(restric, new HashSet<String>());
		}
		_dietRestricts.get(restric).add(userID);
	}
	
	public void removeDietaryRestriction(String restric, String userID){
		if(_dietRestricts.containsKey(restric)){
			HashSet<String> hs = _dietRestricts.get(restric);
			hs.remove(userID);
			if(hs.size()==0){
				_dietRestricts.remove(restric);
			}
		}
	}
	
	public void addAllergy(String allergy, String userID){
		if(!_allergies.containsKey(allergy)){
			_allergies.put(allergy, new HashSet<String>());
		}
		_allergies.get(allergy).add(userID);
	}
	
	public void removeAllergy(String allergy, String userID){
		if(_allergies.containsKey(allergy)){
			HashSet<String> hs = _allergies.get(allergy);
			hs.remove(userID);
			if(hs.size()==0){
				_allergies.remove(allergy);
			}
		}
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
	
	public HashSet<Event> getEvents(){
		return _events; 
	}
	
	/**
	 * Returns a list of names, used to populate the event selector in controller class.
	 * @return
	 */
	public HashSet<String> getEventNames(){
		HashSet<String> names = new HashSet<String>();
		for(Event e: _events){
			names.add(e.getName());
		}
		return names;
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
				+ ", _recipes=" + _recipes + ", _id=" + _id + ", _ingredients" + _ingToUsers.keySet() + ", _restrictions=" + _dietRestricts + ", _allergies" + _allergies+"]";
	}
	
	public HashMap<Ingredient, HashSet<String>> getIngredientsMap(){
		return _ingToUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kitchen other = (Kitchen) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}
}
