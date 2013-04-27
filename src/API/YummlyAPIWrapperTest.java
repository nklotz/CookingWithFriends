package API;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import UserInfo.Recipe;

public class YummlyAPIWrapperTest {
	
	private Wrapper _yummly;

	@Before
	public void setUp() throws Exception {
		_yummly = new YummlyAPIWrapper();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void searchTest() throws IOException {
		List<String> ingredients = new ArrayList<>();
		ingredients.add("cheese");
		ingredients.add("potatoes");
		
		List<String> dislikes = new ArrayList<>();
		dislikes.add("broccoli");
		dislikes.add("green peppers");
		
		List<String> dietRestrictions = new ArrayList<>();
		dietRestrictions.add("vegetarian");
		
		List<String> allergies = new ArrayList<>();
		allergies.add("peanut");
		
		List<? extends Recipe> recipes = _yummly.findRecipesWithIngredients("soup", ingredients, dislikes, dietRestrictions, allergies);
		
		for (Recipe recipe : recipes) {
			System.out.println(recipe);
		}
	}
	
	@Test
	public void recipeTest() throws IOException {
		String recipeID = "Creamy-Artichoke-Soup-Food-Network";
		Recipe recipe = _yummly.getRecipe(recipeID);
		System.out.println("Got recipe: " + recipe);
		System.out.println("Instructions:\n" + recipe.getInstructions());
	}

}
