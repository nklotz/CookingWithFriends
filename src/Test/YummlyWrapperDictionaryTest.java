package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.WrapperDictionary;
import API.YummlyWrapperDictionary;
import UserInfo.Ingredient;

public class YummlyWrapperDictionaryTest {

	private WrapperDictionary _yummlyDict;

	@Before
	public void setUp() throws Exception {
		_yummlyDict = new YummlyWrapperDictionary();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void parsingTest() throws IOException, URISyntaxException {
		System.out.println("---INGREDIENTS---");
		for (Ingredient ingredient : _yummlyDict.getPossibleIngredients()) {
			System.out.println(ingredient.getName());
		}
		System.out.println("---DIETARY RESTRICTIONS---");
		for (String restriction : _yummlyDict.getPossibleDietaryRestrictions()) {
			System.out.println(restriction);
		}
		System.out.println("---ALLERGIES---");
		for (String allergy : _yummlyDict.getPossibleAllergies()) {
			System.out.println(allergy);
		}
	}

}
