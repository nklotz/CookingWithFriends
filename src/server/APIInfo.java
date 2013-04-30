package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import UserInfo.Ingredient;
import autocorrect.Engine;
import autocorrect.SuggestionEngine;

/**
 * Creates the autocorrect suggestion engines that are used to autocorrect the gui.
 * @author hacheson
 *
 */
public class APIInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Engine iEng_;
	private Engine rEng_;
	private Engine aEng_;
	
	public APIInfo(List<Ingredient> ingredients, List<String> restrictions, List<String> allergies){
		//Creates double arraylists to pass to suggestion engines.
		List<List<String>> iList = new ArrayList<List<String>>();
		List<String> ingredientsStrings = new ArrayList<String>();
		for(Ingredient i: ingredients){
			ingredientsStrings.add(i.getName());
		}
		iList.add(ingredientsStrings);
		List<List<String>> rList = new ArrayList<List<String>>();
		rList.add(restrictions);
		List<List<String>> aList = new ArrayList<List<String>>();
		aList.add(allergies);
		
		//Creates the suggestion engines for autocorrect.
		iEng_ = new SuggestionEngine(iList);
		rEng_ = new SuggestionEngine(rList);
		aEng_ = new SuggestionEngine(aList);
	}
	
	public Engine getRestrictionEngine(){
		return rEng_;
	}
	
	public Engine getIngredientEngine(){
		return iEng_;
	}
	
	public Engine getAllergyEngine(){
		return aEng_;
	}
	
	/**
	 * Wrapper for the suggestion engine to suggest restrictions.
	 * @param restriction
	 * @return
	 */
	public List<String> getRestrictionSuggestions(String restriction){
		rEng_.setText(restriction);
		return rEng_.suggest();
	}
	
	/**
	 * Wrapper for the suggestion engine to suggest ingredients.
	 * @param ingredient
	 * @return
	 */
	public List<String> getIngredientSuggestions(String ingredient){
		iEng_.setText(ingredient);
		return iEng_.suggest();
	}
	
	/**
	 * Wrapper for the suggestion engine to suggest allergies.
	 * @param allergy
	 * @return
	 */
	public List<String> getAllergySuggestions(String allergy){
		aEng_.setText(allergy);
		return aEng_.suggest();
	}
}
