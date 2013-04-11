/**
 * 
 */
package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import UserInfo.Account;
import UserInfo.Recipe;
import UserInfo.User;

/**
 * @author hacheson
 * Fake database storage system.
 */
public class Database {

	HashMap<String, String> userToPassword_;
	HashMap<String, Account> nameToAccount_;
	//HashMap<String, User> nameToUser_; 
	Account _acc;
	Recipe _rec;
	HashSet<Recipe> _recipes;
	public Database(){
		userToPassword_ = new HashMap<String, String>();
		userToPassword_.put("Hannah", "Hannah");
		userToPassword_.put("Natalie", "Natalie");
		userToPassword_.put("Jon", "Jon");
		userToPassword_.put("Eddie", "Beyonce");
		_acc = new Account(new User("Hannah", userToPassword_.get("Hannah")));
		HashSet<String> i = new HashSet<String>();
		i.add("apples");
		i.add("pears");
		i.add("articokes");
		_acc.setIngredients(i);
		
		String[] arr = {"chicken", "bread", "cheese"};
		
		ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(arr));
		_rec = new Recipe("Chicken Kiev", "111", "Place stuff in a bowl and mix it.", ingredients);
		_recipes = new HashSet<Recipe>();
		_recipes.add(_rec);
		_acc.setRecipes(_recipes);
		
		nameToAccount_.put("Hannah", _acc);
	}
	
	/**
	 * Returns whether a password is valid given a username.
	 * @param user User to check.
	 * @return String user to check.
	 */
	public String getPasswordFromUser(String user){
		System.out.println( "returning " + userToPassword_.get(user));
		return userToPassword_.get(user);
	}
	
	
	public void addUser(String user, String password){
		userToPassword_.put(user, password);
	}
	
	public void removeUser(String user, String password){
		userToPassword_.remove(user);
	}
	
	public Account getAccountFromUser(String name){
		return nameToAccount_.get(name);
	}
	
}
