package GUI2;

import java.net.URL;
import java.util.ResourceBundle;

import UserInfo.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;


public class RecipeController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBox<?> chooseKitchenBox;
    @FXML private ListView<?> ingredientsList;
    @FXML private TextArea instructionsField;
    @FXML private ImageView recipeImage;
    @FXML private Label titleLabel;

    private Recipe _recipe;

    @FXML
    void addRecipeListener(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
        assert chooseKitchenBox != null : "fx:id=\"chooseKitchenBox\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert ingredientsList != null : "fx:id=\"ingredientsList\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert instructionsField != null : "fx:id=\"instructionsField\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert recipeImage != null : "fx:id=\"recipeImage\" was not injected: check your FXML file 'RecipeWindow.fxml'.";
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'RecipeWindow.fxml'.";


    }
    
    public void setRecipe(Recipe recipe) {
    	_recipe = recipe;
    }

}
