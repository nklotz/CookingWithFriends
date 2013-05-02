package GUI2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import server.AutocorrectEngines;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Nameable;
import client.Client;


public class Controller extends AnchorPane implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addFridgeIngredient;
    
    @FXML
    private CheckBox removeIngredientButton;
    
    @FXML
    private CheckBox removeAllergy;

    @FXML
    private ComboBox<String> addShoppingIngredient;

    @FXML
    private ListView<UserIngredientBox> fridgeList;

    @FXML
    private ComboBox<String> newIngredient;

    @FXML
    private ListView<UserRecipeBox> recipeList;

    @FXML
    private ListView<ShoppingIngredientBox> shoppingList;
    
    @FXML
    private ToggleButton removeFridge;
    
    @FXML
    private Button addRestriction;
    
    @FXML
    private Button addAllergy;
    
    @FXML
    private CheckBox removeRestriction;
    
    @FXML
    private ComboBox<String> addRestrictionBar;
    
    @FXML
    private ComboBox<String> addAllergyBar;
    
    @FXML
    private ListView<RestrictionBox> restrictionsList;

    private Client _client;
    private Account _account;
    private Map<KitchenName,Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    
    
    @FXML
    void initialize() {
        assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert addShoppingIngredient != null : "fx:id=\"addShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert fridgeList != null : "fx:id=\"fridgeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert newIngredient != null : "fx:id=\"newIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert shoppingList != null : "fx:id=\"shoppingList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert restrictionsList != null : "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";

    }
    
    private class  GuiBox extends GridPane{

    	public void remove(){}
    }
    
    
    private class RemoveButton extends Button{
    	
    	public RemoveButton(final GuiBox parent){
    		this.setText("X");
    		
    		this.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
                public void handle(ActionEvent e) {
    				parent.remove();
    			}
    		});
    	}
    }
    
    private class ShoppingIngredientBox extends GuiBox{
    	protected String _toDisplay;

    	public ShoppingIngredientBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    RemoveButton remove = new RemoveButton(this);
    	    remove.setVisible(false);
    	    this.add(remove, 0, 0);
    	}
    	
    	public void remove(){
    		System.out.println("REMOVING ingredient!!!!!!!!!!!!!");
    		Ingredient ing = new Ingredient(_toDisplay);
    		_account.removeShoppingIngredient(ing);
    		_client.storeAccount(_account, ing);
    		ObservableList<ShoppingIngredientBox> listItems = shoppingList.getItems();
    		listItems.remove(this);
    	}
    }
    
    private class RestrictionBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public RestrictionBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);;
    	}
    	
    	public void remove(){
    		System.out.println("removing restriction!!!!!!!!!!!!!");
    		_account.removeRestriction(_toDisplay);
    		_client.storeAccount(_account, 3, _toDisplay);
    		ObservableList<RestrictionBox> listItems = restrictionsList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    private class UserIngredientBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public UserIngredientBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);;
    	}
    	
    	public void remove(){
    		System.out.println("REMOVING ingredient!!!!!!!!!!!!!");
    		Ingredient ing = new Ingredient(_toDisplay);
    		_account.removeIngredient(ing);
    		_client.storeAccount(_account, ing);
    		ObservableList<UserIngredientBox> listItems = fridgeList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    private class UserRecipeBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public UserRecipeBox(String display){
    		_toDisplay = display;
    	    Label rec = new Label(display);
    	    this.add(rec, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);
    	}
    	
    	public void remove(){
    		Ingredient ing = new Ingredient(_toDisplay);
    		_account.removeIngredient(ing);
    		_client.storeAccount(_account, ing);
    		ObservableList<UserIngredientBox> listItems = fridgeList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    public void setUp(Client client, Account account, Map<KitchenName,Kitchen> kitchens, AutocorrectEngines engines){
    	_client = client;
    	_account = account;
    	_kitchens = kitchens;
    	_engines = engines;
    	initializeRestrictionsBar();
    	populateUserFridge();
    	populateUserRecipes();
    	populateShoppingList();
    	populateAllergies();
    	populateRestrictions();
    	
    	List<String> list = new ArrayList<String>();
    	addShoppingIngredient.getItems().addAll(list);
    	newIngredient.getItems().addAll(list);
    	//addShoppingIngredient.
    //	initializeAutocorrect();
    	
    	

    }
    
    public void initializeRestrictionsBar(){
    	newIngredient.getItems().clear();
    	addRestrictionBar.getItems().addAll("Vegan", "Lacto vegetarian", "Ovo vegetarian", 
    			"Pescetarian", "Lacto-ovo vegetarian");
    }
    public void addShoppingListListener(){
    	System.out.println("ADD INGREDIENT LISTENER");
    	//addFridgeIngredient
    	String name = addShoppingIngredient.getEditor().getText();
    	_account.addShoppingIngredient(new Ingredient(name));
    	_client.storeAccount(_account);
    	populateShoppingList();
    }
    
    public void addIngredientListener(){
    	System.out.println("ADD INGREDIENT LISTENER");
    	//addFridgeIngredient
    	String name = newIngredient.getEditor().getText();
    	_account.addIngredient(new Ingredient(name));
    	_client.storeAccount(_account);
    	populateUserFridge();
    	
    	
    }
    
    /**
     * Listener for new ingredient box.
     */
    public void ingredientComboListener(){
    	String text = newIngredient.getEditor().getText();
    	List<String> suggs = null;
    	if(text.trim().length()==0){
    		newIngredient.getItems().clear();
    	}
    	if(text.trim().length()!=0)
    		suggs = _engines.getIngredientSuggestions(text);
    	if(suggs!=null){
    		newIngredient.getItems().clear();
    		newIngredient.getItems().addAll(suggs);
    	}
    }
    
    /**
     * Listener for adding ingredient to shopping list.
     */
    public void shoppingListComboListener(){
    	String text = addShoppingIngredient.getEditor().getText();
    	List<String> suggs = null;
    	if(text.trim().length()!=0)
    		suggs = _engines.getIngredientSuggestions(text);
    	if(suggs!=null){
    		addShoppingIngredient.getItems().clear();
    		addShoppingIngredient.getItems().addAll(suggs);
    	}
    }
    
    public void restrictionComboListener(){
    	System.out.println("REST LISTENER IS CALLED");
    	String text = addRestrictionBar.getEditor().getText();
    	List<String> suggs = null;
    	if(text.trim().length()!=0)
    		suggs = _engines.getRestrictionSuggestions(text);
    	if(suggs!=null){
    		addRestrictionBar.getItems().clear();
    		addRestrictionBar.getItems().addAll(suggs);
    	}
    }
    
    public void populateAllergies(){
    	
    }
    
    public void populateRestrictions(){
    	ObservableList<RestrictionBox> listItems = FXCollections.observableArrayList(); 
    	for(String r: _account.getDietaryRestrictions()){
    		RestrictionBox box = new RestrictionBox(r);
    		listItems.add(box);
    	}
    	
    	restrictionsList.setItems(listItems);
    }
    
    
    public void populateUserRecipes(){
    	ObservableList<UserRecipeBox> listItems = FXCollections.observableArrayList(); 
    	for(Nameable r: _account.getRecipes()){
    		UserRecipeBox box = new UserRecipeBox(r.getName());
    		listItems.add(box);
    	}
    	recipeList.setItems(listItems);
    }
    
    public void populateUserFridge(){
    	ObservableList<UserIngredientBox> listItems = FXCollections.observableArrayList();  
    	fridgeList.setItems(listItems);
    	for(Ingredient i: _account.getIngredients()){
    		UserIngredientBox box = new UserIngredientBox(i.getName());
    		listItems.add(box);
    	}
    	fridgeList.setItems(listItems);
    }
    
    public void populateShoppingList(){
    	ObservableList<ShoppingIngredientBox> listItems = FXCollections.observableArrayList();  
    	shoppingList.setItems(listItems);
    	for(Ingredient i: _account.getShoppingList()){
    		ShoppingIngredientBox box = new ShoppingIngredientBox(i.getName());
    		listItems.add(box);
    	}
    	shoppingList.setItems(listItems);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("I HAVE BEEN INITIALIZED");
		assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert addShoppingIngredient != null : "fx:id=\"addShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert fridgeList != null : "fx:id=\"fridgeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert newIngredient != null : "fx:id=\"newIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert shoppingList != null : "fx:id=\"shoppingList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	}
	
	public void removeIngredients(){
		System.out.println("EHROWRJOEJIEOW JHEYYYYYYYYYYYYYYYYYYYY");
		
		for(UserIngredientBox s: fridgeList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
    

}