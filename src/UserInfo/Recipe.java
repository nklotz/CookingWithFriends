package UserInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Interface for Recipes stored by the user.
 * @author hacheson & jschear
 *
 */
public interface Recipe extends Nameable, Serializable {
	
	public String getName();
	
	public List<Ingredient> getIngredients();
	
	public String getYield();
	
	public String getInstructions();
	
	public String getNumberOfServings();
	
	public String getID();
	
	public String getTime();
	
	public String getImageUrl();
	
	public Set<Ingredient> getIngredientDifference(Set<Ingredient> fridge);
	
}
