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

}
