package API;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import UserInfo.Ingredient;

/**
 * Returns the API's seacrh dictionaries.
 * 
 * @author jschear
 *
 */
public class YummlyWrapperDictionary implements WrapperDictionary {
	
	//Hard coded dietary restrictons and allergies
	private final String[] DIETARY_RESTRICTIONS = {"Vegan", "Lacto vegetarian", "Ovo vegetarian", 
			"Pescetarian", "Lacto-ovo vegetarian"};
	private final String[] ALLERGIES = {"Wheat-Free", "Gluten-Free", "Peanut-Free", 
			"Tree Nut-Free", "Dairy-Free", "Egg-Free", "Seafood-Free", "Sesame-Free", 
			"Soy-Free", "Sulfite-Free"};
	
	private Gson _gson;
	private List<Ingredient> _possibleIngredients;
	
	public YummlyWrapperDictionary() {
		_gson = new Gson();
		_possibleIngredients = new ArrayList<Ingredient>();
		
		//Read ingredient metadata
		try {
			for (String ingredientName : _gson.fromJson(new FileReader("ingredients"), String[].class)) {
				_possibleIngredients.add(new Ingredient(ingredientName));
			}
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.out.println("ERROR: Couldn't read files of search values.");
		}
	}
		
	@Override
	public List<Ingredient> getPossibleIngredients() {
		return _possibleIngredients;
	}	
	
	@Override
	public List<String> getPossibleDietaryRestrictions() {
		return Arrays.asList(DIETARY_RESTRICTIONS);
	}

	@Override
	public List<String> getPossibleAllergies() {
		return Arrays.asList(ALLERGIES);
	}

}
