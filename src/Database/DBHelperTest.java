package Database;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import UserInfo.Kitchen;

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
			assertTrue(false);
		}
	}

}
