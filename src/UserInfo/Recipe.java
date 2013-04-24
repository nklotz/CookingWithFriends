package UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Recipe stored by the user.
 * @author hacheson
 *
 */
public class Recipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String _name;
	public final String _id;
	public final String _instructions;
	public final ArrayList<String> _ingredients;
	
	
	public Recipe(String name, String id, String instructions, ArrayList<String> ingredients){
		_name = name;
		_id = id;
		_instructions = instructions;
		_ingredients = ingredients;
	}
	
}
