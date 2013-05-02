package GUI2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import client.Client;


public class Controller extends AnchorPane implements Initializable {

	private Image xImage = new Image("http://4.bp.blogspot.com/-JgoPXVNn5-U/UU3x1hDVHcI/AAAAAAAAA-E/s2dwrJcapd0/s1600/redx-300x297.jpg", 10, 10, true, true, true);
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addFridgeIngredient, editProfile, addRestriction, addAllergy, searchButton;
    @FXML
    private CheckBox removeShoppingIngredient, removeFridgeIngredient, removeAllergy, removeRestriction;
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
    private Label nameLabel, locationLabel, emailLabel;  
    @FXML
    private TextField nameField, locationField, searchField;   
    @FXML
    private ToggleButton removeFridge;          
    @FXML
    private ComboBox<String> addRestrictionBar, addAllergyBar;    
    @FXML
    private ListView<RestrictionBox> restrictionsList;
    @FXML
    private ListView<AllergyBox> allergiesList;
    @FXML
    private FlowPane resultsFlow;
    @FXML
    private Accordion ingredientsAccordion;

    //Local Data
    private Client _client;
    private Account _account;
    private Map<KitchenName,Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    private Wrapper _api;
    private List<CheckBox> _ingredientsBoxes;
    
    
    @FXML
    void initialize() {
    	System.out.println("HOLY SHIT I DIDN'T KNOW THIS METHOD EVER GETS CALLED?");
    }
    
    private abstract class  GuiBox extends GridPane{

    	public void remove() {};
    	
    	public RemoveButton getRemover(){
    		return null;
    	}
    	
    }
    
    private class RemoveButton extends Button{
    	
    	public RemoveButton(final GuiBox parent){
    		this.setGraphic(new ImageView(xImage));
    		
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
    	protected RemoveButton _remove;
    	
    	public ShoppingIngredientBox(String display){
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
    		_account.removeShoppingIngredient(ing);
    		_client.storeAccount(_account);
    		ObservableList<ShoppingIngredientBox> listItems = shoppingList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    private class AllergyBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public AllergyBox(String display){
    		_toDisplay = display;
    	    Label all = new Label(display);
    	    this.add(all, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);;
    	}
    	
    	public void remove(){
    		System.out.println("removing restriction!!!!!!!!!!!!!");
    		//_account.removeRestriction(_toDisplay);
    		_account.removeAllergy(_toDisplay);
    		_client.storeAccount(_account, 5, _toDisplay);
    		ObservableList<AllergyBox> listItems = allergiesList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
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
    
    private class UserRecipeBox extends Label{
    	private RemoveButton _remove;
    	private Recipe _recipe;

    	public UserRecipeBox(Recipe recipe){
    		_recipe = recipe;
    		if(recipe.getImageUrl()!=null){
	    		Image thumbnail = new Image(recipe.getImageUrl(), 20, 20, true, true, true); 
				this.setGraphic(new ImageView(thumbnail));
    		}
			this.setText(recipe.getName());
			this.setOnMouseReleased(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					System.out.println("I WOULD TOTALLY POP UP A RECIPE WINDOW");
				}
			});
    	}
    	
    	public void remove(){
    		_account.removeRecipe(_recipe);
    		_client.storeAccount(_account);
    		ObservableList<UserRecipeBox> listItems = recipeList.getItems();
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
    	_api = new YummlyAPIWrapper();
    	
    	initializeComboBoxes();
    	populateUserFridge();
    	populateUserRecipes();
    	populateShoppingList();
    	populateAllergies();
    	populateRestrictions();
    	populateInfo();
    	
    	//Set up search page
    	setUpSearchTab();
    	
    	
    	List<String> list = new ArrayList<String>();
    	addShoppingIngredient.getItems().addAll(list);
    	newIngredient.getItems().addAll(list);
    	//addShoppingIngredient.
    //	initializeAutocorrect();
    }
    
    private void setUpSearchTab() {
    	_ingredientsBoxes = new ArrayList<>();
    	populateSearchIngredients();
    	searchButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				System.out.println("Clicked search button!");
				List<String> selectedIngredients = new ArrayList<>();
				for (CheckBox checkBox : _ingredientsBoxes) {
					if (checkBox.isSelected()) selectedIngredients.add(checkBox.getText());
				}
				System.out.println("Querying with ingredients: " + selectedIngredients);
				//Attempt to query API
				try {
					List<String> dummy = Collections.emptyList(); //TODO: POOL KITCHEN ALLERGIES
					List<? extends Recipe> results = _api.searchRecipes(searchField.getText(), selectedIngredients, dummy, dummy, dummy);
					System.out.println("Got results: " + results);
					for (Recipe recipe : results) {
						resultsFlow.getChildren().add(new RecipeBox(recipe));
					}
				} catch (IOException ex) {
					resultsFlow.getChildren().add(new Text("Error querying API -- is your internet connection down?"));
				}
			}
		});
    }
    
