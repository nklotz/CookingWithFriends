package Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

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
	
	
	
	
}
