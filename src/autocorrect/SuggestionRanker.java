/**
 * SuggestionRanker class implements the ranker. Has methods to generate suggestions: including LED,
 * whitespace.
 */
package autocorrect;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SuggestionRanker implements Ranker, Serializable{
	private HashMap<String, Integer> unigrams_;
	private HashMap<String, HashMap<String, Integer>> bigrams_;
	private String text_;
	
	
	/**
	 * Constructor for the suggestion ranker.
	 * @param wordLists The list of lines of words in the corpus files.
	 * @throws IOException
	 */
	public SuggestionRanker(List<List<String>> wordLists){
		bigrams_ = makeBigrams(wordLists);
		unigrams_ = makeUnigrams(wordLists);
	}
	
	/**
	 * Sets the text to be ranked by.
	 * @param text
	 */
	public void setText(String text){
		text_ = text;
	}
	
	/**
	 * Ranks the suggestions according to the list of suggestions generated.
	 */
	public List<String> rank(List<String> toRank, boolean isSmart){
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		String[] textArr = text_.toLowerCase().split(" ");
		String prevWord = null;
		String currWord = null;
		String beforeWord = "";
		if(textArr.length!=0){
			//Find the string up to the word before it.
			for(int i = 0; i < textArr.length-1; i++){
				beforeWord += textArr[i] + " ";
			}
			beforeWord = beforeWord.trim();
			currWord = textArr[textArr.length-1];
		}
		if(textArr.length>=2)
			prevWord = textArr[textArr.length-2];
		
		//Now rank the list
		for(int i = 0; i < toRank.size(); i++){
			String bigramSecondWord = toRank.get(i);
			
			//Take the word to use the bigram with as
			if(toRank.get(i).split(" ").length!=1){
				bigramSecondWord = toRank.get(i).split(" ")[0];
			}
			
			//Find the bigram and unigram occurences.
			Integer bigramOccurences = 0;
			Integer unigramOccurences = unigrams_.get(bigramSecondWord);
			HashMap<String, Integer> bigramMap = bigrams_.get(bigramSecondWord);
			if(bigramMap != null)
				bigramOccurences = bigramMap.get(prevWord);
			
			if(bigramOccurences == null)
				bigramOccurences = 0;

			if(unigramOccurences == null)
				unigramOccurences = 0;
			Suggestion sugg = new Suggestion(toRank.get(i), bigramOccurences, unigramOccurences);
			suggestionList.add(sugg);
		}
		
		if(isSmart)
			Collections.sort(suggestionList, new SmartSuggestionComparator(currWord));
		else
			Collections.sort(suggestionList, new SuggestionComparator());
		ArrayList<String> stringSuggs = convertList(suggestionList);
		currWord = currWord.trim();

		if(currWord!=null){
			if(stringSuggs.contains(currWord)){
				stringSuggs.remove(currWord);
				stringSuggs.add(0, currWord);
			}	
		}
		return stringSuggs;
		//return appendWordBefore(stringSuggs, beforeWord);
	}
	
	/**
	 * Appends a word before it.
	 * @param words The list of words to append before to.
	 * @param before The word to append before all other words.
	 * @return The new array list.
	 */
	public ArrayList<String> appendWordBefore(ArrayList<String> words, String before){
		ArrayList<String> fullLines = new ArrayList<String>();
		for(int i = 0; i < words.size(); i++){
			if(!before.equals(""))
				fullLines.add(before + " " + words.get(i));
			else
				fullLines.add(words.get(i));
		}
		return fullLines;
			
	}
	
	/**
	 * Converts a list of suggestions to a list of strings.
	 * @param ArrayList The list of suggestions.
	 * @return The list of converted suggestions to strings.
	 */
	public ArrayList<String> convertList(ArrayList<Suggestion> suggestions){
		ArrayList<String> ret = new ArrayList<String>();
		for(Suggestion sugg: suggestions){
			ret.add(sugg.getWord());
			
		}
		return ret;
	}
	
	/**
	 * Given an array of lines from the file, make a bigram hash map with occurences.
	 * @param lineLists The lists of words in all files.
	 * @return The hashmap of bigrams.
	 */
	public HashMap<String, HashMap<String, Integer>> makeBigrams(List<List<String>> lineLists){
		//Loop through each line and remove punctuation.
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> bigramMap;
		for(List<String> lines: lineLists){
			for(int i = 1; i < lines.size(); i++){
				String prev = lines.get(i-1);
				String curr = lines.get(i);
				if(map.get(curr)==null){
					bigramMap = new HashMap<String, Integer>();
					bigramMap.put(prev, 1);
					map.put(curr, bigramMap);
				}
				else{
					bigramMap = map.get(curr);
					if(bigramMap.get(prev) == null)
						bigramMap.put(prev, 1);
					else
						bigramMap.put(prev, bigramMap.get(prev)+1);
				}
			}
		}
		return map;
	}
	
	/**
	 * Given an array of lines from the file, make a unigram hash map with occurences.
	 * @param lines
	 * @return The hashmap of unigrams.
	 */
	public HashMap<String, Integer> makeUnigrams(List<List<String>> lineLists){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for(List<String> lines: lineLists)	{
			for(int i = 0; i < lines.size(); i++){
				if(map.get(lines.get(i)) == null)
					map.put(lines.get(i), 1);
				else
					map.put(lines.get(i), map.get(lines.get(i)) + 1);
			}
		}	
		return map;
	}
}
