package server;

/**
 * Tests for the autocorrect class in server. Tests to make sure that autocorrect will work the 
 * way that is appropriate for the gui ie not 
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import autocorrect.Main;
import autocorrect.SuggestionEngine;

public class AutocorrectTest {

	/**
	 * Tests the engine with a general example.
	 */
	@Test
	public void testRankGeneral(){
		List<List<String>> wordLists = new ArrayList<List<String>> ();
		SuggestionEngine eng = null;
		List<String> list = new ArrayList<String>();
		list.add("yellow squash");
		list.add("apples");
		list.add("carrots");
		list.add("apple pie");
		wordLists.add(list);
		
		//wordLists.add(Main.readFile("testFiles/rankerTest3.txt"));
		eng = new SuggestionEngine(wordLists, true, true, true, true, 0);
		eng.setText("yellow squash");
		assertTrue(eng.suggest().get(0).equals("yellow squash"));
		eng.setText("ap ap");
		assertTrue(eng.suggest().isEmpty());
		eng.setText("apple");
		assertTrue(eng.suggest().size()==2);
	}
}
