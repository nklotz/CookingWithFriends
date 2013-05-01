/**
 * SuggestionComparator class. Ranks suggestions based on bigram and unigram
 * frequencies.
 */
package autocorrect;

import java.util.Comparator;

public class SuggestionComparator implements Comparator<Suggestion> {
	
	/**
	 * Compare method override to compare two suggestions.
	 * First based on bigram importances, then on unigram importances, then
	 * alphabetically.
	 */
	public int compare(Suggestion a, Suggestion b){
		/*int aBigram = a.getBigramOccurences();
		int bBigram = b.getBigramOccurences();
		if(aBigram<bBigram){
			return 1;
		}
		else if(bBigram<aBigram){
			return -1;
		}
		//Bigrams are equal, now check unigrams.
		else{
			int aUni = a.getUnigramOccurences();
			int bUni = b.getUnigramOccurences();
			if(aUni<bUni){
				return 1;
			}
			else if(bUni<aUni){
				return -1;
			}
			//Rank alphabeticaly
			else{
				return a.getWord().compareTo(b.getWord());
			}*/
			return a.getWord().compareTo(b.getWord());
		
	}
	
	
}
