package GUI2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import UserInfo.Account;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import client.Client;

public class RecipeController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<String> chooseKitchenBox;
    @FXML private ListView<String> ingredientsList;
    @FXML private ImageView recipeImage;
    @FXML private Label titleLabel;
    @FXML private Label prepTimeLabel, prepTimeHeader, servingsHeader;
    @FXML private Hyperlink recipeLink;
    @FXML private Label servingsLabel;

    private Recipe _basicRecipe, _completeRecipe;
    private Account _account;
	private Client _client;
	private Controller2 _controller;
	private HashMap<String, KitchenName> _kitchenIds;

	public void setUp(Recipe basicRecipe, Recipe completeRecipe, Client client, Account account, Controller2 controller) {
		_completeRecipe = completeRecipe;
		_basicRecipe = basicRecipe;
		_account = account;
		_client = client;
		_controller = controller;
		
		_kitchenIds = _client.getKitchenIdMap();
		populateKitchenSelector();
		
		titleLabel.setText(_completeRecipe.getName());
		
		ObservableList<String> ingredients = FXCollections.observableArrayList();
		ingredients.addAll(_completeRecipe.getIngredientStrings());
		ingredientsList.setItems(ingredients);
		
		if (_completeRecipe.getNumberOfServings() != null)
			servingsLabel.setText(_completeRecipe.getNumberOfServings());
		else
			servingsHeader.setVisible(false);
		
		String time = _completeRecipe.getTime();
		if (time != null)
			prepTimeLabel.setText(time);
		else
			prepTimeHeader.setVisible(false);
		
		recipeLink.setText(_completeRecipe.getSourceName());
		
		if (_completeRecipe.getImageUrl() != null) {
			recipeImage.setImage(new Image(_completeRecipe.getImageUrl(), 180, 120, true, true, true));
		}
	}
	
    @FXML
    void addRecipeListener(ActionEvent event) {
    	if (chooseKitchenBox.getValue().equals("My Recipes")) {
    		_account.addRecipe(_basicRecipe);
    		_controller.populateUserRecipes();
    		_client.storeAccount(_account);
    	}
    	else {
    		System.out.println("Adding to kitchen: " + chooseKitchenBox.getValue());
    		_client.addRecipe(chooseKitchenBox.getValue(), _basicRecipe);
    	}
    }

    @FXML
    void initialize() {
        assert chooseKitchenBox != null : "fx:id=\"chooseKitchenBox\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert ingredientsList != null : "fx:id=\"ingredientsList\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert recipeImage != null : "fx:id=\"recipeImage\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
    }
    
    @FXML
    public void openLink() {
    	try {
			java.awt.Desktop.getDesktop().browse(new URI(_completeRecipe.getSourceUrl()));
		} catch (IOException | URISyntaxException e) {
			System.out.println("Error opening link.");
			//TODO: Handle this better
		}
    }
        
	public void populateKitchenSelector(){
		final HashMap<String, KitchenName> kitchenIds = _client.getKitchenIdMap();
		chooseKitchenBox.getItems().clear();
		chooseKitchenBox.getItems().add("My Recipes");
		chooseKitchenBox.getItems().addAll(kitchenIds.keySet());
		
		chooseKitchenBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(final ListView<String> name) {
				return new MyCell();
			}
		});
		
		
		chooseKitchenBox.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e) {
				System.out.println("I have been clicked! " + chooseKitchenBox.getValue());
				String id = chooseKitchenBox.getValue();
				if (id != null) {
					chooseKitchenBox.setButtonCell(new MyCell());
				}
			}
		});
	}
	
	 private class MyCell extends ListCell<String> {
		 @Override
		 protected void updateItem(String name, boolean empty) {
			 super.updateItem(name, empty);
			 if (name == null || empty) {
				 setText("Select Kitchen");
			 } 
			 else if (name.equals("My Recipes")) {
				 setText(name);
			 }
			 else {
				 setText(_kitchenIds.get(name).getName());
			 }
		 }
	 }
}
