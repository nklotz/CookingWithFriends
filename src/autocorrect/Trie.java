package autocorrect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trie implements WordStructure, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrieNode root_;
	
	/**
	 * Constructor for the trie.
	 * @param root The root of the trie node.
	 */
	public Trie(){
		root_ = new TrieNode(' ', false);
	}
	
	/**
	 * Constructor Takes in a list of words to add to the trie.
	 * @param lists List of word lists to add to the trie.
	 */
	public Trie(List<List<String>> lists){
		root_ = new TrieNode(' ', false);
		addLists(lists);
	}
	
	/**
	 * Adds a list of words to the trie.
	 * @param words List of words to add to the trie.
	 */
	public void add(List<String> words){
		for(String word: words){
			addNode(word, root_, 0);
		}
	}
	
	/**
	 * Adds a list of list of words to the trie.
	 * @param words List of words to add to the trie.
	 */
	public void addLists(List<List<String>> lists){
		for(List<String> words: lists){
			for(String word: words){
				addNode(word, root_, 0);
			}
		}
	}
	
	/**
	 * A wrapper for the addNode class. Calls addNode with the trie's root.
	 * @param word The word to be added to the trie.
	 */
	public void add(String word){
		addNode(word, root_, 0);
	}
	
	/**
	 * Adds a node into the trie.
	 * @param word The word to be added
	 * @param curr The current node tranversing.
	 * @param index The index into the word that is being traversed.
	 */
	public void addNode(String word, TrieNode curr, int index){
		if(word.length() == 0)
			return;
		char c = word.charAt(index);
		TrieNode nextNode = curr.getChildren().get(c);
		if(nextNode == null){
			if(index != word.length()-1){
				TrieNode node = new TrieNode(c, false);
				curr.getChildren().put(c, node);
				node.setWord(word.substring(0, index+1));
				node.setParent(curr);
				addNode(word, node, index+1);
			}
			else{
				TrieNode node = new TrieNode(c, true);
				node.setWord(word);
				curr.getChildren().put(c, node);
				node.setParent(curr);
			}
		}
		//Does have children.
		else{
			if(index != word.length()-1){
				addNode(word, nextNode, index+1);
			}
			else{
				nextNode.setTerminal(true);
				nextNode.setWord(word);
			}
		}
	}
	
	/**
	 * A wrapper for the find node class.
	 * @param word The word to find in the trie
	 * @return
	 */
	public TrieNode find(String word){
		return findNode(word, root_, 0);
	}
	/**
	 * Finds a node given a word, recursive call.
	 * @param word The word of the word to find.
	 * @return The node with that given word.
	 */
	public TrieNode findNode(String word, TrieNode curr, int index){
		char c = word.charAt(index);
		TrieNode nextNode = curr.getChildren().get(c);
		
		if(nextNode == null){
			return null;
		}
		//Found word.
		if(index == word.length()-1){
			return nextNode;
		}
		if(nextNode!=null){
			return findNode(word, nextNode, index+1);
		}
		//Will never actually get here.
		return curr;
	}
	
	/**
	 * Returns the root of the trie.
	 * @return Root node of trie.
	 */
	public TrieNode getRoot(){
		return root_;
	}
	
	@Override
	public String toString(){
		return printTree(root_);
	}
	
	/**
	 * Prints the trie recursively.
	 * @param curr The current node of the trie.
	 * @return String representation of the tree.
	 */
	public String printTree(TrieNode curr){
		String output = "";
		output += curr.toString();
		return output;
	}
}
