/**
 * Word Structure interface. Implemented by the trie and other data structes that 
 * implement the word structure.
 */

package autocorrect;

/**
 * @author hacheson
 * The interface for a containing word structures. My trie implements this interface.
 * If I had a space saving trie, the space saving trie would also implement this interface.
 */
public interface WordStructure {

	/**
	 * Finds a node give that specific text.
	 * @param text Text trying to find.
	 * @return Object: The node in the tree that is being searched for.
	 */
	public TrieNode find(String text);
	
	/**
	 * Adds a word to the word structure.
	 * @param word String to be added.
	 */
	public void add(String word);
	
	/**
	 * Returns the root of that given structure.
	 * @return The trie node that should be returned.
	 */
	TrieNode getRoot();
		

}
