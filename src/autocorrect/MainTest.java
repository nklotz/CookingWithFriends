/**
 * Tests for the main method.
 */

package autocorrect;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
public class MainTest {

	/**
	 * Tests that the read lines method works and returns a list
	 * including duplicates of all words within the list.
	 */
	@Test
	public void testReadLines() {
		List<String> lines = null;
		try{
			lines = Main.readFile("/home/hacheson/course/cs032/autocorrect/testReadLines.txt");
		} catch(IOException e){
			
		}
		assertTrue(lines.get(0).equals("b"));
		assertTrue(lines.get(1).equals("the"));
		assertTrue(lines.get(2).equals("ca"));
		assertTrue(lines.get(3).equals("t"));
		assertTrue(lines.get(4).equals("dog"));
		assertTrue(lines.get(5).equals("abc"));
		assertTrue(lines.get(6).equals("hannah"));
		assertTrue(lines.get(7).equals("a"));
		assertTrue(lines.get(8).equals("b"));
	}

}
