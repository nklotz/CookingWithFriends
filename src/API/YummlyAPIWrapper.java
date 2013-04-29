package API;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import UserInfo.Recipe;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * This is a wrapper class for the Yummly API.
 * It queries the API by a list of ingredients, and can returns a list of recipe objects.
 * 
 * @author jschear
 *
 */
public class YummlyAPIWrapper implements Wrapper {

	// API ID and Key
	private final static String APP_ID = "9404a024";
	private final static String APP_KEY = "6e07b7b6599dd2da3b0cea88ae2285fc";
	
	// Base URL strings for search and recipe requests
	private final static String HOST = "api.yummly.com";
	private final static String SEARCH_PATH = "/v1/api/recipes";
	private final static String RECIPE_PATH = "/v1/api/recipe/";
	
	private final String[] DIETARY_RESTRICTIONS = {"Vegan", "Lacto vegetarian", "Ovo vegetarian", 
			"Pescetarian", "Lacto-ovo vegetarian"};
	private final String[] ALLERGIES = {"Wheat-Free", "Gluten-Free", "Peanut-Free", 
			"Tree Nut-Free", "Dairy-Free", "Egg-Free", "Seafood-Free", "Sesame-Free", 
			"Soy-Free", "Sulfite-Free"};
	
	private Gson _gson;
	private Map<String, List<YummlyRecipe>> _searchCache;
	private Map<String, YummlyRecipe> _recipeCache;
	private List<String> _possibleIngredients;
	
	public YummlyAPIWrapper() {
		_gson = new Gson();
		_searchCache = new HashMap<>();
		_recipeCache = new HashMap<>();
		
		//Read ingredient metadata
		try {
			_possibleIngredients = Arrays.asList(_gson.fromJson(new FileReader("ingredients"), String[].class));
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.out.println("ERROR: Couldn't read files of search values.");
		}
	}
		
	@Override
	public List<String> getPossibleIngredients() throws IOException, URISyntaxException {
		return _possibleIngredients;
	}	
	
	@Override
	public List<String> getPossibleDietaryRestrictions() throws IOException, URISyntaxException {
		return Arrays.asList(DIETARY_RESTRICTIONS);
	}

	@Override
	public List<String> getPossibleAllergies() throws IOException, URISyntaxException {
		return Arrays.asList(ALLERGIES);
	}
	
	@Override
	public Recipe getRecipe(String recipeID) throws IOException {
		if (_recipeCache.containsKey(recipeID)) {
			return _recipeCache.get(recipeID);
		}
		
		YummlyRecipe recipe = null;
		String jsonResponse = null;
		try {
			jsonResponse = httpGet(buildRecipeURI(recipeID));
		} catch (IOException | URISyntaxException e) {
			throw new IOException("Failed to perform recipe GET request.");
		}
		if (jsonResponse != null) {
			recipe = _gson.fromJson(jsonResponse, YummlyRecipe.class);
			_recipeCache.put(recipeID, recipe);
		}
		return recipe;
	}

	@Override
	public List<? extends Recipe> searchRecipes(String query, List<String> ingredients, List<String> dislikes, 
			List<String> dietRestrictions, 	List<String> allergies) throws IOException {
		
		URI searchKey = null;
		List<YummlyRecipe> recipes = null;
		String jsonResponse = null;
		try {
			searchKey = buildSearchURI(query, ingredients, dislikes, dietRestrictions, allergies);
			//Check if we've already made this query
			if (_searchCache.containsKey(searchKey.toString()))
				return _searchCache.get(searchKey.toString());
			jsonResponse = httpGet(searchKey);
		} catch (IOException | URISyntaxException e) {
			throw new IOException("Failed to perform GET request.");
		}
		if (jsonResponse != null) {
			Response response = _gson.fromJson(jsonResponse, Response.class);
			recipes = response.matches;
			_searchCache.put(searchKey.toString(), recipes);
		}
		return recipes;
	}
	
	/**
	 * Builds a URI to query the Yummly API for a recipe search.
	 * @param query
	 * @param ingredients
	 * @param dislikes
	 * @param dietRestrictions
	 * @param allergies
	 * @return
	 * @throws URISyntaxException
	 */
	private static URI buildSearchURI(String query, List<String> ingredients, List<String> dislikes, 
			List<String> dietRestrictions, 	List<String> allergies) throws URISyntaxException {
		
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(SEARCH_PATH).setParameter("q", query);
		
		for (String ingredient : ingredients)
			builder.addParameter("allowedIngredient[]", ingredient);
		for (String dislike : dislikes)
			builder.addParameter("excludedIngredient[]", dislike);
		for (String restriction : dietRestrictions)
			builder.addParameter("allowedDiet[]", restriction);
		for (String allergy : allergies)
			builder.addParameter("allowedAllergy[]", allergy);
		
		return builder.build();
	}
	
	/**
	 * Builds a URI for a recipe query to the Yummly API.
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 */
	private static URI buildRecipeURI(String id) throws URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(RECIPE_PATH + id);
		return builder.build();
	}
	
	/**
	 * Performs an HTTP GET request for the URI passed in.
	 * Returns a string respresentation of the returned JSON object.
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	private static String httpGet(URI uri) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(uri);
		httpget.addHeader("X-Yummly-App-ID", APP_ID);
		httpget.addHeader("X-Yummly-App-Key", APP_KEY);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		
		StringBuilder sb = new StringBuilder();
		if (entity != null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
		    try {
				String line;
				while ((line = rd.readLine()) != null) {
				  sb.append(line);
				}
		    } finally {
		    	rd.close();
		    }
		}
		return sb.toString();
	}
	
	
	//Private inner class for reading objects from JSON
	private class Response {
		public List<YummlyRecipe> matches;
	}
}
