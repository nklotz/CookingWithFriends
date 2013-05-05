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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.util.Callback;
import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import Email.Sender;
import UserInfo.Account;
import UserInfo.Event;
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
    private FlowPane recipeFlow;
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
    private ListView<EventMenuBox> eventMenuList; //*********************************************************
    @FXML
    private ToggleButton eventMenuRemoveButton;
    @FXML
    private ComboBox<String> eventSelector;  //*********************************************************
    @FXML
    private Button eventShoppingAddButton;
    @FXML
    private ListView<EventShoppingListBox> eventShoppingList; //*********************************************************
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
    private Button newKitchenButton;
    @FXML
    private Button leaveKitchenButton;
    @FXML
    private Label invalidEmailError;
    @FXML 
    private ListView<InvitationBox> invitationsList;
    @FXML
    private ImageView envelope;
    @FXML
    private Label numberOfInvites;
    @FXML
    private Button goToRecipeSearchButton;
    @FXML
    private ComboBox<String> addRecipeEventSelector;
    @FXML
    private Button addRecipeToEventButton;
    @FXML
    private ListView<String> kitchenRecipeList;
    @FXML
    private CheckBox getRecipeChecksButton;
    @FXML
    private TextField newKitchenNameField;
    @FXML
    private Button newKitchenCreateButton;
    @FXML
    private Label newKitchenLabel;
    @FXML
    private Text newKitchenActionText;
    @FXML
    private Button newKitchenCancelButton;
    @FXML
    private TextArea createEventField;
    @FXML
    private Button createEventButton;
    @FXML
    private TextArea createDateField;
    @FXML
    private ComboBox<String> eventShoppingComboBox;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab recipeSearchTab;
    @FXML
    private Pane kitchenHide;
    @FXML
    private Label NoSearchResults;
    
    @FXML
    private Pane inviteBigPane;
    
    @FXML ComboBox<String> searchAdditionalBox;
    @FXML ListView<SearchAdditionBox> searchAdditionalList;
    
    //Local Data
    private Client _client;
    private Account _account;
    private Map<KitchenName, Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    private Wrapper _api;
    private String _currentEventName;
    private KitchenPane _currentKitchenPane;
    private HashSet<String> _setOfAdditionalSearchIngs = new HashSet<String>();//Set of additional ingredients for search.
    
    
    @FXML
    void initialize() {
    	System.out.println("HOLY SHIT I DIDN'T KNOW THIS METHOD EVER GETS CALLED?");
    }
    
    private abstract class GuiBox extends GridPane{
    	//*********************************************************
    	public void remove() {};
    	
    	public RemoveButton getRemover(){
    		return null;
    	}
    	
    	
    	
    }
    
    private class RemoveButton extends Button{
    	
    	public RemoveButton(final GuiBox parent){
    		//this.setGraphic(new ImageView(xImage));
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
    
    private class SearchAdditionBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;
    	
    	public SearchAdditionBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    
    	}
    	
    	public void remove(){
    		Ingredient ing = new Ingredient(_toDisplay);
    		//_account.removeShoppingIngredient(ing);
    		//_client.storeAccount(_account);
    		ObservableList<SearchAdditionBox> listItems = searchAdditionalList.getItems();
    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    
    private class EventMenuBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;
    	
    	public EventMenuBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    
    	}
    	
    	public void remove(){
//    		Recipe rec = new YummlyRecipe(_toDisplay);
//    		_account.removeShoppingIngredient(ing);
//    		_client.storeAccount(_account);
//    		ObservableList<ShoppingIngredientBox> listItems = shoppingList.getItems();
//    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
    	}
    }
    
    private class EventShoppingListBox extends GuiBox{
    	protected String _toDisplay;
    	protected RemoveButton _remove;
    	
    	public EventShoppingListBox(String display){
    		_toDisplay = display;
    	    Label ingred = new Label(display);
    	    this.add(ingred, 1, 0);
    	    _remove = new RemoveButton(this);
    	    _remove.setVisible(false);
    	    this.add(_remove, 0, 0);
    	    
    	}
    	
    	public void remove(){
//    		Ingredient ing = new Ingredient(_toDisplay);
//    		_account.removeShoppingIngredient(ing);
//    		_client.storeAccount(_account);
//    		ObservableList<ShoppingIngredientBox> listItems = shoppingList.getItems();
//    		listItems.remove(this);
    	}
    	
    	public RemoveButton getRemover(){
    		return _remove;
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

    		if(message != null){
	    		Button accept = new Button("Accept");
	    		accept.setOnAction(new EventHandler<ActionEvent>(){
	    			@Override
	    			public void handle(ActionEvent e){
	    			//	System.out.println("Accept invitatioN!!!");
	    				
	    				_account.getInvitions().remove(_invite.getKitchenID());
	    				_account.getKitchens().add(_invite.getKitchenID());
	    				_client.storeAccount(_account);
	    				_client.addActiveKitchenUser(_invite.getKitchenID().getID(), _account);
	    				populateInvitations();
	    			}
	    		});
	    		
	    		Button decline = new Button("Decline");
	    		decline.setOnAction(new EventHandler<ActionEvent>(){
	    			@Override
	    			public void handle(ActionEvent e){
	    			//	System.out.println("REJECT invitatioN!!!");
	    				
	    				_account.getInvitions().remove(_invite.getKitchenID());
	    				_client.storeAccount(_account);
	    				_client.removeRequestedKitchenUser(_invite.getKitchenID().getID());
	    				populateInvitations();
	    			}
	    		});
	    		
	    		this.add(message, 0, 0);
	    		this.add(accept, 1, 0);
	    		this.add(decline, 2, 0);
    		}

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
    	_api = new YummlyAPIWrapper();
    	
    	kitchenHide.setVisible(true);
    	NoSearchResults.setVisible(false);
    	
    	initializeComboBoxes();
    	populateUserFridge();
    	populateUserRecipes();
    	populateShoppingList();
    	populateAllergies();
    	populateRestrictions();
    	populateInfo();
    	populateKitchenSelector();
    	populateInvitations();
    	
    	numberOfInvites.setText(Integer.toString(_account.getInvitions().size()));
    	
    	//Set up search page
    	populateSearchIngredients();
    	setUpSearchTab();
    	
    	
    	List<String> list = new ArrayList<String>();
    	addShoppingIngredient.getItems().addAll(list);
    	newIngredient.getItems().addAll(list);
    	//addShoppingIngredient.
    //	initializeAutocorrect();
    }
   
    public void createEventListener(){
    	String name = createEventField.getText();
    	String date = createDateField.getText();
    	if(name!=null && date!=null && name.trim().length()!= 0 && date.trim().length()!=0){
    		HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
    		if(kitchens.get(_client.getCurrentKitchen())!=null){
    			Kitchen k = kitchens.get(_client.getCurrentKitchen());
    			Event event = new Event(name, date, k);
            	_client.addEvent(k.getID(), event);
            	//populateEventSelector();
            	
    		}
    		
    	}
    	
   // 	System.out.println("TEXT: " + createEventField.getText());
    }
    
    
    /***************************************************************************************
     * Search Page Methods
     */
    private void setUpSearchTab() {
    	searchButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
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
					
					List<? extends Recipe> results = _api.searchRecipes(searchField.getText(), selectedIngredients, dummyList, restrictions, allergies);
					
					if (results.size() == 0) {
						System.out.println("no results!!");
						String message = "Your search didn't yield any results.\n You searched for:  /'" + searchField.getText() + "/'\n";
						if (selectedIngredients.size() != 0){
							message += "with required ingredients: ";
							for (int i = 0; i < selectedIngredients.size(); i++){
								message += selectedIngredients.get(i);
								if(i != selectedIngredients.size() - 1){
									message += ", ";
								}
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
		});
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
    		if (recipe.hasImage()) {
    			Image recipeThumbnail = new Image(recipe.getImageUrl(), 80, 80, true, true, true); 
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
    	//TODO: create beautiful popup
    	System.out.println("TODO: Create this popup (with buttons to add a recipe to any kitchen)");
       
    	final Popup popup = new Popup(); 
        popup.setX(300); 
        popup.setY(200);
        popup.getContent().addAll(new Circle(25, 25, 50, Color.AQUAMARINE));
     
        //popup.show(arg0, arg1, arg2)
           
    	//Perhaps do it in scenebuilder?
	}
    
    protected void populateSearchIngredients() {
    	List<KitchenPane> kitchenPanes = new ArrayList<>();
        kitchenPanes.add(new KitchenPane("My Fridge",
        		_account.getIngredients(),
        		_account.getDietaryRestrictions(), 
        		_account.getAllergies()
        		));
        
        for (Kitchen kitchen : _kitchens.values()) {
        	kitchenPanes.add(new KitchenPane(kitchen.getName(),
        			kitchen.getIngredients(),
        			kitchen.getDietaryRestrictions(),
        			kitchen.getAllergies()
        		));
        }
        
        ingredientsAccordion.getPanes().clear();
        ingredientsAccordion.getPanes().addAll(kitchenPanes);
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
					if (arg2) _currentKitchenPane = _thisPane;
					else _currentKitchenPane = null;
					
					System.out.println("Updated open pane: " + _currentKitchenPane);
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
    
    /****************************************************************************************
     * END Search page methods
     */
    
    

	public void initializeComboBoxes(){
		kitchenSelector.getItems().clear();
		eventSelector.getItems().clear();
		eventShoppingComboBox.getItems().clear();
    	addRestrictionBar.getItems().clear();
    	newIngredient.getItems().clear();
    	addAllergyBar.getItems().clear();
    	addShoppingIngredient.getItems().clear();
    	searchAdditionalBox.getItems().clear();
    	addRestrictionBar.getItems().addAll("Vegan", "Lacto vegetarian", "Ovo vegetarian", 
    			"Pescetarian", "Lacto-ovo vegetarian");
    	addAllergyBar.getItems().addAll("Wheat-Free", "Gluten-Free", "Peanut-Free", 
    			"Tree Nut-Free", "Dairy-Free", "Egg-Free", "Seafood-Free", "Sesame-Free", 
    			"Soy-Free", "Sulfite-Free");
    	//TODO: READ THESE FROM SERVER? (just don't hard code them?) is this worth doing?!/!
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
    
    public void addShoppingListListener(){
    	//addFridgeIngredient
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
    
    public void addEventShoppingIngredientListener(){
    	disableRemoves(eventShoppingList);
    	eventShoppingRemoveButton.setSelected(false);
    	String name = eventShoppingComboBox.getValue();
    	if(name!=null){
    		if(name.trim().length()!=0){
    			HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
    			 
   	    	if(kitchens!=null){
    	    		Kitchen k = kitchens.get(_client.getCurrentKitchen());
    	    		if(k!=null){
    	    			System.out.println("adding " + name + " to event " + _currentEventName + " in kitchen " + k.getName());
    	   	    		_client.addIngToEventShopping(_currentEventName, k.getID(), new Ingredient(name));
    	   	    		System.out.println("KITCHEN: " + k);
    	    		}
    	    	}
    		}
    	}
    	loadEvent();
    }
    
    
    public void addSearchBoxListener(){
    	//disableRemoves(searchAdditionalList);
    	//searchAdditionalList.getItems().clear();
    	
    	//String ing = searchAdditionalBox.getEditor().getText();
    	String ing = searchAdditionalBox.getValue();
    	System.out.println("ADD SEARCH LSITENER!!: " + ing);
    	if(ing!=null&&ing.trim().length()!=0){
    		_setOfAdditionalSearchIngs.add(ing);
    		populateAddSearchBox();	
    	}
    		
    	
    }
    
    
    public void addIngredientListener(){
    	//System.out.println("add ingredient listener!: " + newIngredient.getValue());
    	//addFridgeIngredient
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
        populateSearchIngredients();
    	newIngredient.setValue("");
    	newIngredient.getItems().clear();
    	reDisplayKitchen();
    }
    
    
    public void eventShoppingComboListener(){
    	String text = eventShoppingComboBox.getEditor().getText();
    //	if(eventShoppingComboBox.getValue()!=null){
    	if(text!=null){
    		eventShoppingComboBox.getItems().clear();
	    	List<String> suggs = null;
	    	if(text.trim().length()!=0){
	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
	    	}
	    	if(suggs!=null){
	    		eventShoppingComboBox.getItems().addAll(suggs);
	    	}
    	}
    //	else{
    		//TODO: WHY DOES THIS WORK FOR SHOPPING BUT NOT INGREDIENTS
    		//addIngredientListener();
    //	}
    }
    
    public void newKitchenButtonListener(){
    	newKitchenCreateButton.setVisible(true);
    	newKitchenNameField.setVisible(true);
    	newKitchenLabel.setVisible(true);
    	newKitchenCancelButton.setVisible(true);
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
    	}
    }
    
    public void searchComboListener(){
    	String text = searchAdditionalBox.getEditor().getText();
    	System.out.println("SEARCH COMBO LISTENER: " + text);
    	if(text!=null){
    		searchAdditionalBox.getItems().clear();
	    	List<String> suggs = null;
	    	if(text.trim().length()!=0){
	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
	    	}
	    	if(suggs!=null){
	    		searchAdditionalBox.getItems().addAll(suggs);
	    	}
    	}
    	else{
    		//TODO: WHY DOES THIS WORK FOR SHOPPING BUT NOT INGREDIENTS
    		//addSearchBoxListener();
    	}
    }
    
    /**
     * Listener for new ingredient box.
     */
    public void ingredientComboListener(){
    	String text = newIngredient.getEditor().getText();

    	if(newIngredient.getValue()!=null){
    		newIngredient.getItems().clear();
	    	List<String> suggs = null;
	    	if(text.trim().length()!=0){
	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
	    	}
	    	if(suggs!=null){
	    		newIngredient.getItems().addAll(suggs);
	    	}
    	}
    	else{
    		//TODO: WHY DOES THIS WORK FOR SHOPPING BUT NOT INGREDIENTS
    		addIngredientListener();
    	}
    	
    }
    
    /**
     * Listener for adding ingredient to shopping list.
     */
    public void shoppingListComboListener(){
    	String text = addShoppingIngredient.getEditor().getText();
    	if(addShoppingIngredient.getValue() != null){
    		addShoppingIngredient.getItems().clear();
    		List<String> suggs = null;
    	    if(text!=null){
        		if(text.trim().length()!=0)
    	    		suggs = _engines.getIngredientSuggestions(text.toLowerCase());
    	    }
    	    if(suggs!=null){
	    		addShoppingIngredient.getItems().clear();
	    		addShoppingIngredient.getItems().addAll(suggs);
	    	}
    	}
    	else{
    		 addShoppingListListener();
    	}
    }
    
    //TODO: confirm this is not being used, delete it
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
    	//CLEAR SOMEHOW?
    	for(Recipe r: _account.getRecipes()){
    		recipeFlow.getChildren().add(new RecipeBox(r));
    	}
    }
    
    
    public void populateEventMenu(){
    	ObservableList<EventMenuBox> listItems = FXCollections.observableArrayList();  
    	eventMenuList.getItems().clear();
    	String eventName = eventSelector.getValue();
    	HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
 
    	if(kitchens!=null){
    		Kitchen k = kitchens.get(_client.getCurrentKitchen());

    		
    		if(k!=null){
    			Event e = k.getEvent(new Event(eventName, null, k));

    			
    			for(Recipe r: e.getMenuRecipes()){
    	    		EventMenuBox b = new EventMenuBox(r.getName());
    	    		listItems.add(b);
    	        	eventMenuList.setItems(listItems);
    	    	}
    		}
    	}
    }
    
    public void populateEventShoppingList(){
    	System.out.println("IN POPULATE SHOPPING LIST!!!!!!!!!");
    	ObservableList<EventShoppingListBox> listItems = FXCollections.observableArrayList();  
    	eventShoppingList.getItems().clear();
    	String eventName = eventSelector.getValue();
    	HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
 
    	if(kitchens!=null){
    		Kitchen k = kitchens.get(_client.getCurrentKitchen());
    		if(k!=null){
    			Event e = k.getEvent(new Event(eventName, null, k));
    			for(Ingredient i: e.getShoppingIngredients()){
    				System.out.println("ADDING INGREDIENT: " + i);
    				EventShoppingListBox b = new EventShoppingListBox(i.getName());
    	    		listItems.add(b);
    	    		eventShoppingList.setItems(listItems);
    	    	}
    		}
    	}
    }
    
    public void populateAddSearchBox(){
		System.out.println("POPULATING BOX");
		//SearchAdditionBox box = new SearchAdditionBox(ing);
		ObservableList<SearchAdditionBox> listItems = FXCollections.observableArrayList(); 
		searchAdditionalList.setItems(listItems);
		//searchAdditionalList.setItems(listItems);
    	for(String i: _setOfAdditionalSearchIngs){
    		if(i.trim().length()!=0){
    			SearchAdditionBox box = new SearchAdditionBox(i);
        		listItems.add(box);
    		}
    		
    	}
    	searchAdditionalList.setItems(listItems);
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
	        assert addAllergyBar != null : "fx:id=\"addAllergyBar\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
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
	        assert recipeFlow != null : "fx:id=\"recipeFlow\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
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
	        assert envelope != null : "fx:id=\"envelope\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenButton != null : "fx:id=\"newKitchenButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert leaveKitchenButton != null : "fx:id=\"leaveKitchenButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert goToRecipeSearchButton != null : "fx:id=\"goToRecipeSearchButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addRecipeToEventButton != null : "fx:id=\"addRecipeToEvent\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert addRecipeEventSelector != null : "fx:id=\"addRecipeEventSelector\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert kitchenRecipeList != null : "fx:id=\"kitchenRecipeList\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert getRecipeChecksButton != null : "fx:id=\"getRecipeChecksButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenNameField != null : "fx:id=\"newKitchenNameField\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenCreateButton != null : "fx:id=\"newKitchenCreateButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenLabel != null : "fx:id=\"newKitchenLabel\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenActionText != null : "fx:id=\"newKitchenActionText\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert newKitchenCancelButton != null : "fx:id=\"newKitchenCancelButton\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert recipeSearchTab != null : "fx:id=\"recipeSearchTab\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        assert inviteBigPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'CookingWithFriends.fxml'.";
	        
	}
	
	public void goToRecipeTab(){
		tabPane.getSelectionModel().select(recipeSearchTab);
	}
	
	public void addRtoEMode(){
		if (!getRecipeChecksButton.isSelected()){
			addRecipeEventSelector.setVisible(false);
			addRecipeToEventButton.setVisible(false);
		} else {
			addRecipeEventSelector.setVisible(true);
			addRecipeToEventButton.setVisible(true);
		}
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
		//System.out.println("name: " + _account.getName());
		nameLabel.setText(_account.getName());
	//	System.out.println("address: " + _account.getAddress());
		locationLabel.setText(_account.getAddress());
		emailLabel.setText(_account.getID());
		nameLabel.setVisible(true);
		locationLabel.setVisible(true);
		emailLabel.setVisible(true);
		locationField.setVisible(false);
		nameField.setVisible(false);
	}
	
	public void editInfo(){
		//System.out.println("Editing info: "+ editProfile.getText());
		
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
	
	public void loadEvent(){
		System.out.println("TOP OF LOAD EVENT!!!!!!!!!");
		if(eventSelector.getValue()!= null){
			System.out.println("setting current event to " + eventSelector.getValue());
			_currentEventName = eventSelector.getValue();
		}
		else if (_currentEventName != null){
			eventSelector.setEditable(true);
			eventSelector.setValue(_currentEventName);
			eventSelector.setEditable(false);
		}

		System.out.println("EDITOR TEXT: " + eventSelector.getEditor().getText());
		if(eventSelector.getValue()!=null){
		//if(eventSelector.getEditor().getText().trim().length()!=0){
			populateEventMenu();
			populateEventShoppingList();
		}
		
//		if(eventSelector.getValue()== null && _currentEventName !=null){
//			System.out.println("value was null but current event is " + _currentEventName);
//			eventSelector.setEditable(true);
//			eventSelector.setValue(_currentEventName);
//			eventSelector.setEditable(false);
//			System.out.println("I WOULD display event: " + _currentEventName);
//		}
//		else if(eventSelector.getValue() != null && !eventSelector.getValue().equals("Select an Event")){
//			System.out.println("setting current event to " + eventSelector.getValue());
//			_currentEventName = eventSelector.getValue();
//			populateEventMenu();
//			populateEventShoppingList();
//		}
	}
	
	public void populateEventSelector(){
		HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
		Kitchen k = kitchens.get(_client.getCurrentKitchen());
		
		
		//If kitchen doesn't equal null.
		if(k!=null){
			HashSet<String> names = k.getEventNames();
			eventSelector.getItems().clear();
			eventSelector.getItems().addAll(k.getEventNames());
		}
		System.out.println("IN POPOULATE EVENT SELECTOR: SETTING SELECTOR TO FIRST ITEM");
		eventSelector.setValue(eventSelector.getItems().get(0));
		
//		eventSelector.setEditable(true);
//		System.out.println("CURRENT EVENT NAME: " + _currentEventName);
//		eventSelector.setValue(_currentEventName);
//
//		eventSelector.setEditable(false);
		
//		if(_currentEventName != null){
//			eventSelector.setEditable(true);
//			eventSelector.setValue(_currentEventName);
//			eventSelector.setEditable(false);
//			System.out.println("editable? " + eventSelector.isEditable());
//		}
//		else{
//			eventSelector.setEditable(true);
//			eventSelector.setValue(null);
//			eventSelector.setEditable(false);
//			System.out.println("editable? " + eventSelector.isEditable());
//
//		}
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
				//disable the thing that hides everything
				kitchenHide.setVisible(false);
				kitchenHide.setDisable(true);
				

				String id = kitchenSelector.getValue();
				if(id!= null){
					_currentEventName = null;
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
    
	public void hideNewKitchenStuff(){
		newKitchenLabel.setVisible(false);
		newKitchenNameField.setVisible(false);
		newKitchenNameField.setText("");
		newKitchenCreateButton.setVisible(false);
		newKitchenActionText.setVisible(false);
		newKitchenActionText.setText("");
		newKitchenCancelButton.setVisible(false);
	}
	
	public void displayKitchen(KitchenName kn){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("I WANT TO DISPLAY KITCHEN: " + kn.getName() + "   -->  " + kn.getID());
		
		//Clearing/hiding new kitchen stuff
		hideNewKitchenStuff();
		
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
		kitchenAddChefField.setText("");
		
		populateEventSelector();
		System.out.println("ABOVE LOAD EVENT");
		loadEvent();
		
	}

	public void reDisplayKitchen() {
		if(_client.getCurrentKitchen() != null){
			//System.out.println("I would redisplay");
			displayKitchen(_client.getCurrentKitchen());
		}
	}
	
	public void addIngredientToKichen(){

		String ing = kitchenIngredientComboBox.getValue();
		if(ing!=null){
			if(!ing.equals("Select Ingredient from Fridge")){
				_client.addIngredient(_client.getCurrentKitchen().getID(), new Ingredient(ing));
			}
		}
		
		
	}
	
	public void sendInviteEmails(boolean userInDatabase){
		String email = kitchenAddChefField.getText();
		if(email != null){
			//TODO: PUT ALL THIS IN.
			//if(_client.userExists(email)){

			if(isValidEmail(email)){
				if(userInDatabase){
					if(isValidEmail(email)){
						Kitchen k = null;
						if(_client.getKitchens()!=null){
							k = _client.getKitchens().get(_client.getCurrentKitchen());
							_client.addRequestedKitchenUser(email, _account.getName(), _client.getCurrentKitchen());
							
							//instant display update
							Text t = new Text(kitchenAddChefField.getText() + " (pending)");
							t.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
							t.setFill(Color.GRAY);
							kitchenChefList.getItems().add(t);
							
							String message = "Hi there, \n " + _account.getName() + "(" + _account.getID() +") "
									+ "wants you to join the kitchen, " + k.getName();
							message += ". To accept this invitation, you must log in and accept.";
							Sender.send(kitchenAddChefField.getText(), message);
							//System.out.println("SENT TO: " + message);
						}
					}
				}
				else{
					PopupWindow pop = new Popup();
					pop.setX(100);
					pop.setY(100);
					pop.setWidth(100);
					pop.setHeight(100);
					pop.show(inviteBigPane, 100, 100);
					pop.setAutoHide(true);
					System.out.println("SHOULD DISPLAY MESSAGE ABOUT NOT BEING IN EMAIL.");
					invalidEmailError.setText(email + "\nis not a member of CWF.\nWould you like\nto invite"
							+ " them to join?");
					invalidEmailError.setVisible(true);
				}
			}
			
			
			
			else{
				invalidEmailError.setVisible(true);
			}
		}
		kitchenAddChefField.setText("");
	}
	public void checkAndSendEmail(){
		System.out.println("IN CHECK AND SEND EMAIL.");
		String email = kitchenAddChefField.getText();
		if(email != null){
			_client.userInDatabase(email);
		}
	}
		
//		String email = kitchenAddChefField.getText();
//		if(email != null){
//			//TODO: PUT ALL THIS IN.
//			
//			if(_client.userInDatabase(email)){
//				if(isValidEmail(email)){
//					Kitchen k = null;
//					if(_client.getKitchens()!=null){
//						k = _client.getKitchens().get(_client.getCurrentKitchen());
//						_client.addRequestedKitchenUser(email, _account.getName(), _client.getCurrentKitchen());
//						String message = "Hi there, \n " + _account.getName() + "(" + _account.getID() +") "
//								+ "wants you to join the kitchen, " + k.getName();
//						message += ". To accept this invitation, you must log in and accept.";
//						Sender.send(kitchenAddChefField.getText(), message);
//						//System.out.println("SENT TO: " + message);
//					}
//				}
//			}
//			
//			else{
//				invalidEmailError.setVisible(true);
//			}
//		}
//		kitchenAddChefField.setText("");

	
	public void clearError(){
		invalidEmailError.setVisible(false);
	}
	
	public boolean isValidEmail(String email){
		if(email.trim().length()!=0){
			String[] atSplit = email.split("\\@");
			if(atSplit.length==2){
				String[] dotSplit = atSplit[1].split("\\.");
				if(dotSplit.length>1){
					return true;
				}
			}
		}
		return false;
	}
	
	public void leaveKitchen(){

		System.out.println("leavvving");
		_client.removeKitchen(_client.getCurrentKitchen());

		_account.removeKitchen(_client.getCurrentKitchen());
		_client.storeAccount(_account, _client.getCurrentKitchen().getID());
		kitchenSelector.setValue(null);
		kitchenHide.setVisible(true);
		_client.setCurrentKitchen(null);
		
	}
	
	public void populateInvitations(){
			
			invitationsList.getItems().clear();
			HashMap<KitchenName, Invitation> invites = _account.getInvitions();
			numberOfInvites.setText(Integer.toString(invites.size()));

			System.out.println("user has " + invites.size() + " invitations!!!");
			if(invites.size()==0){
				invitationsList.setVisible(false);
			}
			else{
				for(KitchenName kn: _account.getInvitions().keySet()){
					invitationsList.getItems().add(new InvitationBox(invites.get(kn)));
				}
			}
		
	}
	
	public void removeItem(){
	//	System.out.println("REMOVE");
	}
	
	public void displayInvitations(){
		if(invitationsList.isVisible()){
				invitationsList.setVisible(false);
		}
		else{
			if(_account.getInvitions().size()!=0){
				invitationsList.setVisible(true);
				populateInvitations();
			}
		}
	}
	
	public void displayMessages(String newText){
		eventCommentDisplayField.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?");
		//TODO: thissss
	}
	
	public void postMessage(){
		String post = eventCommentWriteField.getText();
		eventCommentWriteField.setText("");
		String pre = "";
		String mid = "";
		String email = _account.getID();
		String id = email.split("@")[0];
		if (!_account.getName().equals("")){
			mid = id + " (" + _account.getName() + "): ";
		} else {
			mid = id +": ";
		}
		if (eventCommentDisplayField.getText().length() != 0){
			pre = eventCommentDisplayField.getText() + "\n";
		}
		eventCommentDisplayField.setText(pre + mid + post);
		String forServer = mid+post;
		//TODO: post forServer to server
	}

	public void recieveInvite(Invitation invitation) {
		_account.addInvitation(invitation);
		_client.storeAccount(_account);
		populateInvitations();
		
	}

}
