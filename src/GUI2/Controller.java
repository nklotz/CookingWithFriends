package GUI2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Callback;
import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import GUI.Style;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Invitation;
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
    @FXML
    private TextArea eventCommentDisplayField;
    @FXML
    private Button eventCommentPostButton;
    @FXML
    private TextArea eventCommentWriteField;
    @FXML
    private Button eventMenuAddButton;
    @FXML
    private ListView<?> eventMenuList; //*********************************************************
    @FXML
    private ToggleButton eventMenuRemoveButton;
    @FXML
    private ComboBox<?> eventSelector;  //*********************************************************
    @FXML
    private Button eventShoppingAddButton;
    @FXML
    private ListView<?> eventShoppingList; //*********************************************************
    @FXML
    private ToggleButton eventShoppingRemoveButton;
    @FXML
    private TextField kitchenAddChefField;
    @FXML
    private Button kitchenAddIngredientButton;
    @FXML
    private Button kitchenChefInviteButton;
    @FXML
    private ListView<Text> kitchenChefList;
    @FXML
    private ListView<String> kitchenDietList, kitchenAllergyList;
    @FXML
    private ComboBox<String> kitchenIngredientComboBox;
    @FXML
    private ListView<String> kitchenIngredientList;
    @FXML
    private ComboBox<String> kitchenSelector;
    @FXML
    private Label invalidEmailError;
    @FXML 
    private ListView<InvitationBox> invitationsList;
    
    
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
    	//*********************************************************
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
    
    private class SelectKitchen extends Label{
    	KitchenName _kname;
    	
    	public SelectKitchen(KitchenName kname){
    		_kname = kname;
    		this.setText(_kname.getName());
    		this.setTextFill(Color.BLACK);
    		//this.setFont(Font.font ("Verdana", 25));
    		this.setOnMouseReleased(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent e){
					displayKitchen(_kname);
				}
    		}); 
    	}
    	
    	public String getName(){
    		return _kname.getName();
    	}
    	
    	public String getID(){
    		return _kname.getID();
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
    	}//*********************************************************
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    private class InvitationBox extends GridPane{
    	protected Invitation _invite;

    	public InvitationBox(Invitation invite){
    		_invite = invite;

    		
    		Label message = new Label(_invite.getMessage());
    		Button accept = new Button("Accept");
    		accept.setOnAction(new EventHandler<ActionEvent>(){
    			@Override
    			public void handle(ActionEvent e){
    				System.out.println("Accept invitatioN!!!");
    				
    				_account.getInvitions().remove(_invite.getKitchenID());
    				_account.getKitchens().add(_invite.getKitchenID());
    				_client.storeAccount(_account);
    				_client.addActiveKitchenUser(_invite.getKitchenID().getID());
    				invitationsList.getItems().remove(this);
    				invitationsList.getItems().clear(); /*don't actually do this ********************************/
    			}
    		});
    		
    		Button decline = new Button("Decline");
    		decline.setOnAction(new EventHandler<ActionEvent>(){
    			@Override
    			public void handle(ActionEvent e){
    				System.out.println("REJECT invitatioN!!!");
    				
    				_account.getInvitions().remove(_invite.getKitchenID());
    				_client.storeAccount(_account);
    				_client.removeRequestedKitchenUser(_invite.getKitchenID().getID());
    				invitationsList.getItems().remove(this);
    				invitationsList.getItems().clear();
    			}
    		});
    		
    		this.add(message, 0, 0);
    		this.add(accept, 1, 0);
    		this.add(decline, 2, 0);
    	}

    }
    
    private class UserIngredientBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public UserIngredientBox(String display){//*********************************************************
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
    
    private class UserRecipeBox extends Label {
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
    	populateKitchenSelector();
    	populateInvitations();
    	
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
					System.out.println("Got " + results.size() + " results.");
					for (Recipe recipe : results) {
						resultsFlow.getChildren().add(new RecipeBox(recipe));
					}
				} catch (IOException ex) {
					resultsFlow.getChildren().add(new Text("Error querying API -- is your internet connection down?"));
				}
			}
		});
    }
    
    private class RecipeBox extends VBox {
    	public RecipeBox(Recipe recipe) {
    		super();
    		System.out.println("Creating recipe box");
    		this.getStyleClass().add("recipeBox");
//    		this.setPrefSize(150, 80);
//			this.setMaxSize(150, 80);
			this.setAlignment(Pos.CENTER);
			
			this.setPrefWidth(150);
			this.setMaxWidth(150);
			
			this.setPrefHeight(80);
			this.setMaxHeight(80);
    		Label recipeLabel = new Label(recipe.getName());
    		recipeLabel.setWrapText(true);
    		this.getChildren().add(recipeLabel);
    		if (recipe.hasImage()) {
    			Image recipeThumbnail = new Image(recipe.getImageUrl(), 80, 80, true, true, true); 
    			ImageView imageV = new ImageView(recipeThumbnail);
    			imageV.getStyleClass().add("recipeThumbnail");
    			this.getChildren().add(imageV);
    		}
    		
			this.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					createPopup();					
				}
			});
    	}
    }
    
    private void createPopup() {
    	//TODO: CREATE A bEAUTIFUL RECIPE POPUP!!!!!
    	//Perhaps do it in scenebuilder??/??
		System.out.println("I WOULD BE POPPING UP AN INGREDIENT BOX");
	}
    
    protected void populateSearchIngredients() {
    	
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
        ingredientsAccordion.getPanes().clear();
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
    	if(name!=null){
	    	if(name.trim().length()!=0){
	    		_account.addRestriction(name);
	    		_client.storeAccount(_account, 2, name);
	    		populateRestrictions();
	    	}
    	}
    }
    
    public void addAllergyListener(){
    	System.out.println("ADDING LISTENER ALLERGY: " +  addAllergyBar.getValue());
    	String name = addAllergyBar.getValue();
	    if(name!=null){
    		if(name.trim().length()!=0){
	    		_account.addAllergy(name);
	        	_client.storeAccount(_account, 4, name);
	        	populateAllergies();
	    	}
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
	    if(name!=null){
    		if(name.trim().length()!=0){
	    		_account.addIngredient(new Ingredient(name.toLowerCase().trim()));
	    		_client.storeAccount(_account);
	    	}
	    }
    	populateUserFridge();
        populateSearchIngredients();
    	
    }
    
    /**
     * Listener for new ingrenamedient box.
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
		 assert addAllergy != null : "fx:id=\"addAllergy\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addAllergyBar != null : "fx:id=\"addAllergyBar\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addRestriction != null : "fx:id=\"addRestriction\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addRestrictionBar != null : "fx:id=\"addRestrictionBar\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addShoppingIngredient != null : "fx:id=\"addShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert allergiesList != null : "fx:id=\"allergiesList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert editProfile != null : "fx:id=\"editProfile\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventCommentDisplayField != null : "fx:id=\"eventCommentDisplayField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventCommentPostButton != null : "fx:id=\"eventCommentPostButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventCommentWriteField != null : "fx:id=\"eventCommentWriteField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventMenuAddButton != null : "fx:id=\"eventMenuAddButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventMenuList != null : "fx:id=\"eventMenuList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventMenuRemoveButton != null : "fx:id=\"eventMenuRemoveButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventSelector != null : "fx:id=\"eventSelector\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventShoppingAddButton != null : "fx:id=\"eventShoppingAddButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventShoppingList != null : "fx:id=\"eventShoppingList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert eventShoppingRemoveButton != null : "fx:id=\"eventShoppingRemoveButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert fridgeList != null : "fx:id=\"fridgeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert ingredientsAccordion != null : "fx:id=\"ingredientsAccordion\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenAddChefField != null : "fx:id=\"kitchenAddChefField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenAddIngredientButton != null : "fx:id=\"kitchenAddIngredientButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenChefInviteButton != null : "fx:id=\"kitchenChefInviteButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenChefList != null : "fx:id=\"kitchenChefList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenDietList != null : "fx:id=\"kitchenDietList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenIngredientComboBox != null : "fx:id=\"kitchenIngredientComboBox\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenIngredientList != null : "fx:id=\"kitchenIngredientList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenSelector != null : "fx:id=\"kitchenSelector\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert locationField != null : "fx:id=\"locationField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newIngredient != null : "fx:id=\"newIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert recipeList != null : "fx:id=\"recipeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert removeAllergy != null : "fx:id=\"removeAllergy\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert removeFridgeIngredient != null : "fx:id=\"removeFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert removeRestriction != null : "fx:id=\"removeRestriction\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert removeShoppingIngredient != null : "fx:id=\"removeShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert restrictionsList != null : "fx:id=\"restrictionsList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert resultsFlow != null : "fx:id=\"resultsFlow\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert shoppingList != null : "fx:id=\"shoppingList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert invalidEmailError != null : "fx:id=\"invalidEmailError\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        
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
	
	public void populateKitchenSelector(){
		
		HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
		final HashMap<String,KitchenName> kitchenIds = _client.getKitchenIdMap();
		kitchenSelector.getItems().clear();
		kitchenSelector.getItems().addAll(kitchenIds.keySet());
		/*for(String k : kitchenIds.keySet()){
			kitchenSelector.getItems().add(k);
		}*/
		kitchenSelector.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(final ListView<String> name) {
				return new ListCell<String>() {
					private final Label id;
					{
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						id = new Label("balls");
				    }
					
					@Override
					protected void updateItem(String name, boolean empty) {
						super.updateItem(name, empty);
						
						if (name == null || empty) {
							setText("why is this null");
						} else {
							//id.setText(kitchenIds.get(name).getName());
							setText(kitchenIds.get(name).getName());
						}
					}
				};
			}
		});
		
		kitchenSelector.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				String id = kitchenSelector.getValue();
				kitchenSelector.setButtonCell(new ListCell<String>() {
					private final Label id;
					{
						setContentDisplay(ContentDisplay.TEXT_ONLY);
						id = new Label("balls");
				    }
					
					@Override
					protected void updateItem(String name, boolean empty) {
						super.updateItem(name, empty);
						
						if (name == null || empty) {
							setText("why is this null");
						} else {
							//id.setText(kitchenIds.get(name).getName());
							setText(kitchenIds.get(name).getName());
						}
					}
				});
				displayKitchen(kitchenIds.get(id));
			}
		});
	}
    
	public void displayKitchen(KitchenName kn){
		System.out.println("I WANT TO DISPLAY KITCHEN: " + kn.getName() + "   -->  " + kn.getID());
		
		_client.setCurrentKitchen(kn);
		
		Kitchen k = _client.getKitchens().get(kn);
		
		kitchenDietList.getItems().clear();
		for(String r: k.getDietaryRestrictions()){
			kitchenDietList.getItems().add(r);
		}
		
		kitchenAllergyList.getItems().clear();
		for(String r: k.getAllergies()){
			kitchenAllergyList.getItems().add(r);
		}
		
		
		kitchenIngredientList.getItems().clear();
		HashMap<Ingredient, HashSet<String>> map = k.getIngredientsMap();
		HashSet<String> toAddAll = new HashSet<String>();
		for(Ingredient ing: map.keySet()){
			String toDisplay = ing.getName() + " (";
			for(String user: map.get(ing)){
				toDisplay += " " + user;
			}
			toDisplay += ")";
				
			kitchenIngredientList.getItems().add(toDisplay);
		}
		
		kitchenIngredientComboBox.getItems().clear();
		for(Ingredient ing: _account.getIngredients()){
			kitchenIngredientComboBox.getItems().add(ing.getName());
		}
		
		kitchenChefList.getItems().clear();
		for(String user: k.getActiveUsers()){
			kitchenChefList.getItems().add(new Text(user));
		}
		for(String user: k.getRequestedUsers()){
			Text t = new Text(user + " (pending)");
			t.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
			t.setFill(Color.GRAY);
			kitchenChefList.getItems().add(t);
		}
		
		
	}

	public void reDisplayKitchen() {
		System.out.println("redisplaying " + _client.getCurrentKitchen().getName());
		displayKitchen(_client.getCurrentKitchen());
	}
	
	public void addIngredientToKichen(){
		System.out.println("add my ingredient to kitcheN!" );

		String ing = kitchenIngredientComboBox.getValue();
		if(ing!=null){
			if(!ing.equals("Select Ingredient from Fridge")){
				System.out.println("trying to add " + ing);
				_client.addIngredient(_client.getCurrentKitchen().getID(), new Ingredient(ing));
			}
		}
		
		
	}
	
	public void checkAndSendEmail(){
		if(kitchenAddChefField.getText() != null){
			if(isValidEmail(kitchenAddChefField.getText())){
				System.out.println("SENDING REQUEST TO " + kitchenAddChefField.getText());
				_client.addRequestedKitchenUser(kitchenAddChefField.getText(), _account.getName(), _client.getCurrentKitchen());
			}
			else{
				invalidEmailError.setVisible(true);
			}
		}
	}
	
	public void clearError(){
		invalidEmailError.setVisible(false);
	}
	
	public boolean isValidEmail(String email){
		if(email.trim().length()!=0){
			String[] atSplit = email.split("\\@");
			System.out.println(atSplit);
			if(atSplit.length==2){
				String[] dotSplit = atSplit[1].split("\\.");
				if(dotSplit.length>1){
					return true;
				}
			}
		}
		return false;
	}
	
	public void populateInvitations(){
		System.out.println("populate invitations");
		invitationsList.getItems().clear();
		HashMap<KitchenName, Invitation> invites = _account.getInvitions();
		System.out.println("user has " + invites.size() + " invitations!!!");
		for(KitchenName kn: _account.getInvitions().keySet()){
			invitationsList.getItems().add(new InvitationBox(invites.get(kn)));
		}
		
	}

}