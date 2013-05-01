package API;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import UserInfo.Ingredient;
import UserInfo.Recipe;

/**
 * An interface for interacting with a recipe database API.
 */
public interface Wrapper extends Serializable{
	
	/**
	 * Returns a list of recipes given a list of ingredients, dislikes, dietary restrictions, and allergies.
	 * @return ArrayList<Recipe> The searched recipes.
	 * @throws IOException 
	 */
	public List<? extends Recipe> searchRecipes(String query, List<String> ingredients, List<String> dislikes,
			List<String> dietRestrictions, List<String> allergies) throws IOException;

	/**
	 * Gets a recipe based on the recipe's ID.
	 * @param recipeID
	 * @return
	 * @throws IOException 
	 */
	public Recipe getRecipe(String recipeID) throws IOException;

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
