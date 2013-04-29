package API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YummlySearchBuilder {
	
	// API ID and Key
	private final static String APP_ID = "9404a024";
	private final static String APP_KEY = "6e07b7b6599dd2da3b0cea88ae2285fc";
	
	// Base URL strings for search and recipe requests
	private final static String HOST = "api.yummly.com";
	private final static String METADATA_PATH = "/v1/api/metadata/";
	
	private Gson _gson;
	private List<String> _possibleIngredients, _possibleRestrictions, _possibleAllergies;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		new YummlySearchBuilder();
	}
	
	public YummlySearchBuilder() throws IOException, URISyntaxException {
		_gson = new GsonBuilder().setPrettyPrinting().create();
		BufferedWriter out;
		String json;
		
		_possibleIngredients = new ArrayList<>();
		_possibleRestrictions = new ArrayList<>();
		_possibleAllergies = new ArrayList<>();
		
		//Read ingredient metadata into lists of strings
		json = readMetadata("ingredient");
		IngredientMetadata[] ingredients = _gson.fromJson(json, IngredientMetadata[].class);
		for (IngredientMetadata ingredient : ingredients) {
			_possibleIngredients.add(ingredient.searchValue);
		}
		
		json = readMetadata("diet");
		RestrictionMetadata[] restrictions = _gson.fromJson(json, RestrictionMetadata[].class);			
		for (RestrictionMetadata dietRestriction : restrictions) {
			_possibleRestrictions.add(dietRestriction.shortDescription);
		}
		
		json = readMetadata("allergy");
		restrictions = _gson.fromJson(json, RestrictionMetadata[].class);
		for (RestrictionMetadata allergy : restrictions) {
			_possibleAllergies.add(allergy.shortDescription);
		}
		
		File ingredientsFile = new File("ingredients"); 
		ingredientsFile.createNewFile();
		out = new BufferedWriter(new FileWriter(ingredientsFile));
		out.write(_gson.toJson(_possibleIngredients));
		out.close();
		
		File restrictionsFile = new File("dietary_restrictions");
		restrictionsFile.createNewFile();
		out = new BufferedWriter(new FileWriter(restrictionsFile));
		out.write(_gson.toJson(_possibleRestrictions));
		out.close();
		
		File allergiesFile = new File("allergies");
		allergiesFile.createNewFile();
		out = new BufferedWriter(new FileWriter(allergiesFile));
		out.write(_gson.toJson(_possibleAllergies));
		out.close();
	}
	
	private static String readMetadata(String searchKey) throws IOException, URISyntaxException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(HOST).setPath(METADATA_PATH + searchKey);
		String jsonpResponse = httpGet(builder.build());
		String prefix = "set_metadata('" + searchKey + "', ";
		return jsonpResponse.substring(prefix.length(), jsonpResponse.length() - 2);
	}
	
	private class IngredientMetadata {
		public String searchValue;
	}
	
	private class RestrictionMetadata {
		public String shortDescription;
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
	

}
