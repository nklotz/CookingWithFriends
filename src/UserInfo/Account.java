package UserInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author hacheson
 * This is the account class. It is what the home page will call to boot up. It has a user
 * but also has a list of recipes, ingredients, and shopping list.
 */
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Specific to user.
	private HashSet<KitchenName> _kitchens = new HashSet<KitchenName>();
	private String _address = "";
	private String _userId = "", _name = "";
	private Preferences _preferences = new Preferences();
	
	//Specific to account.
	private HashSet<Recipe> _recipes = new HashSet<Recipe>();
	private Set<Ingredient> _ingredients = new HashSet<Ingredient>();
	private Set<Ingredient> _shoppingList = new HashSet<Ingredient>();
	private HashMap<KitchenName, Invitation> _invitations = new HashMap<KitchenName, Invitation>() ;
	private Set<String> _dietRestrictions = new HashSet<String>();
	private Set<String> _allergies = new HashSet<String>();
	
	public Account(String userId, String name, String address, HashSet<Recipe> recipes, HashSet<Ingredient> ingredients, HashSet<Ingredient> shoppingList){
		_userId = userId;
		_recipes = recipes;
		_ingredients = ingredients;
		_name = name;
		_address = address;
		//TODO: Will be generated by the api.
		_shoppingList = shoppingList;

	}
	
	//Without the shopping list.
	public Account(String userId, String name, String address, HashSet<Recipe> recipes, HashSet<Ingredient> ingredients){
		_userId = userId;
		_recipes = recipes;
		_ingredients = ingredients;
		_name = name;
		_address = address;
		//TODO: Will be generated by the api.
	}
	
	public Account(String userId){
		_userId = userId;
	}
	
	/**
	 * Sets the user of this account.
	 * @param u The user id to set.
	 */
	public void setUserId(String u){
		_userId = u;
	}
	
	/**
	 * Returns the name of the account user
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * Sets the name of the account user
	 */
	public void setName(String name){
		_name = name;
	}
	
	/**
	 * Sets the list of ingredients in the account.
	 * @param ing
	 */
	public void setIngredients(Set<Ingredient> ing){
		_ingredients = ing;
	}
	
	public void setShoppingList(Set<Ingredient> SL){
		_shoppingList = SL;
	}
	
	public Set<Ingredient> getShoppingList(){
		return _shoppingList;
	}
	
	
	public void addRestriction(String restric){
		_dietRestrictions.add(restric);
	}
	
	public void removeRestriction(String restric){
		_dietRestrictions.remove(restric);
	}
	
	public Set<String> getDietaryRestrictions(){
		return _dietRestrictions;
	}
	
	public void addAllergy(String allerg){
		_allergies.add(allerg);
	}
	
	public void removeAllergy(String allerg){
		_allergies.remove(allerg);
	}
	
	public Set<String> getAllergies(){
		return _allergies;
	}
	
	/**
	 * Returns the list of ingredients in this account.
	 * @return HashSet<String> List of ingredients.
	 */
	public Set<Ingredient> getIngredients(){
		return _ingredients;
	}
	
	/**
	 * Sets the list of recipes in the account.
	 * @param r The list of recipes.
	 */
	public void setRecipes(HashSet<Recipe> r){
		_recipes = r;
	}
	
	/**
	 * Returns the list of recipes in this account.
	 * @return HashSet<String> List of recipes.
	 */
	public Set<Recipe> getRecipes(){
		return _recipes;
	}
	
	/**
	 * Returns the user's address.
	 * @return String user's address.
	 */
	public String getAddress(){
		return _address;
	}
	
	/**
	 * Returns the user name.
	 * @return String the user's name.
	 */
	public String getID(){
		return _userId;
	}
	
	
	/**
	 * Adds a kitchen to the user.
	 * @param k Kitchen the user's kitchen.
	 */
	public void addKitchen(KitchenName k){
		_kitchens.add(k);
	}
	
	/**
	 * Removes a kitchen from the user.
	 * @param k Kitchen kitchen to remove.
	 */
	public void removeKitchen(String kID){
		_kitchens.remove(kID);
	}
	
	/**
	 * Returns a list of kitchens to which the user belongs.
	 */
	public HashSet<KitchenName> getKitchens(){
		return _kitchens;
	}
	
	public void setKitchens(HashSet<KitchenName> kitchens){
		_kitchens = kitchens;
	}
	
	/**
	 * Sets the user's preferences.
	 * @param pref Preferences for the user to set.
	 */
	public void setPreferences(Preferences pref){
		_preferences = pref;
	}
	
	public void setAddress(String add){
		_address = add;
	}
	
	public HashMap<KitchenName, Invitation> getInvitions(){
		return _invitations;
	}
	
	public void addInvitation(Invitation invite){
		_invitations.put(invite.getKitchenID(), invite);
	}
	
	public void removeInvitation(String kitchenID){
		_invitations.remove(kitchenID);
	}
	
	public void addIngredient(Ingredient ingred){
		_ingredients.add(ingred);
	}
	
	public void addShoppingIngredient(Ingredient ingred){
		_shoppingList.add(ingred);
	}
	public void removeIngredient(Ingredient ingred){
		_ingredients.remove(ingred);
	}
	
	public void removeShoppingIngredient(Ingredient ingred){
		_shoppingList.remove(ingred);
	}
	
	

	@Override
	public String toString() {
		return "Account [_kitchens=" + _kitchens + ", _address=" + _address
				+ ", _userId=" + _userId + ", _preferences=" + _preferences
				+ ",  _recipes=" + _recipes
				+ ", _ingredients=" + _ingredients + ", _shoppingList="
				+ _shoppingList + "]";
	}
	
	public void removeRecipe(Recipe recipe){
		_recipes.remove(recipe);
	}
	
	public void addRecipe(Recipe recipe){
		_recipes.add(recipe);
	}
	

	
	
}
