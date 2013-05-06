package GUI2;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import Email.Sender;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Controller2 extends AnchorPane implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Label NoSearchResults;
    @FXML private Button addFridgeIngredient;
    @FXML private ComboBox<?> addRecipeEventSelector;
    @FXML private Button addRecipeToEventButton;
    @FXML private ComboBox<String> addShoppingIngredient;
    @FXML private ImageView chefHat;
    @FXML private Label communalDietPreferencesList;
    @FXML private ImageView envelope;
    @FXML private ListView<UserIngredientBox> fridgeList;
    @FXML private CheckBox getRecipeChecksButton;
    @FXML private Button goToRecipeSearchButton;
    @FXML private Accordion ingredientsAccordion;
    @FXML private ListView<?> invitationsList;
    @FXML private ListView<Text> kitchenChefList;
    @FXML private Pane kitchenHide;
    @FXML private ListView<String> kitchenIngredientList;
    @FXML private ComboBox<String> kitchenSelector;
    @FXML private Button leaveKitchenButton;
    @FXML private ComboBox<String> newIngredient;
    @FXML private Text newKitchenActionText;
    @FXML private Button newKitchenButton;
    @FXML private Button newKitchenCancelButton;
    @FXML private Button newKitchenCreateButton;
    @FXML private Label newKitchenLabel;
    @FXML private TextField newKitchenNameField;
    @FXML private AnchorPane newKitchenPane;
    @FXML private Label numberOfInvites;
    @FXML private FlowPane recipeFlow;
    @FXML private Tab recipeSearchTab;
    @FXML private CheckBox removeFridgeIngredient;
    @FXML private AnchorPane removeIngredientsButton;
    @FXML private CheckBox removeShoppingIngredient;
    @FXML private FlowPane resultsFlow;
    @FXML private AnchorPane root;
    @FXML private ListView<String> searchAdditionalList;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML private ListView<ShoppingIngredientBox> shoppingList;
    @FXML private TabPane tabPane;
    @FXML private Label welcome;
    @FXML private Label weather;
    @FXML private Label nameLabel, locationLabel;
    @FXML private TextField nameField, locationField;
    @FXML private Button profileEditor;
    @FXML private ComboBox<String> addRestrictionBar, addAllergyBar; 
    @FXML private CheckBox removeAllergy, removeRestriction;
    @FXML private ListView<RestrictionBox> restrictionsList;
    @FXML private ListView<AllergyBox> allergiesList;
    @FXML private Label emailLabel;
    
    //Local Data
    private Client _client;
    private Account _account;
    private Map<KitchenName, Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    private Wrapper _api;
    private String _currentEventName;
    //private KitchenPane _currentKitchenPane;
    private HashSet<String> _setOfAdditionalSearchIngs = new HashSet<String>();//Set of additional ingredients for search.
    private InviteChefController _inviteChefController;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        assert NoSearchResults != null : "fx:id=\"NoSearchResults\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert addRecipeEventSelector != null : "fx:id=\"addRecipeEventSelector\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert addRecipeToEventButton != null : "fx:id=\"addRecipeToEventButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert addShoppingIngredient != null : "fx:id=\"addShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert allergiesList != null : "fx:id=\"allergiesList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert chefHat != null : "fx:id=\"chefHat\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert communalDietPreferencesList != null : "fx:id=\"communalDietPreferencesList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert envelope != null : "fx:id=\"envelope\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert fridgeList != null : "fx:id=\"fridgeList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert getRecipeChecksButton != null : "fx:id=\"getRecipeChecksButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert goToRecipeSearchButton != null : "fx:id=\"goToRecipeSearchButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert ingredientsAccordion != null : "fx:id=\"ingredientsAccordion\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert invitationsList != null : "fx:id=\"invitationsList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert kitchenChefList != null : "fx:id=\"kitchenChefList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert kitchenHide != null : "fx:id=\"kitchenHide\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert kitchenIngredientList != null : "fx:id=\"kitchenIngredientList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert kitchenSelector != null : "fx:id=\"kitchenSelector\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert leaveKitchenButton != null : "fx:id=\"leaveKitchenButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newIngredient != null : "fx:id=\"newIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenActionText != null : "fx:id=\"newKitchenActionText\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenButton != null : "fx:id=\"newKitchenButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenCancelButton != null : "fx:id=\"newKitchenCancelButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenCreateButton != null : "fx:id=\"newKitchenCreateButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenLabel != null : "fx:id=\"newKitchenLabel\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenNameField != null : "fx:id=\"newKitchenNameField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert newKitchenPane != null : "fx:id=\"newKitchenPane\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert numberOfInvites != null : "fx:id=\"numberOfInvites\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert recipeFlow != null : "fx:id=\"recipeFlow\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert recipeSearchTab != null : "fx:id=\"recipeSearchTab\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert removeFridgeIngredient != null : "fx:id=\"removeFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert removeIngredientsButton != null : "fx:id=\"removeIngredientsButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert removeShoppingIngredient != null : "fx:id=\"removeShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert resultsFlow != null : "fx:id=\"resultsFlow\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert searchAdditionalList != null : "fx:id=\"searchAdditionalList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert shoppingList != null : "fx:id=\"shoppingList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
		
	}
	
	public void setUp(Client client, Account account, Map<KitchenName,Kitchen> kitchens, AutocorrectEngines engines){
    	_client = client;
    	_account = account;
    	_kitchens = kitchens;
    	_engines = engines;
    	_api = new YummlyAPIWrapper();
    	populateUserFridge();
    	populateShoppingList();
    	displayPleasantries();
    	nameLabel.setText(_account.getName());
    	locationLabel.setText(_account.getAddress());
    	emailLabel.setText(_account.getID());
    	initializeComboBoxes();
    	populateKitchenSelector();
	}
	
	public void initializeComboBoxes(){
		kitchenSelector.getItems().clear();
		//eventSelector.getItems().clear();
	//	eventShoppingComboBox.getItems().clear();
    	addRestrictionBar.getItems().clear();
    	newIngredient.getItems().clear();
    	addAllergyBar.getItems().clear();
    	addShoppingIngredient.getItems().clear();
    	//searchAdditionalBox.getItems().clear();
    	addRestrictionBar.getItems().addAll("Vegan", "Lacto vegetarian", "Ovo vegetarian", 
    			"Pescetarian", "Lacto-ovo vegetarian");
    	addAllergyBar.getItems().addAll("Wheat-Free", "Gluten-Free", "Peanut-Free", 
    			"Tree Nut-Free", "Dairy-Free", "Egg-Free", "Seafood-Free", "Sesame-Free", 
    			"Soy-Free", "Sulfite-Free");
    }
	
	/*
	 ********************************************************** 
	 * ALL PURPOSE
	 **********************************************************
	 */
    private abstract class GuiBox extends GridPane{
    	public void remove() {};	
    	public RemoveButton getRemover(){
    		return null;
    	}    	
    }
    
    private class RemoveButton extends Button{
    	
    	public RemoveButton(final GuiBox parent){
    		this.setText("X");
    		this.setFont(Font.font("Verdana", FontWeight.BLACK,13));
    		this.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
                public void handle(ActionEvent e) {
    				parent.remove();
    			}
    		});
    	}
    }
    
	public void disableRemoves(ListView<? extends GuiBox> listview){
		for(GuiBox gb: listview.getItems()){
			gb.getRemover().setVisible(false);
		}
	}
	
	public void displayPleasantries(){
        welcome.setText("Welcome " + _account.getName());
        weather.setText("How's the weather in " + _account.getAddress());
    }
	
	/*
	 ********************************************************** 
	 * Profile
	 **********************************************************
	 */
	
	public void EditOrSaveAccountChanges(){
		System.out.println(profileEditor.getText());
		if(profileEditor.getText().equals("Edit")){
			nameField.setVisible(true);
			locationField.setVisible(true);
			nameLabel.setVisible(false);
			locationLabel.setVisible(false);
			nameField.setText(nameLabel.getText());
			locationField.setText(locationLabel.getText());
			profileEditor.setText("Save Changes");
		}
		else{
			nameField.setVisible(false);
			locationField.setVisible(false);
			nameLabel.setVisible(true);
			locationLabel.setVisible(true);
			nameLabel.setText(nameField.getText());
			locationLabel.setText(locationField.getText());
			_account.setName(nameLabel.getText());
			_account.setAddress(locationLabel.getText());
			_client.storeAccount(_account);
			profileEditor.setText("Edit");
		}
	}
	
	public void addRestrictionListListener(){
    	removeRestriction.setSelected(false);
    	disableRemoves(restrictionsList);
    	String name = addRestrictionBar.getValue();
    	if(name!=null){
	    	if(name.trim().length()!=0){
	    		_account.addRestriction(name);
	    		_client.storeAccount(_account, 2, name);
	    		populateRestrictions();
	    	}
    	}
	    addRestrictionBar.setButtonCell(new ListCell<String>() {
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
					setText("Select a Restriction to add it");
				}
			}
		});

    }
	
    public void populateRestrictions(){
    	ObservableList<RestrictionBox> listItems = FXCollections.observableArrayList(); 
    	for(String r: _account.getDietaryRestrictions()){
    		RestrictionBox box = new RestrictionBox(r);
    		listItems.add(box);
    	}
    	
    	restrictionsList.setItems(listItems);
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
    		_account.removeRestriction(_toDisplay);
    		_client.storeAccount(_account, 3, _toDisplay);
    		ObservableList<RestrictionBox> listItems = restrictionsList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
	
	public void removeRestrictions(){		
		for(RestrictionBox s: restrictionsList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
    public void restrictionComboListener(){
    	String text = addRestrictionBar.getEditor().getText();
    	System.out.println("hurr");
    	addRestrictionBar.getEditor().setText("Select a Restriction to add it");
    	List<String> suggs = null;
    	if(text.trim().length()!=0)
    		suggs = _engines.getRestrictionSuggestions(text.toLowerCase());
    	if(suggs!=null){
    		addRestrictionBar.getItems().clear();
    		addRestrictionBar.getItems().addAll(suggs);
    	}
    }
    
    public void addAllergyListener(){
    	removeAllergy.setSelected(false);
    	disableRemoves(allergiesList);
    	String name = addAllergyBar.getValue();
	    if(name!=null){
    		if(name.trim().length()!=0){
	    		_account.addAllergy(name);
	        	_client.storeAccount(_account, 4, name);
	        	populateAllergies();
	    	}
	    }
	    addAllergyBar.setButtonCell(new ListCell<String>() {
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
					setText("Select an Allergy to add it");
				}
			}
		});
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
    
    public void populateAllergies(){
    	ObservableList<AllergyBox> listItems = FXCollections.observableArrayList(); 
    	for(String a: _account.getAllergies()){
    		AllergyBox box = new AllergyBox(a);
    		listItems.add(box);
    	}
    	allergiesList.setItems(listItems);
    }
    
	public void removeAllergies(){		
		for(AllergyBox s: allergiesList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
	
	/*
	 ********************************************************** 
	 * Home Screen
	 **********************************************************
	 */
	
	private class UserIngredientBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public UserIngredientBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
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
	
    public void addIngredientListener(){
    	disableRemoves(fridgeList);
    	System.out.println("was removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
    	removeFridgeIngredient.setSelected(false);
    	System.out.println("now removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
    	String name = newIngredient.getValue();
	    if(name!=null){
    		if(name.trim().length()!=0){
	    		_account.addIngredient(new Ingredient(name.toLowerCase().trim()));
	    		_client.storeAccount(_account);
	    	}
	    }
    	populateUserFridge();
    	newIngredient.setValue("");
    	newIngredient.getItems().clear();
    }
    
    public void addIngredientListener(Event event) {
    	disableRemoves(fridgeList);
    	removeFridgeIngredient.setSelected(false);
    	String name = newIngredient.getValue();
	    if(name!=null){
    		if(name.trim().length()!=0){
	    		_account.addIngredient(new Ingredient(name.toLowerCase().trim()));
	    		_client.storeAccount(_account);
	    	}
	    }
    	populateUserFridge();
    	newIngredient.setValue("");
    	newIngredient.getItems().clear();
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
    
	public void removeIngredients(){		
		for(UserIngredientBox s: fridgeList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
    
    public void addShoppingListListener(Event event) {
    	disableRemoves(shoppingList);
    	removeShoppingIngredient.setSelected(false);
    	String name = addShoppingIngredient.getValue();
    	if(name!=null){
    		if(name.trim().length()!=0){
    			_account.addShoppingIngredient(new Ingredient(name.toLowerCase().trim()));
    			_client.storeAccount(_account);
    			populateShoppingList();
    		}
    		addShoppingIngredient.setValue("");
    		addShoppingIngredient.getItems().clear();
    		
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
    	    this.add(_remove, 0, 0);
    	    
    	}
    	
    	public void remove(){
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
    
    public void populateShoppingList(){
    	ObservableList<ShoppingIngredientBox> listItems = FXCollections.observableArrayList();  
    	shoppingList.setItems(listItems);
    	for(Ingredient i: _account.getShoppingList()){
    		ShoppingIngredientBox box = new ShoppingIngredientBox(i.getName());
    		listItems.add(box);
    	}
    	shoppingList.setItems(listItems);
    }
    
	public void removeShoppingIngredient(){		
		for(ShoppingIngredientBox s: shoppingList.getItems()){
			RemoveButton rButton = s.getRemover();
			rButton.setVisible(!rButton.isVisible());
		}
	}
    
	/*
	 ********************************************************** 
	 * Kitchen
	 **********************************************************
	 */

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
							setText("Select Kitchen");
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
				System.out.println("I have been clicked! " + kitchenSelector.getValue());
				
				//disable the thing that hides everything
				kitchenHide.setVisible(false);
				kitchenHide.setDisable(true);
				

				String id = kitchenSelector.getValue();
				if(id!= null){
					if(_client.getCurrentKitchen()!= null){
						if(!_client.getCurrentKitchen().getID().equals(id)){
							System.out.println(_client.getCurrentKitchen().getID() + " != " + id);
							System.out.println("SETTING CURRENT EVENT NAME TO NULL");
							_currentEventName = null;
							//clearEventPane();
							
						}
					}
					kitchenSelector.setButtonCell(new ListCell<String>() {						
						@Override
						protected void updateItem(String name, boolean empty) {
							super.updateItem(name, empty);
							
							if (name == null || empty) {
								setText("Select Kitchen");
							} else {
								//id.setText(kitchenIds.get(name).getName());
								setText(kitchenIds.get(name).getName());
							}
						}
					});
					
					
					//displayKitchen(kitchenIds.get(id));
				}
			}
		});
		
	}
	
	public void displayKitchen(KitchenName kn){
		System.out.println("I WANT TO DISPLAY KITCHEN: " + kn.getName() + "   -->  " + kn.getID());
		
		//Clearing/hiding new kitchen stuff
		//******************hideNewKitchenStuff();
		//******************clearEventPane();
		_client.setCurrentKitchen(kn);
		
		//Making sure selector displays correctly
		final String currentName = _client.getCurrentKitchen().getName();
		final String currentId = _client.getCurrentKitchen().getID();
		kitchenSelector.setValue(currentId);
		kitchenSelector.setButtonCell(new ListCell<String>() {
			@Override
			protected void updateItem(String name, boolean empty) {
				super.updateItem(name, empty);
				
				if (name == null || empty) {
					setText("why is this null");
				} else {
					//id.setText(kitchenIds.get(name).getName());
					setText(currentName);
				}
			}
		});
		
		Kitchen k = _client.getKitchens().get(kn);
		System.out.println("SHOULD DISPLAY KITCHEN: " + k);
		System.out.println("EVENTS ARE: " + k.getEvents());
		//******************kitchenDietList.getItems().clear();
		//******************for(String r: k.getDietaryRestrictions()){
		//******************	kitchenDietList.getItems().add(r);
		//******************}
		
		//******************kitchenAllergyList.getItems().clear();
		//******************for(String r: k.getAllergies()){
		//******************	kitchenAllergyList.getItems().add(r);
			//******************}
		
		
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
		
		//******************kitchenIngredientComboBox.getItems().clear();
		//******************for(Ingredient ing: _account.getIngredients()){
		//******************	kitchenIngredientComboBox.getItems().add(ing.getName());
		//******************}
		
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
		//******************kitchenAddChefField.setText("");
		
		//******************populateEventSelector();
		//******************System.out.println("ABOVE LOAD EVENT");
		//******************oadEvent();
	}

	public void reDisplayKitchen() {
		if(_client.getCurrentKitchen() != null){
			//System.out.println("I would redisplay");
			displayKitchen(_client.getCurrentKitchen());
		}
	}


	/*
	 ********************************************************** 
	 * Invite
	 **********************************************************
	 */
	
	public void popupInvite(){
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
//				try {
//					URL location = getClass().getResource("InviteChefWindow.fxml");
//					FXMLLoader fxmlLoader = new FXMLLoader();
//					fxmlLoader.setLocation(location);
//					fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
//					Parent p = (Parent) fxmlLoader.load(location.openStream());
//			        _inviteChefController = (InviteChefController) fxmlLoader.getController();
//			        Scene scene = new Scene(p);
//			        _inviteChef
//			        _panel.setScene(scene);
//				} catch (IOException e) {
//					System.out.println("ERROR: IN GUI 2 Frame");
//					e.printStackTrace();
//				}
    	
    		}
		});
	}
	
	public void checkAndSendEmail(String email){
		System.out.println("IN CHECK AND SEND EMAIL.");
		if(email != null){
			//Will call sendInviteEmail.
			_client.userInDatabase(email);
		}
	}
		
	public void sendInviteEmails(boolean userInDatabase){
		_inviteChefController.sendInviteEmails(userInDatabase);
	}

	public void inviteUserToKitchen(String toInvite){
		if(_client.getKitchens()!=null){
			Kitchen k = _client.getKitchens().get(_client.getCurrentKitchen());
			_client.addRequestedKitchenUser(toInvite, _account.getName(), _client.getCurrentKitchen());
			
			//instant display update
			Text t = new Text(toInvite + " (pending)");
			t.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
			t.setFill(Color.GRAY);
			kitchenChefList.getItems().add(t);
			
			String message = "Hi there, \n " + _account.getName() + "(" + _account.getID() +") "
					+ "wants you to join the kitchen, " + k.getName();
			message += ". To accept this invitation, you must log in and accept.";
			Sender.send(toInvite, message);

		}
	}
    
	
    @FXML void addFromMyFridgeListener(ActionEvent event) {
    }

    @FXML void addRtoEMode(ActionEvent event) {
    }

    @FXML void displayInvitations(MouseEvent event) {
    }


    @FXML void goToRecipeTab(ActionEvent event) {
    }

    @FXML void hideNewKitchenStuff(ActionEvent event) {
    }

    @FXML void ingredientComboListener(InputEvent event) {
    }

    @FXML void leaveKitchen(ActionEvent event) {
    }

    @FXML void newKitchenButtonListener(ActionEvent event) {
    }

    @FXML void newKitchenCreateButtonListener(ActionEvent event) {
    }

    @FXML void searchButtonListener(MouseEvent event) {
    }

    @FXML void shoppingListComboListener(InputEvent event) {
    }
    

}