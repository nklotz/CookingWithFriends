package Test;

import java.util.List;

import UserInfo.Ingredient;
import UserInfo.Recipe;

public class MockRecipe implements Recipe {
	
	private String _name;
	List<Ingredient> _ingredients;

	public MockRecipe(String name) {
		_name = name;
	}
	
	public MockRecipe(String name, List<Ingredient> ingredients) {
		_name = name;
		_ingredients = ingredients;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<Ingredient> getIngredients() {
		// TODO Auto-generated method stub
		return _ingredients;
	}

	@Override
	public String getYield() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNumberOfServings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

}
