/**
 * This is the suggestion class. It stores the word that is a suggestion,
 * the word before it, and the occurences of the bigram.
 * @author hacheson
 */

package autocorrect;

public class Suggestion {
	private int bigramOccurences_;
	private int unigramOccurences_;
	//The suggested word;
	private String word_;
	
	public Suggestion(String word, int occurences, int unigramOccurences){
		bigramOccurences_ = occurences;
		unigramOccurences_ = unigramOccurences;
		word_ = word;
	}
	
	String getWord(){
		return word_;
	}

	int getBigramOccurences(){
		return bigramOccurences_;
	}
	int getUnigramOccurences(){
		return unigramOccurences_;
	}
	
}
