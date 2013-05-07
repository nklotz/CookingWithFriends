package GUI2;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.Client;

import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

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

    private Recipe _recipe;
    private Controller2 _controller;
	private Client _client;

	public void setUp(Recipe recipe, Client client, Controller2 controller) {
		_recipe = recipe;
		_controller = controller;
		_client = client;
		
		titleLabel.setText(_recipe.getName());
		
		ObservableList<String> ingredients = FXCollections.observableArrayList();
		ingredients.addAll(_recipe.getIngredientStrings());
		ingredientsList.setItems(ingredients);
		
		if (_recipe.getNumberOfServings() != null) {
			servingsLabel.setText(_recipe.getNumberOfServings());
		}
		else {
			servingsHeader.setVisible(false);
		}
		
		String time = _recipe.getTime();
		if (time != null) {
			prepTimeLabel.setText(time);
		}
		else {
			prepTimeHeader.setVisible(false);
		}
		
		recipeLink.setText(_recipe.getSourceUrl());
	}
	
    @FXML
    void addRecipeListener(ActionEvent event) {
    	if (chooseKitchenBox.getValue().equals("My Recipes")) {
    		
    	}
    	else {
    		
    	}

    	//_controller.addRecipeToAccount();
    	
    }

    @FXML
    void initialize() {
        assert chooseKitchenBox != null : "fx:id=\"chooseKitchenBox\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert ingredientsList != null : "fx:id=\"ingredientsList\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert recipeImage != null : "fx:id=\"recipeImage\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
    }
    
	public void populateKitchenSelector(){
		HashMap<KitchenName, Kitchen> kitchens = _client.getKitchens();
		final HashMap<String,KitchenName> kitchenIds = _client.getKitchenIdMap();
		chooseKitchenBox.getItems().clear();
		chooseKitchenBox.getItems().addAll(kitchenIds.keySet());
		
		chooseKitchenBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
							setText(kitchenIds.get(name).getName());
						}
					}
				};
			}
		});
		
		chooseKitchenBox.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				System.out.println("I have been clicked! " + chooseKitchenBox.getValue());
				String id = chooseKitchenBox.getValue();
				if (id != null) {
					chooseKitchenBox.setButtonCell(new ListCell<String>() {						
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
					
				}
			}
		});
		
	}
}
