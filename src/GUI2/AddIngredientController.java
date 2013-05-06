package GUI2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class AddIngredientController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ListView<?> IngredientList;

    @FXML
    void addSelectedListener(ActionEvent event) {
    	//TODO:Things
    }

    @FXML
    void initialize() {
        assert IngredientList != null : "fx:id=\"IngredientList\" was not injected: check your FXML file 'AddIngredientsWindow.fxml'.";
    }

}

