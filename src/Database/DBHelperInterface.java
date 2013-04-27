package Database;

import UserInfo.Account;
import UserInfo.Kitchen;

public interface DBHelperInterface {
	
	/**
	 * Given the username, returns the account that was stored in the database.
	 * @param username 
	 * @return
	 */
	public Account getAccount(String username);
	
	/**
	 * Stores the account given the 
	 * @param a
	 */
	public void storeAccount(Account a);
	
	/**
	 * Returns a kitchen given the kitchen id.
	 * @param id 
	 * @return
	 */
	public Kitchen getKitchen(String id);
	
	/**
	 * Stores the kitchen in the database.
	 * @param k
	 */
	public void storeKitchen(Kitchen k);
	
	/**
	 * Check the username and password given the username database.
	 * @param username String username to check.
	 * @param password String password to check.
	 * @return Whether the password matches the username.
	 */
	public boolean checkUsernamePassword(String username, String password);
	
	/**
	 * Returns whether the username is already in the database.
	 * @param username
	 * @return
	 */
	public boolean uniqueUsername(String username);

}
