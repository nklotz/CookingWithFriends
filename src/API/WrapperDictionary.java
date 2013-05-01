package API;

import java.util.List;

import UserInfo.Ingredient;

public interface WrapperDictionary {
	
	/**
	 * Gets the API's list of search values for ingredients.
	 * @return
	 */
	public List<Ingredient> getPossibleIngredients();
	
	/**
	 * Gets the API's list of search values for dietary restrictions.
	 * @return
	 */
	public List<String> getPossibleDietaryRestrictions();
	
	/**
	 * Gets the API's list of search values for allergies.
	 * @return
	 */
	public List<String> getPossibleAllergies();

}
