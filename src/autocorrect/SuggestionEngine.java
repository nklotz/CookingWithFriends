/**
 * Suggestion Engine class, generates suggestions give its generator and ranker.
 */

package autocorrect;

import java.util.List;

public class SuggestionEngine implements Engine{
	private Generator g_;
	private Ranker r_;
	private WordStructure trie_;
	private boolean isSmart_;
	
	/**
	 * @Constructor
	 * @param words List of words total words used as suggestions.
	 * @param isLED If should use led led suggestions.
	 * @param isPrefix If should use prefix suggestions.
	 * @param isWhitespace If should use whitespace suggestions.
	 * @param isSmart If should use the smart ranking system.
	 * @param edits Number of edits for LED command.
	 */
	public SuggestionEngine(List<List<String>> words, boolean isLED, boolean isPrefix,
			boolean isWhitespace, boolean isSmart, int edits){
		trie_ = new Trie(words);
		g_ = new SuggestionGenerator(isLED, isPrefix, isWhitespace, edits, trie_);
		r_ = new SuggestionRanker(words);
		isSmart_ = isSmart;
	}
	
	public SuggestionEngine(List<List<String>> words){
		System.out.println("WORDS SIZE: " + words.size());
		System.out.println("WORDS: " + words);
		trie_ = new Trie(words);
		g_ = new SuggestionGenerator(true, true, false, 2, trie_);
		r_ = new SuggestionRanker(words);
	}
	
	/**
	 * Suggests a list of possible words.
	 * @return List of suggestions.
	 */
	public List<String> suggest(){
		List<String> g = g_.generate();
		return r_.rank(g, isSmart_);
	}
	
	/**
	 * Sets the text on both the generator and the ranker.
	 * @param text The new text.
	 */
	public void setText(String text){
		r_.setText(text);
		g_.setText(text);
	}
}
