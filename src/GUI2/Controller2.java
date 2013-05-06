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
import javax.swing.JFrame;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import server.AutocorrectEngines;
import API.Wrapper;
import API.YummlyAPIWrapper;
import Email.Sender;
import GUI2.Controller.GuiBox;
import GUI2.Controller.KitchenPane;
import GUI2.Controller.SelectAllBox;
import GUI2.Controller.UserIngredientBox;
import UserInfo.Account;
import UserInfo.Event;
import UserInfo.Ingredient;
import UserInfo.Invitation;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import client.Client;

public class Controller2 extends AnchorPane implements Initializable {

	private Image xImage = new Image("http://4.bp.blogspot.com/-JgoPXVNn5-U/UU3x1hDVHcI/AAAAAAAAA-E/s2dwrJcapd0/s1600/redx-300x297.jpg", 10, 10, true, true, true);	    @FXML private ResourceBundle resources;	    @FXML private URL location;	    @FXML private Label NoSearchResults;
    @FXML private Button addFridgeIngredient;
    @FXML private ComboBox<String> addRecipeEventSelector;
    @FXML private Button addRecipeToEventButton;
    @FXML private ComboBox<String> addShoppingIngredient;
    @FXML private ImageView chefHat;
    @FXML private TextArea createDateField;
    @FXML private Button createEventButton;
    @FXML private TextArea createEventField;
    @FXML private ImageView envelope;
    @FXML private TextArea eventCommentDisplayField;
    @FXML private Button eventCommentPostButton;
    @FXML private TextArea eventCommentWriteField;
    @FXML private Label eventLabel;
    @FXML private Button eventMenuAddButton;
    @FXML private ListView<?> eventMenuList;
    @FXML private ToggleButton eventMenuRemoveButton;
    @FXML private ComboBox<String> eventSelector;
    @FXML private Button eventShoppingAddButton;
    @FXML private ComboBox<String> eventShoppingComboBox;
    @FXML private ListView<String> eventShoppingList;
    @FXML private ToggleButton eventShoppingRemoveButton;
    @FXML private ListView<UserIngredientBox> fridgeList;
    @FXML private CheckBox getRecipeChecksButton;
    @FXML private Button goToRecipeSearchButton;
    @FXML private Accordion ingredientsAccordion;
    @FXML private Label invalidEmailError;
    @FXML private ListView<?> invitationsList;
    @FXML private Pane inviteBigPane;
    @FXML private AnchorPane invitePane;
    @FXML private TextField kitchenAddChefField;
    @FXML private Button kitchenAddIngredientButton;
    @FXML private ListView<String> kitchenAllergyList;
    @FXML private Button kitchenChefInviteButton;
    @FXML private ListView<String> kitchenChefList;
    @FXML private ListView<String> kitchenDietList;
    @FXML private Pane kitchenHide;
    @FXML private ComboBox<String> kitchenIngredientComboBox;
    @FXML private ListView<String> kitchenIngredientList;
    @FXML private ListView<String> kitchenRecipeList;
    @FXML private ComboBox<String> kitchenSelector;
    @FXML private Button leaveKitchenButton;
    @FXML private Label nameEventLabel;
    @FXML private ComboBox<String> newIngredient;
    @FXML private Text newKitchenActionText;
    @FXML private Button newKitchenButton;
    @FXML private Button newKitchenCancelButton;
    @FXML private Button newKitchenCreateButton;
    @FXML private Label newKitchenLabel;
    @FXML private TextField newKitchenNameField;
    @FXML private Label numberOfInvites;
    @FXML private FlowPane recipeFlow;
    @FXML private Tab recipeSearchTab;
    @FXML private CheckBox removeFridgeIngredient;
    @FXML private AnchorPane removeIngredientsButton;
    @FXML private CheckBox removeShoppingIngredient;	    
    @FXML private FlowPane resultsFlow;	    
    @FXML private AnchorPane root;	    
    @FXML private Button searchAdditionalAdd;	    
    @FXML private ComboBox<String> searchAdditionalBox;	    
    @FXML private ListView<String> searchAdditionalList;	    
    @FXML private CheckBox searchAdditionalRemove;	    
    @FXML private Button searchButton;	    
    @FXML private TextField searchField;	    
    @FXML private ListView<String> shoppingList;	    
    @FXML private TabPane tabPane;	
	    
    
    //Local Data
    private Client _client;
    private Account _account;
    private Map<KitchenName, Kitchen> _kitchens;
    private AutocorrectEngines _engines;
    private Wrapper _api;
    private String _currentEventName;
    //private KitchenPane _currentKitchenPane; **************************************************
    private HashSet<String> _setOfAdditionalSearchIngs = new HashSet<String>();
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
            assert NoSearchResults != null : "fx:id=\"NoSearchResults\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert addFridgeIngredient != null : "fx:id=\"addFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert addRecipeEventSelector != null : "fx:id=\"addRecipeEventSelector\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert addRecipeToEventButton != null : "fx:id=\"addRecipeToEventButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert addShoppingIngredient != null : "fx:id=\"addShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert chefHat != null : "fx:id=\"chefHat\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert createDateField != null : "fx:id=\"createDateField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert createEventButton != null : "fx:id=\"createEventButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert createEventField != null : "fx:id=\"createEventField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert envelope != null : "fx:id=\"envelope\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventCommentDisplayField != null : "fx:id=\"eventCommentDisplayField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventCommentPostButton != null : "fx:id=\"eventCommentPostButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventCommentWriteField != null : "fx:id=\"eventCommentWriteField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventLabel != null : "fx:id=\"eventLabel\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventMenuAddButton != null : "fx:id=\"eventMenuAddButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventMenuList != null : "fx:id=\"eventMenuList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventMenuRemoveButton != null : "fx:id=\"eventMenuRemoveButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventSelector != null : "fx:id=\"eventSelector\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventShoppingAddButton != null : "fx:id=\"eventShoppingAddButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventShoppingComboBox != null : "fx:id=\"eventShoppingComboBox\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventShoppingList != null : "fx:id=\"eventShoppingList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert eventShoppingRemoveButton != null : "fx:id=\"eventShoppingRemoveButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert fridgeList != null : "fx:id=\"fridgeList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert getRecipeChecksButton != null : "fx:id=\"getRecipeChecksButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert goToRecipeSearchButton != null : "fx:id=\"goToRecipeSearchButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert ingredientsAccordion != null : "fx:id=\"ingredientsAccordion\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert invalidEmailError != null : "fx:id=\"invalidEmailError\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert invitationsList != null : "fx:id=\"invitationsList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert inviteBigPane != null : "fx:id=\"inviteBigPane\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert invitePane != null : "fx:id=\"invitePane\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenAddChefField != null : "fx:id=\"kitchenAddChefField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenAddIngredientButton != null : "fx:id=\"kitchenAddIngredientButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenAllergyList != null : "fx:id=\"kitchenAllergyList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenChefInviteButton != null : "fx:id=\"kitchenChefInviteButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenChefList != null : "fx:id=\"kitchenChefList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenDietList != null : "fx:id=\"kitchenDietList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenHide != null : "fx:id=\"kitchenHide\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenIngredientComboBox != null : "fx:id=\"kitchenIngredientComboBox\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenIngredientList != null : "fx:id=\"kitchenIngredientList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenRecipeList != null : "fx:id=\"kitchenRecipeList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert kitchenSelector != null : "fx:id=\"kitchenSelector\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert leaveKitchenButton != null : "fx:id=\"leaveKitchenButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert nameEventLabel != null : "fx:id=\"nameEventLabel\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newIngredient != null : "fx:id=\"newIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenActionText != null : "fx:id=\"newKitchenActionText\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenButton != null : "fx:id=\"newKitchenButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenCancelButton != null : "fx:id=\"newKitchenCancelButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenCreateButton != null : "fx:id=\"newKitchenCreateButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenLabel != null : "fx:id=\"newKitchenLabel\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert newKitchenNameField != null : "fx:id=\"newKitchenNameField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert numberOfInvites != null : "fx:id=\"numberOfInvites\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert recipeFlow != null : "fx:id=\"recipeFlow\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert recipeSearchTab != null : "fx:id=\"recipeSearchTab\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert removeFridgeIngredient != null : "fx:id=\"removeFridgeIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert removeIngredientsButton != null : "fx:id=\"removeIngredientsButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert removeShoppingIngredient != null : "fx:id=\"removeShoppingIngredient\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert resultsFlow != null : "fx:id=\"resultsFlow\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchAdditionalAdd != null : "fx:id=\"searchAdditionalAdd\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchAdditionalBox != null : "fx:id=\"searchAdditionalBox\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchAdditionalList != null : "fx:id=\"searchAdditionalList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchAdditionalRemove != null : "fx:id=\"searchAdditionalRemove\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert shoppingList != null : "fx:id=\"shoppingList\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";
            assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'CookingWithFriends update.fxml'.";

    }
    
    /*
     * General use:
     * GUI BOX, REMOVE BUTTON, AND DISABLE REMOVES
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
    
	
	/*
	 * FRIDGE STUFF
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
    	//System.out.println("was removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
    	removeFridgeIngredient.setSelected(false);
    	//System.out.println("now removeFridgebutton is selected: " + removeFridgeIngredient.isSelected());
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
    


}
