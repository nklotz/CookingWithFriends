/**
 * Tests for the trie class.
 * @author hacheson
 */

package autocorrect;


import org.junit.Test;
import static org.junit.Assert.*;


public class TrieTest {

	@Test
	/**
	 * Adds one word to the trie.
	 */
	public void testOneWordTrie() {
		String word = "cat";
		Trie trie = new Trie();
		trie.add(word);
		assertTrue(trie.getRoot().getChildren().get('c') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a').getWord().equals("ca"));
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('t').getWord().equals("cat"));
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('t').getTerminal());
		assertTrue(trie.getRoot().getChildren().get('c').getParent().equals(trie.getRoot()));
	}
	
	@Test
	/**
	 * Adds two words to the trie.
	 */
	public void testTwoWordTrie() {
		String word = "cat";
		String word2 = "car";
		Trie trie = new Trie();
		trie.add(word);
		trie.add(word2);
		assertTrue(trie.getRoot().getChildren().get('c') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().size() == 2);
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('t').getWord().equals(word));
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a')
				.getChildren().get('r').getWord().equals(word2));
		assertTrue(trie.getRoot().getChildren().get('c').getChildren().get('a').getParent().
				equals(trie.getRoot().getChildren().get('c')));
	}
	
	@Test
	/**
	 * Adds a word to a trie that is terminated not on a leaf node. Tests for termination after
	 * the node has already been added.
	 */
	public void testMiddleTerminatedTrie() {
		String word = "ate";
		String word2 = "at";
		Trie trie = new Trie();
		trie.add(word);
		trie.add(word2);
		assertTrue(trie.getRoot().getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().size() == 1);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e').getWord().equals(word));
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getWord().equals(word2));
	}
	
	/**
	 * Adds a word to a trie that is terminated not on a leaf node. Tests for termination when
	 * the smaller node is added first.
	 */
	@Test
	public void testMiddleTerminatedTrie2() {
		String word = "ate";
		String word2 = "at";
		Trie trie = new Trie();
		trie.add(word2);
		trie.add(word);
		assertTrue(trie.getRoot().getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().size() == 1);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e').getWord().equals(word));
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getWord().equals(word2));
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getTerminal());
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t').getParent()
				.equals(trie.getRoot().getChildren().get('a')));
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e').getParent().equals(trie.getRoot().getChildren().get('a').
						getChildren().get('t')));
		assertTrue(trie.getRoot().getChildren().get('a').getChar() == 'a');
		
	}
	
	/**
	 * Adds 5 words to a trie.
	 */
	@Test
	public void testFiveWordTrie() {
		String word = "ate";
		String word2 = "at";
		String word3 = "car";
		String word4 = "bat";
		String word5 = "a";
		Trie trie = new Trie();
		trie.add(word);
		trie.add(word2);
		trie.add(word3);
		trie.add(word4);
		trie.add(word5);
		assertTrue(trie.getRoot().getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('b') != null);
		assertTrue(trie.getRoot().getChildren().get('c') != null);
		assertTrue(trie.getRoot().getChildren().size() == 3);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e') != null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('f') == null);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().size() == 1);
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getChildren().get('e').getWord().equals(word));
		assertTrue(trie.getRoot().getChildren().get('a').getChildren().get('t')
				.getWord().equals(word2));
		assertTrue(trie.getRoot().getChildren().get('a').getWord().equals(word5));
		assertTrue(trie.getRoot().getChildren().get('b').getChildren().get('a')
				.getChildren().get('t').getWord().equals(word4));
		assertTrue(trie.getRoot().getChildren().get('b').getChildren().get('a')
				.getChildren().get('t').getChar() == 't');
	}
	
	/**
	 * Adds 8 words to a trie.
	 */
	@Test
	public void testEightWordTrie() {
		String word = "a";
		String word2 = "to";
		String word3 = "tea";
		String word4 = "ted";
		String word5 = "ten";
		String word6 = "i";
		String word7 = "in";
		String word8 = "inn";
		Trie trie = new Trie();
		trie.add(word);
		trie.add(word2);
		trie.add(word3);
		trie.add(word4);
		trie.add(word5);
		trie.add(word6);
		trie.add(word7);
		trie.add(word8);
		assertTrue(trie.getRoot().getChildren().get('a') != null);
		assertTrue(trie.getRoot().getChildren().get('t') != null);
		assertTrue(trie.getRoot().getChildren().get('i') != null);
		assertTrue(trie.getRoot().getChildren().size() == 3);
		assertTrue(trie.getRoot().getChildren().get('t').getChildren().get('o') != null);
		assertTrue(trie.getRoot().getChildren().get('t').getChildren().get('o').getWord().equals(word2));
		assertTrue(trie.getRoot().getChildren().get('i').getWord().equals(word6));
		assertTrue(trie.getRoot().getChildren().get('i').getChildren().get('n')
				.getWord().equals(word7));
		assertTrue(trie.getRoot().getChildren().get('i').getChildren().get('n')
				.getChildren().get('n').getWord().equals(word8));
		assertTrue(trie.getRoot().getChildren().get('t').getChildren().get('e')
				.getChildren().size() == 3);
	}
	
	/*********************************************************************
	 * Tests for find method.
	 *********************************************************************/
	@Test
	/**
	 * Adds one word to the trie.
	 */
	public void testFindOneWord() {
		String word = "cat";
		Trie trie = new Trie();
		trie.add(word);
		TrieNode found = trie.find(word);
		assertTrue(found.getWord().equals(word));
	}
	
	/**
	 * Adds one letter to the trie and finds it.
	 */
	@Test
	public void testFindOneLetter() {
		String word = "c";
		Trie trie = new Trie();
		trie.add(word);
		TrieNode found = trie.find(word);
		assertTrue(found.getWord().equals(word));
	}
	
	/**
	 * Adds three words to the try and finds them and prefixes.
	 */
	@Test
	public void testFindThreeWords() {
		String word = "cat";
		String word2 = "cars";
		String word3 = "zzz";
		Trie trie = new Trie();
		trie.add(word);
		trie.add(word2);
		trie.add(word3);
		assertTrue(trie.find(word).getWord().equals(word));
		assertTrue(trie.find(word2).getWord().equals(word2));
		assertTrue(trie.find(word3).getWord().equals(word3));
		assertTrue(trie.find("ca").getWord().equals("ca"));
		assertTrue(trie.find("zz").getWord().equals("zz"));
	}
	
	/**
	 * Adds three words to the try and finds them and prefixes.
	 */
	@Test
	public void testFindNonexistantWord() {
		String word = "cat";
		Trie trie = new Trie();
		trie.add(word);
		assertTrue(trie.find("zzz")== null);
	}
	
	/**
	 * Tests that if the word is longer than anything in the trie.
	 */
	@Test
	public void testFindLongerWord() {
		String word = "cat";
		Trie trie = new Trie();
		trie.add(word);
		TrieNode found = trie.find("cats");
		assertTrue(found == null);
	}
}
