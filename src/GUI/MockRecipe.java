package GUI;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import UserInfo.Ingredient;
import UserInfo.Recipe;

public class MockRecipe implements Recipe, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _name;

	public MockRecipe(String name) {
		_name = name;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<Ingredient> getIngredients() {
		// TODO Auto-generated method stub
		return null;
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
	public String getID() {
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

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Ingredient> getIngredientDifference(Set<Ingredient> fridge) {
		// TODO Auto-generated method stub
		return null;
	}

}
