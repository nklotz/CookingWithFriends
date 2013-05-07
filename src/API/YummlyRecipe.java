package API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import UserInfo.Ingredient;
import UserInfo.Nameable;
import UserInfo.Recipe;
/**
 * Recipe stored by the user.
 * @author jschear
 *
 */
public class YummlyRecipe implements Recipe, Nameable {

	private static final long serialVersionUID = 1L;
	
	//Indicated whether the recipe info has been read in completely from the API.
	public boolean isFullRecipe;
	
	//Fields from Yummly search query
	private String id;
	private String recipeName;
	private double rating;
	private String[] smallImageUrls;
	private String sourceDisplayName;
	private int totalTimeInSeconds;
	private String[] ingredients;
	private Map<String, String[]> attributes;
	private Map<String, Double> flavors;
	
	//Additional fields from Yummly recipe query
	private Map<String, String> attribution;
	private String[] ingredientLines;
	private ImageUrls[] images;
	private String name;
	private String yield;
	private String totalTime;
	private String numberOfServings;
	private Map<String, String> source;
	
	private List<Ingredient> _ingredients;
	private String _time;
	
	
	public YummlyRecipe() {
		_ingredients = new ArrayList<>();
	}


	@Override
	public String toString() {
		return "Recipe [id=" + id + ", recipeName=" + recipeName
				+ ", ingredients=" + Arrays.toString(ingredients) + "]";
	}


	@Override
	public String getName() {
		return (recipeName == null ? name : recipeName);
	}


	@Override
	public List<Ingredient> getIngredients() {
		if (_ingredients.isEmpty()) {
			for (String ingredient : ingredients) {
				_ingredients.add(new Ingredient(ingredient));
			}
		}
		return _ingredients;
	}
	
	@Override
	public List<String> getIngredientStrings() {
		return Arrays.asList(ingredientLines);
	}


	@Override
	public String getYield() {
		return yield;
	}

	@Override
	public String getNumberOfServings() {
		return numberOfServings;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getTime() {
		if (totalTime == null) {
			if (totalTimeInSeconds != 0)
				return Integer.toString(totalTimeInSeconds);
			return null;
		} 
		else {
			try {
				Integer timeInSeconds = Integer.parseInt(totalTime);
				_time = Integer.toString(timeInSeconds / 60) + " minutes";
			}
			catch (NumberFormatException ex) {
				_time = totalTime;
			}
			return _time;
		}
	}
	
	@Override
	public boolean hasImage() {
		return (images.length > 0);
	}
	
	@Override
	public boolean hasThumbnail() {
		return (smallImageUrls.length > 0);
	}
	
	@Override
	public String getThumbnailUrl() {
		if (smallImageUrls != null) {
			return smallImageUrls[0];
		}
		return "";
	}
	
	@Override
	public String getImageUrl() {
		if (images != null) {
			if (images[0].hostedLargeUrl != null) 
				return images[0].hostedLargeUrl;
			if (images[0].hostedMediumUrl != null)
				return images[0].hostedMediumUrl;
			if (images[0].hostedSmallUrl != null)
				return images[0].hostedSmallUrl;
		}
		return "";
	}
	
	@Override 
	public String getSourceUrl() {
		if (source.containsKey("sourceRecipeUrl")) {
			return source.get("sourceRecipeUrl");
		}
		return null;
	}

	@Override
	public Set<Ingredient> getIngredientDifference(Set<Ingredient> fridge) {
		Set<Ingredient> difference = new HashSet<Ingredient>();
		
		for(Ingredient i: _ingredients){
			if(!fridge.contains(i)){
				difference.add(i);
			}
		}
		
		return difference;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YummlyRecipe other = (YummlyRecipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private class ImageUrls {
		public String hostedLargeUrl, hostedSmallUrl, hostedMediumUrl;
	}
}
