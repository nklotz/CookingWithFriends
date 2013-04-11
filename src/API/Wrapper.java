package API;

import java.util.ArrayList;

import UserInfo.Recipe;

/**
 * An interface for the Big Oven wrapper.
 * @author hacheson
 *
 */
public interface Wrapper {

	/**
	 * Returns a list of recipes given the search term.
	 * @param toFind
	 * @return ArrayList<Recipe> The list of recipes to return.
	 */
	public ArrayList<Recipe> recipes(String toFind);
	
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
	public ArrayList<String> neededIngredients(ArrayList<Recipe> recipes);

	/**
	 * Returns a list of recipes given a string of comma separated (maybe?) ingredients.
	 * @return ArrayList<Recipe> The searched recipes.
	 */
	public ArrayList<Recipe> findRecipesWithIngredients(ArrayList<String> ingredients);
	
}
