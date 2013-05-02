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
    private ComboBox<String> addShoppingIngredient;

    @FXML
    private ListView<UserIngredientBox> fridgeList;

    @FXML
    private ComboBox<String> newIngredient;

    @FXML
    private ListView<UserRecipeBox> recipeList;

    @FXML
    private ListView<? extends GuiBox> shoppingList;
    
    @FXML
    private ToggleButton removeFridge;
    
 

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
    	
    	populateUserFridge();
    	populateUserRecipes();
    	List<String> list = new ArrayList<String>();
    	addShoppingIngredient.getItems().addAll(list);
    	newIngredient.getItems().addAll(list);
    	//addShoppingIngredient.
    //	initializeAutocorrect();
    	
    	

    }
    
    public void addIngredientListener(){
    	//addFridgeIngredient
    	String ing = newIngredient.getEditor().getText();
    	
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
    
   /* public void initializeAutocorrect(){
    	newIngredient.setEditable(true);
    	newIngredient.getEditor().textProperty().addListener(new ChangeListener<String>() {
            //box.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	String text = newIngredient.getEditor().getText();
            	List<String> suggs = null;
            	if(text.trim().length()!=0)
            		suggs = _engines.getIngredientSuggestions(text);
            	if(suggs!=null){
            		System.out.println("HEREEE NOT NULL");
            		newIngredient.getItems().clear();
            		newIngredient.getItems().addAll(suggs);
            	}
            }
        });
    }*/
    
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
    	for(Ingredient i: _account.getIngredients()){
    		UserIngredientBox box = new UserIngredientBox(i.getName());
    		listItems.add(box);
    	}
    	fridgeList.setItems(listItems);
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