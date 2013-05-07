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

import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import Email.Sender;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import client.Client;
import javafx.scene.input.DragEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
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
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    @FXML private Label NoSearchResults, communalDietPreferencesList, newKitchenLabel, numberOfInvites;
    @FXML private Button addFridgeIngredient, goToRecipeSearchButton;
    @FXML private ComboBox<?> addRecipeEventSelector;
    @FXML private Button addRecipeToEventButton;
    @FXML private ComboBox<String> addShoppingIngredient;
    @FXML private ImageView chefHat, envelope;
    @FXML private ListView<UserIngredientBox> fridgeList;
    @FXML private CheckBox getRecipeChecksButton;
    @FXML private Accordion ingredientsAccordion;
    @FXML private ListView<?> invitationsList;
    @FXML private ListView<Text> kitchenChefList;
    @FXML private Pane kitchenHide;
    @FXML private ListView<String> kitchenIngredientList;
    @FXML private ComboBox<String> kitchenSelector;
    @FXML private Button leaveKitchenButton;
    @FXML private ComboBox<String> newIngredient;
    @FXML private Text newKitchenActionText;
    @FXML private Button newKitchenButton, newKitchenCancelButton, newKitchenCreateButton;
    @FXML private TextField newKitchenNameField;
    @FXML private AnchorPane newKitchenPane;
    @FXML private FlowPane recipeFlow;
    @FXML private Tab recipeSearchTab, homeTab;
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
    @FXML private Label welcome, weather, nameLabel, locationLabel;
    @FXML private TextField nameField, locationField;
    @FXML private Button profileEditor;
    @FXML private ComboBox<String> addRestrictionBar, addAllergyBar; 
    @FXML private CheckBox removeAllergy, removeRestriction;
    @FXML private ListView<RestrictionBox> restrictionsList;
    @FXML private ListView<AllergyBox> allergiesList;
    @FXML private Label emailLabel;
    @FXML private AnchorPane kitchenJunk;
    @FXML private Label communalAllergiesList;
    @FXML private ListView<DraggableIngredient> kitchenUserIngredients;
    
    
    //Local Data
    private Client _client;
    private Account _account;
    private Map<KitchenName, Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    private Wrapper _api;
    private String _currentEventName;
    private KitchenPane _currentKitchenPane;
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
    	
    	populateSearchIngredients();
    	populateUserIngredientsInKitchen();
    	
    	tabPane.getSelectionModel().select(homeTab);
    	
    	kitchenJunk.setDisable(true);
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
	
	private class RestrictionBox extends GuiBox { 
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
	
	private class AllergyBox extends GuiBox {
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
	
	/*
	 ********************************************************** 
	 * Home Screen
	 **********************************************************
	 */
	
	private class UserIngredientBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;

    	public UserIngredientBox(String display) {
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);
    	}
    	
    	public void remove(){
    		System.out.println("removing ingredient " + _toDisplay);
    		Ingredient ing = new Ingredient(_toDisplay);
    		_account.removeIngredient(ing);
    		_client.storeAccount(_account, ing);
    		populateSearchIngredients();
    		populateUserIngredientsInKitchen();
    		ObservableList<UserIngredientBox> listItems = fridgeList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
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
    	populateSearchIngredients();
    	newIngredient.setValue(null);
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
    
    public void addShoppingListListener() {
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
				
				//disable the pane that hides everything
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
					
					
					displayKitchen(kitchenIds.get(id));
				}
			}
		});
		
	}
	
	public void displayKitchen(KitchenName kn){
		System.out.println("I WANT TO DISPLAY KITCHEN: " + kn.getName() + "   -->  " + kn.getID());
		
		//Clearing/hiding new kitchen stuff
		hideNewKitchenStuff();
		//******************clearEventPane();
		_client.setCurrentKitchen(kn);
		kitchenJunk.setDisable(false);
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
		
		
		StringBuilder restricts = new StringBuilder("");
		boolean first = true;
		for(String r: k.getDietaryRestrictions()){
			if(!first){
				restricts.append(", ");
			}
			restricts.append(r);
			first = false;
		}		
		communalDietPreferencesList.setText(restricts.toString());	
		if(communalDietPreferencesList.getText().length()==0){
			communalDietPreferencesList.setText("No restrictions listed for current users");
		}		
				
		StringBuilder allergies = new StringBuilder("");
		first = true;
		for(String r: k.getAllergies()){
			if(!first){
				allergies.append(", ");
			}
			allergies.append(r);
			first = false;
		}		
		communalAllergiesList.setText(allergies.toString());	
		if(communalAllergiesList.getText().length()==0){
			communalAllergiesList.setText("No allergies listed for current users");
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
	

    public void newKitchenButtonListener(){
    	newKitchenPane.setVisible(true);

    }
    
    public void hideNewKitchenStuff(){
    	newKitchenPane.setVisible(false);
    }

    
    public void newKitchenCreateButtonListener(){
    	String name = newKitchenNameField.getText();
    	
    	if (name.length() == 0){
    		newKitchenActionText.setText("Please enter a name.");
    		newKitchenActionText.setVisible(true);
    	} else if (_client.getKitchenNameSet().contains(name)){
    		newKitchenActionText.setText("You've already got a kitchen with that name");
    		newKitchenActionText.setVisible(true);
    	} else {
    		_client.setNewKitchen(name);
    		_client.createNewKitchen(name, _account);
    		//hideNewKitchenStuff();
    	}
    }
    
	public void leaveKitchen(){

		System.out.println("leavvving");
		hideNewKitchenStuff();
		kitchenSelector.setValue(null);
		
		_client.removeKitchen(_client.getCurrentKitchen());

		_account.removeKitchen(_client.getCurrentKitchen());
		_client.storeAccount(_account, _client.getCurrentKitchen().getID());
		kitchenHide.setVisible(true);
		_client.setCurrentKitchen(null);
		
	}
	
	private class DraggableIngredient extends Text {
		String _i;
		DraggableIngredient _self;
		
    	public DraggableIngredient(String ingredient) {
    		super(ingredient);
    		_i = ingredient;
    		_self = this;

			this.setOnDragDetected(new EventHandler <MouseEvent>() {
	            public void handle(MouseEvent event) {
	                /* drag was detected, start drag-and-drop gesture*/
	                System.out.println("onDragDetected");
	                
	                /* allow any transfer mode */
	                Dragboard db = _self.startDragAndDrop(TransferMode.ANY);
	                
	                /* put a string on dragboard */
	                ClipboardContent content = new ClipboardContent();
	                content.putString(_i);
	                db.setContent(content);
	                
	                event.consume();
	            }
	        });
    	}
    }
	
	@FXML void hoverIngredient(DragEvent event) {
		System.out.println("HVERRRR");
		Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }
	
	@FXML void acceptIngredient(DragEvent event){
		Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            System.out.println("Dropped: " + db.getString());
            success = true;
            _client.addIngredient(_client.getCurrentKitchen().getID(), new Ingredient(db.getString()));
        }
        event.setDropCompleted(success);
        event.consume();
	}
	
	
	
	
	
	public void populateUserIngredientsInKitchen(){
		kitchenUserIngredients.getItems().clear();
		for(Ingredient i: _account.getIngredients()){
			kitchenUserIngredients.getItems().add(new DraggableIngredient(i.getName()));
		}
	}
    
	/*
	 ********************************************************** 
	 * Invite
	 **********************************************************
	 */
	
	public void popupInvite(){
		final Controller2 control = this;
		
		Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
				try {
					URL location = getClass().getResource("InviteChefWindow.fxml");
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(location);
					fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
					Parent p = (Parent) fxmlLoader.load(location.openStream());
			        _inviteChefController = (InviteChefController) fxmlLoader.getController();
			        _inviteChefController.setController(control);
			        Stage stage = new Stage();
			        stage.setScene(new Scene(p));
			        stage.setTitle("InviteChef");
			        stage.initModality(Modality.APPLICATION_MODAL);
				    stage.show();
				} catch (IOException e) {
					System.out.println("ERROR: IN GUI 2 Frame");
					e.printStackTrace();
				}
    	
    		}
		});
	}
	
	public void inviteToJoinCWF(String email){
		String message = "Hi there, \n " + _account.getName() + "(" + _account.getID() +") "
				+ "wants to invite you to join Cooking with Friends, the social cooking coordinator.";
		message += "To accept this invitation, you must log in and accept.";
		System.out.println("SENDING TO : " + email);
		Sender.send(email, message);
		
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
    	tabPane.getSelectionModel().select(recipeSearchTab);
    }
    
    
    /*
	 ********************************************************** 
	 * Search Page
	 **********************************************************
	 */
    
    @FXML void searchButtonListener(MouseEvent event) {
    	NoSearchResults.setVisible(false);
		resultsFlow.getChildren().clear();
		
		if (_currentKitchenPane == null) {
			NoSearchResults.setText("Please select a kitchen");
			NoSearchResults.setVisible(true);
			return;
		}
			
		try { //Attempt to query API
			List<String> dummyList = Collections.emptyList(); 
			List<String> selectedIngredients = _currentKitchenPane.getSelectedIngredients();
			List<String> restrictions = _currentKitchenPane.getRestrictions();
			List<String> allergies = _currentKitchenPane.getAllergies();
			
			System.out.println("Searching for: " + searchField.getText());
			System.out.println("Ingredients: " + selectedIngredients.toString());
			System.out.println("Restrictions: " + restrictions.toString());
			System.out.println("Allergies: " + allergies.toString());
			
			List<? extends Recipe> results = _api.searchRecipes(searchField.getText(), selectedIngredients, dummyList, restrictions, allergies);
			
			if (results.size() == 0) {
				System.out.println("no results!!");
				String message = "Your search didn't yield any results.\n You searched for:  /'" + searchField.getText() + "/'\n";
				if (selectedIngredients.size() != 0) {
					message += "with required ingredients: ";
					for (int i = 0; i < selectedIngredients.size(); i++){
						message += selectedIngredients.get(i);
						if(i != selectedIngredients.size() - 1)
							message += ", ";
					}
				}
				NoSearchResults.setText(message);
				NoSearchResults.setVisible(true);
			}
			else {
				for (Recipe recipe : results)
					resultsFlow.getChildren().add(new RecipeBox(recipe));
			}
		} catch (IOException ex) {
			NoSearchResults.setText("Error querying API -- is your internet connection down?");
			NoSearchResults.setVisible(true);
		}
    }
    
    private class RecipeBox extends VBox {
    	private Recipe _recipe;
    	public RecipeBox(Recipe recipe) {
    		super();
    		_recipe = recipe;
    		
    		this.getStyleClass().add("recipeBox");
			this.setAlignment(Pos.CENTER);
			this.setPrefWidth(150);
			this.setMaxWidth(150);
			this.setPrefHeight(80);
			this.setMaxHeight(80);
			
    		Label recipeLabel = new Label(recipe.getName());
    		recipeLabel.setMaxWidth(140);
    		recipeLabel.setWrapText(true);
    		this.getChildren().add(recipeLabel);
    		if (recipe.hasThumbnail()) {
    			Image recipeThumbnail = new Image(recipe.getThumbnailUrl(), 80, 80, true, true, true); 
    			ImageView imageV = new ImageView(recipeThumbnail);
    			imageV.getStyleClass().add("recipeThumbnail");
    			this.getChildren().add(imageV);
    		}
    		
			this.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					createPopup(_recipe);					
				}
			});
    	}
    }
    
    private void createPopup(Recipe recipe) {
    	System.out.println("TODO: Create this popup (with buttons to add a recipe to any kitchen)");
    
    	try {
	    	Recipe completeRecipe = _api.getRecipe(recipe.getID());
	    	
	    	URL location = getClass().getResource("RecipeWindow.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent p = (Parent) fxmlLoader.load(location.openStream());
			RecipeController recipeControl = (RecipeController) fxmlLoader.getController();
			recipeControl.setUp(completeRecipe, _client, this);
			
			Stage stage = new Stage();
	        stage.setScene(new Scene(p));
	        stage.setTitle("View Recipe");
	        stage.initModality(Modality.APPLICATION_MODAL);
		    stage.show();		
    	} catch (IOException ex) {
    		NoSearchResults.setText("Error querying API -- is your internet connection down?");
			NoSearchResults.setVisible(true);
    	}
	}
    
    public void populateSearchIngredients() {
    	List<KitchenPane> kitchenPanes = new ArrayList<>();
        kitchenPanes.add(new KitchenPane("My Fridge", _account.getIngredients(), _account.getDietaryRestrictions(), _account.getAllergies()));
        
        for (Kitchen kitchen : _kitchens.values())
        	kitchenPanes.add(new KitchenPane(kitchen.getName(), kitchen.getIngredients(), kitchen.getDietaryRestrictions(), kitchen.getAllergies()));
      
        ingredientsAccordion.getPanes().clear();
        ingredientsAccordion.getPanes().addAll(kitchenPanes);
        ingredientsAccordion.setExpandedPane(kitchenPanes.get(0));
        _currentKitchenPane = kitchenPanes.get(0);
	}
    
    private class KitchenPane extends TitledPane {
    	private List<CheckBox> _ingredientBoxes;
    	private KitchenPane _thisPane;
    	private Set<String> _allergies, _restrictions;
    	
    	public KitchenPane(String name, Set<Ingredient> ingredients, Set<String> restrictions, Set<String> allergies) {
    		super();
    		_ingredientBoxes = new ArrayList<>();
    		_thisPane = this;
    		_allergies = allergies;
    		_restrictions = restrictions;
    		
    		this.setText(name); 	
    		this.setContent(this.makeIngredientsList(ingredients)); 	
    		this.expandedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					if (arg2) 
						_currentKitchenPane = _thisPane;
					else 
						_currentKitchenPane = null;
				}
    		});
    	}
    	
    	public List<String> getAllergies() {
			return new ArrayList<String>(_allergies);
		}

		public List<String> getRestrictions() {
			return new ArrayList<String>(_restrictions);
		}

		public List<String> getSelectedIngredients() {
			List<String> selectedIngredients = new ArrayList<>();
			for (CheckBox ingredientBox : _ingredientBoxes) {
				if (ingredientBox.isSelected()) {
					selectedIngredients.add(ingredientBox.getText());
				}
			}
			return selectedIngredients;
		}

    	public ListView<CheckBox> makeIngredientsList(Set<Ingredient> ingredients) {
    		ListView<CheckBox> ingredientsView = new ListView<>();    		
    		for (Ingredient ing : ingredients)
    			_ingredientBoxes.add(new CheckBox(ing.getName()));
    		CheckBox selectAll = new SelectAllBox(_ingredientBoxes);
    		ingredientsView.getItems().add(selectAll);
    		ingredientsView.getItems().addAll(_ingredientBoxes);
			return ingredientsView;
    	}
    }
    
    private class SelectAllBox extends CheckBox {
    	private List<CheckBox> _associatedBoxes;
    	private CheckBox _allBox;
    	
    	public SelectAllBox(List<CheckBox> boxes) {
    		_associatedBoxes = boxes;
    		this.setText("Select all");
    		this.getStyleClass().add("selectAllBox");
    		_allBox = this;
    		
    		this.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	for (CheckBox box : _associatedBoxes) {
		        		box.setSelected(_allBox.isSelected());
		        	}
			    }
			});
    	}	
    }
    
    /**
     * COMBO BOX LISTENERS. _-----------------------------------------------
     */
    
    /**
     * Listener for adding ingredient to shopping list.
     */
    public void shoppingListComboListener(){
    	String text = addShoppingIngredient.getEditor().getText();
    	if(text != null){
    		addShoppingIngredient.getItems().clear();
    		List<String> suggs = null;
    		if(text.trim().length()!=0){
    			System.out.println("TEXT: " + text);
	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());

	    	    if(suggs!=null){
		    		addShoppingIngredient.getItems().clear();
		    		addShoppingIngredient.getItems().addAll(suggs);
		    	}
    		}
    	}
    	else{
    		 addShoppingListListener();
    	}
    }
    
    public void ingredientComboListener(){
    	String text = newIngredient.getEditor().getText();
    	if(text != null){
    		newIngredient.getItems().clear();
    		List<String> suggs = null;
    		if(text.trim().length()!=0){
    			System.out.println("TEXT: " + text);
	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());

	    	    if(suggs!=null){
	    	    	newIngredient.getItems().clear();
	    	    	newIngredient.getItems().addAll(suggs);
		    	}
    		}
    	}
    	else{
    		 addShoppingListListener();
    	}
    }
    
    

}