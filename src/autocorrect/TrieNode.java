/**
 * This is the TrieNode class. It represents a node in the trie.
 * A trie node represents a character and has a hashmap with all its children.
 * @author hacheson
 */

package autocorrect;

import java.util.HashMap;

public class TrieNode{
	private boolean isTerminal_;
	private String word_;
	private HashMap<Character, TrieNode> children_;
	private TrieNode parent_;
	private char c_;
	
	/**
	 * Constructor for TrieNode class.
	 * @param c The character representation of the node.
	 * @param isTerminal Whether the node is terminal or not.
	 */
	public TrieNode(char c, boolean isTerminal){
		c_ = c;
		children_ = new HashMap<Character, TrieNode>();
		isTerminal_ = isTerminal;
	}
	
	/**
	 * Returns the parent of this node.
	 * @return parent of the node.
	 */
	TrieNode getParent(){
		return parent_;
	}
	
	void setParent(TrieNode p){
		parent_ = p;
	}
	
	/**
	 * Returns the hashmap containing the lists children.
	 * @return hashmap of children.
	 */
	public HashMap<Character, TrieNode> getChildren(){
		return children_;
	}
	
	/**
	 * Sets the word in the node.
	 * @param word
	 */
	void setWord(String word){
		word_ = word;
	}
	
	/**
	 * Gets the word that the node is storing.
	 * @return
	 */
	String getWord(){
		return word_;
	}
	
	/**
	 * Sets if that node is terminal.
	 * @param terminal
	 */
	public void setTerminal(boolean terminal){
		isTerminal_ = terminal;
	}
	
	/**
	 * Returns if that node is terminal.
	 * @return If this node is terminal.
	 */
	public boolean getTerminal(){
		return isTerminal_;
	}
	
	/**
	 * Returns the character associated with each node.
	 * @return char c character at each node.
	 */
	char getChar(){
		return c_;
	}
	
	@Override
	public String toString(){
		return "NODE: " + children_ + "WORD: " + word_;
	}
}
