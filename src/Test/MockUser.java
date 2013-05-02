package Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import UserInfo.User;

public class MockUser {
	
	private Account _account;
	public MockUser(){
		
	}
	
	public Account getAccount(){
		HashSet<KitchenName> kitchens = new HashSet<KitchenName>();
		KitchenName a = new KitchenName("West house", "/k/1");
		KitchenName b = new KitchenName("Natalie's surprise bday", "/k/2");
		KitchenName c = new KitchenName("Obama's coming for dinner", "/k/3");
		KitchenName d = new KitchenName("Playboy mansion", "/k/4");
		KitchenName e = new KitchenName("CS32 is almost over partayyyyyy", "/k/5");
		KitchenName f = new KitchenName("baby shower", "/k/6");
		kitchens.add(a);
		kitchens.add(b);
		kitchens.add(c);
		kitchens.add(d);
		kitchens.add(e);
		kitchens.add(f);
		
		String address = "Providence, RI";
		String name = "Crap Face";
		HashSet<String> restrictions = new HashSet<String>();
		restrictions.add("vegan");
		restrictions.add("vegetarian");
		
		Ingredient ai = new Ingredient("broccoli", "cups", 3);
		Ingredient bi = new Ingredient("raspberries", "cups", 4);
		Ingredient ci = new Ingredient("oranges", "cups", 1);
		Ingredient di = new Ingredient("turkey", "cups", 2.3);
		Ingredient ei = new Ingredient("flour", "cups", 2);
		List<Ingredient> ing = new ArrayList<Ingredient>();
		ing.add(ai);
		ing.add(bi);
		ing.add(ci);
		ing.add(di);
		ing.add(ei);
		Recipe r = new MockRecipe("chicken picatta", ing, "http://4.bp.blogspot.com/-Q8gBxP-bIWE/UIUQxrlWtVI/AAAAAAAAHqI/XB_lHiygXt4/s1600/cats_animals_little_kittens_kitten_kitty_cat_adorable_desktop_1920x1080_hd-wallpaper-782249.jpeg");
		Recipe r2 = new MockRecipe("raspberry pie", ing, "http://t2.gstatic.com/images?q=tbn:ANd9GcRMoBC1drAmlgW9nySQRDi1Uta-3sy0B50nKlE6G_SSL8OVNrgG");
		Recipe r3 = new MockRecipe("turkey", ing, "http://www.wallpapersonview.com/wallpapers/1/animals_beautiful_sweet_funny_cute_cats_picture-50.jpg");
		
		HashSet<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(r);
		recipes.add(r2);
		recipes.add(r3);
		
		Ingredient fi = new Ingredient("chicken", "c.", 3);
		Ingredient gi = new Ingredient("strawberries", "c.", 4);
		Ingredient hi = new Ingredient("gouda", "lbs.", 1);
		Ingredient ii = new Ingredient("brie", "lbs.", 2.3);
		Ingredient ji = new Ingredient("sugar", "c.", 2);
		
		HashSet<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.add(fi);
		ingredients.add(gi);
		ingredients.add(hi);
		ingredients.add(ii);
		ingredients.add(ji);
		
		
		_account = new Account("CWF", name, address, recipes, ingredients);
		_account.setKitchens(kitchens);
		return _account;
	}

}
