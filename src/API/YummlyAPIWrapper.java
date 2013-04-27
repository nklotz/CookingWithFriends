package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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
	
	private Gson _gson;
	private Map<String, List<YummlyRecipe>> _searchCache;
	private Map<String, YummlyRecipe> _recipeCache;
	
	public YummlyAPIWrapper() {
		_gson = new Gson();
		_searchCache = new HashMap<>();
		_recipeCache = new HashMap<>();
	}
		
	@Override
	public List<Recipe> recipes(String toFind) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Recipe convertToRecipe(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> neededIngredients(List<Recipe> recipes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Recipe getRecipe(String recipeID) throws IOException {
		Recipe recipe = null;
		String jsonResponse = null;
		try {
			jsonResponse = httpGet(buildRecipeURI(recipeID));
		} catch (IOException | URISyntaxException e) {
			throw new IOException("Failed to perform recipe GET request.");
		}
		if (jsonResponse != null) {
			recipe = _gson.fromJson(jsonResponse, YummlyRecipe.class);
		}
		return recipe;
	}

	@Override
	public List<? extends Recipe> findRecipesWithIngredients(String query, List<String> ingredients, List<String> dislikes, 
			List<String> dietRestrictions, 	List<String> allergies) throws IOException {
		
		URI searchKey = null;
		List<YummlyRecipe> recipes = null;
		String jsonResponse = null;
		try {
			searchKey = buildSearchURI(query, ingredients, dislikes, dietRestrictions, allergies);
			//Check if we've already made this query
			if (_searchCache.containsKey(searchKey))
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
	
	private static URI buildRecipeURI(String id) throws URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(RECIPE_PATH + id);
		return builder.build();
	}
	
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
	
	
	//Private classes for reading objects from JSON
	private class Response {
		public int totalMatchCount;
		public List<YummlyRecipe> matches;
	}
	
}