    private class RecipeBox extends GridPane {
    	public RecipeBox(Recipe recipe) {
    		super();
    		System.out.println("Creating recipe box");
    		if (recipe.hasImage()) {
    			Image recipeThumbnail = new Image(recipe.getImageUrl(), 80, 80, true, true, true); 
    			this.add(new ImageView(recipeThumbnail), 1, 1, 1, 1);
    		}
    		this.add(new Label(recipe.getName()), 0, 0);
			this.setPrefSize(200, 200);
			this.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					createPopup();					
				}
			});
    	}
    }
    
    private void createPopup() {
		// TODO Auto-generated method stub
		System.out.println("I WOULD BE POPPING UP AN INGREDIENT BOX");
	}
    
    private void populateSearchIngredients() {
    	List<TitledPane> kitchenPanes = new ArrayList<>();
        TitledPane personalPane =  new TitledPane();
        personalPane.setText("My Fridge");
        personalPane.setContent(makeIngredientList(_account.getIngredients()));
        kitchenPanes.add(personalPane);
        for (Kitchen kitchen : _kitchens.values()) {
        	TitledPane kitchenPane =  new TitledPane();
        	kitchenPane.setText(kitchen.getName());
        	kitchenPane.setContent(makeIngredientList(kitchen.getIngredients()));
        	kitchenPanes.add(kitchenPane);
        }
        ingredientsAccordion.getPanes().addAll(kitchenPanes);		
	}

	private ListView<CheckBox> makeIngredientList(Set<Ingredient> ingredients) {
		ListView<CheckBox> ingredientsView = new ListView<>();
		List<CheckBox> thisList = new ArrayList<>();
		for (Ingredient ing : ingredients) {
			thisList.add(new CheckBox(ing.getName()));
		}
		_ingredientsBoxes.addAll(thisList);
		ingredientsView.getItems().addAll(thisList);
		return ingredientsView;
	}

	public void initializeComboBoxes(){
    	addRestrictionBar.getItems().clear();
    	newIngredient.getItems().clear();
    	addAllergyBar.getItems().clear();
    	addShoppingIngredient.getItems().clear();
    	addRestrictionBar.getItems().addAll("Vegan", "Lacto vegetarian", "Ovo vegetarian", 
    			"Pescetarian", "Lacto-ovo vegetarian");
    	addAllergyBar.getItems().addAll("Wheat-Free", "Gluten-Free", "Peanut-Free", 
    			"Tree Nut-Free", "Dairy-Free", "Egg-Free", "Seafood-Free", "Sesame-Free", 
    			"Soy-Free", "Sulfite-Free");
    	//TODO: READ THESE FROM SERVER? (just don't hard code them?) is this worth doing?!/!
    }
    
    public void addRestrictionListListener(){
    	System.out.println("ADDING LISTENER RESTR: " +  addRestrictionBar.getValue());
    	String name = addRestrictionBar.getValue();
    	_account.addRestriction(name);
    	_client.storeAccount(_account, 2, name);
    	populateRestrictions();
    }
    
    public void addAllergyListener(){
    	System.out.println("ADDING LISTENER ALLERGY: " +  addAllergyBar.getValue());
    	String name = addAllergyBar.getValue();
    	if(name!=null){
    		_account.addAllergy(name);
        	_client.storeAccount(_account, 4, name);
        	populateAllergies();
    	}
    	
    }
    
    public void addShoppingListListener(){
    	System.out.println("ADD SHOPPING INGREDIENT LISTENER: " + addShoppingIngredient.getValue());
    	//addFridgeIngredient
    	disableRemoves(shoppingList);
    	removeShoppingIngredient.setSelected(false);
    	String name = addShoppingIngredient.getEditor().getText();
    	if(name!=null){
    		if(name.trim().length()!=0){
    			_account.addShoppingIngredient(new Ingredient(name.toLowerCase().trim()));
    			_client.storeAccount(_account);
    			populateShoppingList();
    		}
    	}
    	
    }
    
    public void addIngredientListener(){
    	System.out.println("ADD INGREDIENT LISTENER");
    	//addFridgeIngredient
    	disableRemoves(fridgeList);
    	System.out.println("was removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
    	removeFridgeIngredient.setSelected(false);
    	System.out.println("now removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
    	String name = newIngredient.getEditor().getText();
    	if(name.trim().length()!=0){
    		_account.addIngredient(new Ingredient(name.toLowerCase().trim()));
    		_client.storeAccount(_account);
    	}
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
    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
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
    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
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
    		suggs = _engines.getRestrictionSuggestions(text.toLowerCase());
    	if(suggs!=null){
    		addRestrictionBar.getItems().clear();
    		addRestrictionBar.getItems().addAll(suggs);
    	}
    }
    
    public void populateAllergies(){
    	ObservableList<AllergyBox> listItems = FXCollections.observableArrayList(); 
    	for(String a: _account.getAllergies()){
    		AllergyBox box = new AllergyBox(a);
    		listItems.add(box);
    	}
    	allergiesList.setItems(listItems);
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
    	for(Recipe r: _account.getRecipes()){
    		UserRecipeBox box = new UserRecipeBox(r);
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
        assert removeFridgeIngredient!= null: "fx:id=\removeFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert removeShoppingIngredient!= null: "fx:id=\removeShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert restrictionsList != null : "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert nameLabel != null: "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert locationLabel != null: "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert nameLabel != null: "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
        assert editProfile !=null: "fx:id=\"editProfile\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	    System.out.println(locationLabel);   
	    System.out.println(nameLabel);   
	}
	
	public void removeIngredients(){		
		for(UserIngredientBox s: fridgeList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
	public void removeRestrictions(){		
		for(RestrictionBox s: restrictionsList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
	public void removeAllergies(){		
		for(AllergyBox s: allergiesList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
	public void removeShoppingIngredient(){		
		for(ShoppingIngredientBox s: shoppingList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
	public void disableRemoves(ListView<? extends GuiBox> listview){
		for(GuiBox gb: listview.getItems()){
			gb.getRemover().setVisible(false);
		}
	}
	
	public void populateInfo(){
		System.out.println("name: " + _account.getName());
		nameLabel.setText(_account.getName());
		System.out.println("address: " + _account.getAddress());
		locationLabel.setText(_account.getAddress());
		emailLabel.setText(_account.getID());
		nameLabel.setVisible(true);
		locationLabel.setVisible(true);
		emailLabel.setVisible(true);
		locationField.setVisible(false);
		nameField.setVisible(false);
	}
	
	public void editInfo(){
		System.out.println("Editing info: "+ editProfile.getText());
		
		if(editProfile.getText().equals("Edit")){
			
			nameLabel.setVisible(false);
			nameField.setText(nameLabel.getText());
			nameField.setVisible(true);
			locationLabel.setVisible(false);
			locationField.setText(locationLabel.getText());
			locationField.setVisible(true);
			
			editProfile.setText("Save");
						
		}
		else{
			_account.setName(nameField.getText());
			_account.setAddress(locationField.getText());
			_client.storeAccount(_account);
			
			editProfile.setText("Edit");
			
			populateInfo();
		}
	}
    
	

}