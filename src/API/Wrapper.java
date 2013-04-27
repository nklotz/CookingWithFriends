package API;

import java.io.IOException;
import java.util.List;

import UserInfo.Recipe;

/**
 * An interface for interacting with a recipe database API.
 */
public interface Wrapper {

	/**
	 * Returns a list of recipes given the search term.
	 * @param toFind
	 * @return List<Recipe> The list of recipes to return.
	 */
	public List<Recipe> recipes(String toFind);
	
	/**
	 * Given the format of the Big Oven API, will return a recipe.
	 * @param o The format of the Big Oven API Recipe.
	 * @return Recipe the newly converted recipe.
	 */
	public Recipe convertToRecipe(Object o);
	
	/**
	 * Given a list of recipes, we will return a list of needed ingredients.
	 * @param recipes The recipes 
	 * @return ArrayList<Recipe> The ingredients the user needs.
	 */
	public List<String> neededIngredients(List<Recipe> recipes);

	/**
	 * Returns a list of recipes given a string of comma separated (maybe?) ingredients.
	 * @return ArrayList<Recipe> The searched recipes.
	 * @throws IOException 
	 */
	public List<? extends Recipe> findRecipesWithIngredients(String query, List<String> ingredients, List<String> dislikes,
			List<String> dietRestrictions, List<String> allergies) throws IOException;

	/**
	 * Gets a recipe based on the recipe's ID.
	 * @param recipeID
	 * @return
	 * @throws IOException 
	 */
	public Recipe getRecipe(String recipeID) throws IOException;

	public List<String> getPossibleIngredients();
	
	public List<String> getPossibleDietaryRestrictions();
	
	public List<String> getPossibleAllergies();
	
}
