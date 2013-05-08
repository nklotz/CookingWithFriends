/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hacheson
 * This class represents an event: contains a date, name, and list of user.
 */
public class KitchenEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date _date;
	private String _time;
	private String _name;
	private Kitchen _kitchen;
	private Set<Recipe> _menu;
	private Set<Ingredient> _shoppingIng;
	private StringBuilder _messages = new StringBuilder("");
	
	public KitchenEvent(String name, Date date, Kitchen kitchen){
		_name = name;
		_date = date;
		_kitchen = kitchen;
		_menu = new HashSet<Recipe>();
		_shoppingIng = new HashSet<Ingredient>();
	}
	
	public KitchenEvent(String name, Date date, String time, Kitchen kitchen){
		_name = name;
		_date = date;
		_kitchen = kitchen;
		_menu = new HashSet<Recipe>();
		_shoppingIng = new HashSet<Ingredient>();
		_time = time;
	}
	
	public void addShoppingIngredient(Ingredient i){
		_shoppingIng.add(i);
	}
	public void removeShoppingIngredient(Ingredient i){
		_shoppingIng.remove(i);
	}
	public Set<Ingredient> getShoppingIngredients(){
		return _shoppingIng;
	}
	public void addRecipe(Recipe r){
		_menu.add(r);
	}
	
	public void removeRecipe(Recipe r){
		_menu.remove(r);
	}
	
	public  Set<Recipe> getMenuRecipes(){
		return _menu;
	}
	/**
	 * Sets the name of the event.
	 * @param name String name to set.
	 */
	public void setName(String name){
		_name = name;
	}
	
	/**
	 * Returns the event's name.
	 * @return String the name to return.
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Sets this event's date.
	 * @param date String date to set.
	 */
	public void setDate(Date date){
		_date = date;
	}
	
	/**
	 * Returns this event's date.
	 * @return String date to return.
	 */
	public Date getDate(){
		return _date;
	}
	
	public void setKitchen(Kitchen k){
		_kitchen = k;
	}

	public Kitchen getKitchen(){
		return _kitchen;
	}
	
	public String getMessages(){
		return _messages.toString();
	}
	
	public void addMessages(String message){
		_messages.append(message);
	}
	
	public String getTime() {
		return _time;
	}
	
	public void setTime(String time){
		_time = time;
	}
	
	@Override
	public String toString() {
		return "Event [_date=" + _date + ", _name=" + _name + ", _kitchen="
				+ _kitchen.getName() + "_shoppingList=" + _shoppingIng + "_recipes=" + _menu+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
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
		KitchenEvent other = (KitchenEvent) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}
	
}
