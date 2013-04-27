/**
 * This is the smart string comparator. It takes in two strings, and compares them to the given 
 * string based on the euclidian distance based on the keyboard. This is only after the bigram occurences
 * were taken into account.
 */

package autocorrect;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;

public class SmartSuggestionComparator implements Comparator<Suggestion>{
	private String text_;
	private HashMap<Character, Point> keyBoardPoints_;

	
	/**
	 * Constructor for Comparator.
	 * @param text String text being compared.
	 */
	public SmartSuggestionComparator(String text){
		text_ = text;
		keyBoardPoints_ = makeHashMap();
	}
	
	@Override
	/**
	 * Compares the strings based on the keyboard distance from each letter.
	 */
	public int compare(Suggestion a, Suggestion b) {
		int aBigram = a.getBigramOccurences();
		int bBigram = b.getBigramOccurences();
		if(aBigram<bBigram){
			return 1;
		}
		else if(bBigram<aBigram){
			return -1;
		}
		//Bigrams are equal, now check unigrams.
		else{
			if(keyboardDistance(a.getWord())<keyboardDistance(b.getWord()))
				return -1;
			else if(keyboardDistance(b.getWord())<keyboardDistance(a.getWord()))
				return 1;
			else
				return a.getWord().compareTo(b.getWord());
		}
		
	}
	
	/**
	 * Calculates the euclidian distance of each word in each string of words.
	 * Finds the smaller word then calculates the distance for each character,
	 * then adds them.
	 * @param a String that is being compared.
	 * @return The euclidian distance from the point to the 
	 */
	public double keyboardDistance(String a){
		String smaller = text_;
		
		//Combines the string into itself.
		String[] arr = a.split(" ");
		String combined = "";
		if(arr.length>1){
			for(int i = 0; i<arr.length; i++){
				combined+=arr[i];
			}
		}
		else
			combined = a;
			
		double dist = 0;
		if(combined.length()<text_.length())
			smaller = combined;
		for(int i = 0; i < smaller.length(); i++){
			dist += keyBoardPoints_.get(text_.charAt(i)).distance(keyBoardPoints_.get(combined.charAt(i)));
		}
		return dist;
	}
	
	/**
	 * Makes a hashmap of all character to it's "point" on the keyboard.
	 * @return
	 */
	public HashMap<Character, Point> makeHashMap(){
		HashMap<Character, Point> map = new HashMap<Character, Point>();
		//First row: q-p
		map.put('q', new Point(0,0));
		map.put('w', new Point(1,0));
		map.put('e', new Point(2,0));
		map.put('r', new Point(3,0));
		map.put('t', new Point(4,0));
		map.put('y', new Point(5,0));
		map.put('u', new Point(6,0));
		map.put('i', new Point(7,0));
		map.put('o', new Point(8,0));
		map.put('p', new Point(9,0));
		
		//Second row: a-l
		map.put('a', new Point(0,1));
		map.put('s', new Point(1,1));
		map.put('d', new Point(2,1));
		map.put('f', new Point(3,1));
		map.put('g', new Point(4,1));
		map.put('h', new Point(5,1));
		map.put('j', new Point(6,1));
		map.put('k', new Point(7,1));
		map.put('l', new Point(8,1));
		
		//Third row: z-m
		map.put('z', new Point(0,2));
		map.put('x', new Point(1,2));
		map.put('c', new Point(2,2));
		map.put('v', new Point(3,2));
		map.put('b', new Point(4,2));
		map.put('n', new Point(5,2));
		map.put('m', new Point(6,2));
		return map;
	}
	
}