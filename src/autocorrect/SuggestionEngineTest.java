/**
 * Tests for the suggestion engine. Tests for the rank method are also in this class because
 * it is dependant on the suggestion generator as well.
 */

package autocorrect;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SuggestionEngineTest {

	/**
	 * Test for the rank method for the ranker when there is whitespace.
	 * Checks to see if the first word is the word that is compared.
	 */
	@Test
	public void testRankWhitespace(){
		List<List<String>> wordLists = new ArrayList<List<String>> ();
		SuggestionEngine eng = null;
		try{
			wordLists.add(Main.readFile("testFiles/testRanker.txt"));
			eng = new SuggestionEngine(wordLists, false, false, true, false, 0);
		}
		catch(IOException e){
			
		}
		eng.setText("the mousecheese");
		assertTrue(eng.suggest().get(0).equals("the mouse cheese"));
	}
	
	/**
	 * Tests the engine with a general example.
	 */
	@Test
	public void testRankGeneral(){
		List<List<String>> wordLists = new ArrayList<List<String>> ();
		SuggestionEngine eng = null;
		try{
			wordLists.add(Main.readFile("testFiles/rankerTest3.txt"));
			eng = new SuggestionEngine(wordLists, true, true, true, true, 0);
		}
		catch(IOException e){
			
		}
		eng.setText("i first saw y");
		assertTrue(eng.suggest().get(0).equals("i first saw you"));
		assertTrue(eng.suggest().get(1).equals("i first saw young"));
		assertTrue(eng.suggest().get(2).equals("i first saw your"));
	}

}
