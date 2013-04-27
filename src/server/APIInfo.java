package server;

import java.util.ArrayList;
import java.util.List;

import autocorrect.Engine;
import autocorrect.SuggestionEngine;

/**
 * Creates the autocorrect suggestion engines that are used to autocorrect the gui.
 * @author hacheson
 *
 */
public class APIInfo {

	
	private Engine iEng_;
	private Engine rEng_;
	private Engine aEng_;
	
	public APIInfo(List<String> ingredients, List<String> restrictions, List<String> allergies){
		//Creates double arraylists to pass to suggestion engines.
		List<List<String>> iList = new ArrayList<List<String>>();
		iList.add(ingredients);
		List<List<String>> rList = new ArrayList<List<String>>();
		iList.add(restrictions);
		List<List<String>> aList = new ArrayList<List<String>>();
		iList.add(allergies);
		System.out.println(iList);
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
}
