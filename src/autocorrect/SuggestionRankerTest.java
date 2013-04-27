/**
 * Tests for the SuggestionRanker class. Description for each above methods.
 * @author hacheson
 */

package autocorrect;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class SuggestionRankerTest {
	@Test
	/**
	 * Tests the make bigrams method.
	 */
	public void testMakeBigrams() {
		List<List<String>> wordLists = new ArrayList<List<String>>();
		HashMap<String, HashMap<String, Integer>> map = null;
		SuggestionRanker ranker = null;
		try{
			wordLists.add(Main.readFile("testFiles/bigramTest.txt"));
			ranker = new SuggestionRanker(wordLists);
		}
		catch(IOException e){
			
		}
		
		map = ranker.makeBigrams(wordLists);
		
		assertTrue(map.get("cat").get("dy") == 1);
		assertTrue(map.get("dy").get("cd") == 2);
		assertTrue(map.get("ba").get("as") == 1);
	}
	
	
	@Test
	/**
	 * Tests the make bigrams method using two files, makes sure that the
	 * last word on the first file and first word on the last file are not considered
	 * as a bigram.
	 */
	public void testMakeBigramsTwoFiles() {
		List<List<String>> wordLists = new ArrayList<List<String>>();
		HashMap<String, HashMap<String, Integer>> map = null;
		SuggestionRanker ranker = null;
		try{
			wordLists.add(Main.readFile("testFiles/bigramTest.txt"));
			wordLists.add(Main.readFile("testFiles/bigramTest2.txt"));
			ranker = new SuggestionRanker(wordLists);
		}
		catch(IOException e){
			
		}
		
		map = ranker.makeBigrams(wordLists);
		
		assertTrue(map.get("cat").get("dy") == 1);
		assertTrue(map.get("dy").get("cd") == 2);
		assertTrue(map.get("ba").get("as") == 1);
		assertTrue(map.get("ab") == null);
	}
	
	@Test
	/**
	 * Tests the makeUnigrams method.
	 */
	public void testMakeUnigrams() {
		List<List<String>> wordLists = new ArrayList<List<String>>();
		HashMap<String, Integer> map = null;
		SuggestionRanker ranker = null;
		try{
			wordLists.add(Main.readFile("testFiles/bigramTest.txt"));
			wordLists.add(Main.readFile("testFiles/bigramTest2.txt"));
			ranker = new SuggestionRanker(wordLists);
		}
		catch(IOException e){
			
		}
		
		map = ranker.makeUnigrams(wordLists);
		
		assertTrue(map.get("ab") == 1);
		assertTrue(map.get("dy") == 2);
		assertTrue(map.get("baa") == null);
	}
	
	/**
	 * Test for the rank method for the ranker when there is whitespace.
	 * Checks to see if the first word is the word that is compared.
	 */
	@Test
	public void testRankWhitespaceOneSuggestion(){
		List<List<String>> wordLists = new ArrayList<List<String>>();
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("the");
		fileList.add("mouse");
		fileList.add("cheese");
		fileList.add("the");
		fileList.add("mouse");
		fileList.add("the");
		fileList.add("cheese");
		fileList.add("the");
		fileList.add("the");
		wordLists.add(fileList);
		SuggestionRanker ranker = new SuggestionRanker(wordLists);
		ArrayList<String> rank = new ArrayList<String>();
		rank.add("mouse cheese");
		ranker.setText("the mousecheese");
		assertTrue(ranker.rank(rank, false).get(0).equals("the mouse cheese"));
	}
	
	/**
	 * Test for the rank method for the ranker when there is a tie
	 * in bigram comparison.
	 */
	@Test
	public void testRankBigramUnigram(){
		List<List<String>> wordLists = new ArrayList<List<String>>();
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("the");
		fileList.add("a");
		fileList.add("the");
		fileList.add("b");
		fileList.add("a");
		fileList.add("a");
		fileList.add("a");
		fileList.add("b");
		wordLists.add(fileList);
		SuggestionRanker ranker = new SuggestionRanker(wordLists);
		ArrayList<String> rank = new ArrayList<String>();
		rank.add("a");
		rank.add("b");
		ranker.setText("the ab");
		assertTrue(ranker.rank(rank, false).get(0).equals("the a"));
	}
	
	/**
	 * Test for the rank method for the ranker when there is a tie
	 * in bigram and unigram comparison. Should be ranked alphabetically.
	 */
	@Test
	public void testRankAlphabetical(){
		List<List<String>> wordLists = new ArrayList<List<String>>();
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("the");
		fileList.add("a");
		fileList.add("the");
		fileList.add("b");
		wordLists.add(fileList);
		SuggestionRanker ranker = new SuggestionRanker(wordLists);
		ArrayList<String> rank = new ArrayList<String>();
		rank.add("a");
		rank.add("b");
		ranker.setText("the ab");
		assertTrue(ranker.rank(rank, false).get(0).equals("the a"));
		assertTrue(ranker.rank(rank, false).get(1).equals("the b"));
	}
	
	/**
	 * Test for the rank method for the ranker when using the smart matching.
	 */
	@Test
	public void testRankSmart(){
		List<List<String>> wordLists = new ArrayList<List<String>>();
		//Simulates the list of words the file reads in.
		ArrayList<String> fileList = new ArrayList<String>();
		fileList.add("the");
		fileList.add("a");
		fileList.add("the");
		fileList.add("q");
		wordLists.add(fileList);
		SuggestionRanker ranker = new SuggestionRanker(wordLists);
		//List of suggestions that need to be ranked.
		ArrayList<String> rank = new ArrayList<String>();
		rank.add("a");
		rank.add("q");
		ranker.setText("the n");
		assertTrue(ranker.rank(rank, false).get(0).equals("the a"));
		assertTrue(ranker.rank(rank, false).get(1).equals("the q"));
	}
	
	
}
