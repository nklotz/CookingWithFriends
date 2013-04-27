package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Database.DBHelper;

public class DBHelperTest {

	@Test
	public void testCreateKitchenId() {
		DBHelper help = new DBHelper();
		String s = help.createKitchenId();
		assertTrue(s.startsWith("/k/"));
		assertTrue(s.length()>3 && s.length()<=9);
		try{
			Integer.parseInt(s.substring(3, s.length()));
		} catch(NumberFormatException e){
			fail();
		}
	}
	
	/**
	 * Tests the password check method.
	 */
	@Test
	public void testPasswordCheck(){
		DBHelper help = new DBHelper();
		help.storeUsernamePassword("Hannahs", "platypus");
		assertTrue(help.checkUsernamePassword("Hannahs", "platypus"));
		assertFalse(help.checkUsernamePassword("Hannahs", "asdfdsaf"));
	}
	
	/**
	 * Tests to make sure that if the user already exists in the database, that you can
	 * update the password.
	 */
	@Test
	public void testUpdatePassword(){
		DBHelper help = new DBHelper();
		help.storeUsernamePassword("Hannah", "platypu");
		assertTrue(help.checkUsernamePassword("Hannah", "platypu"));
	}
	
	
}
