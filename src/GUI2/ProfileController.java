package GUI2;

/**
 * Sample Skeleton for "EditProfileWindow.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.InputEvent;


public class ProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML // fx:id="emailLabel"
    private Label emailLabel; // Value injected by FXMLLoader

    @FXML // fx:id="removeAllergy"
    private CheckBox removeAllergy; // Value injected by FXMLLoader

    @FXML // fx:id="removeRestrction"
    private CheckBox removeRestrction; // Value injected by FXMLLoader

    @FXML // fx:id="restrictionsList"
    private ListView<?> restrictionsList; // Value injected by FXMLLoader

    @FXML // fx:id="saveChangesButton"
    private Button saveChangesButton; // Value injected by FXMLLoader


    // Handler for ComboBox[id="newRestriction"] onAction
    @FXML
    void addAllergyListener(ActionEvent event) {
        // handle the event here
    }

    // Handler for ComboBox[id="newRestriction"] onAction
    @FXML
    void addRestrictionListListener(ActionEvent event) {
        // handle the event here
    }

    // Handler for ComboBox[id="newRestriction"] onInputMethodTextChanged
    // Handler for ComboBox[id="newRestriction"] onKeyPressed
    // Handler for ComboBox[id="newRestriction"] onKeyReleased
    // Handler for ComboBox[id="newRestriction"] onKeyTyped
    @FXML
    void ingredientComboListener(InputEvent event) {
        // handle the event here
    }

    // Handler for CheckBox[fx:id="removeAllergy"] onAction
    @FXML
    void removeAllergies(ActionEvent event) {
        // handle the event here
    }

    // Handler for CheckBox[fx:id="removeRestrction"] onAction
    @FXML
    void removeRestrictions(ActionEvent event) {
        // handle the event here
    }

    // Handler for ComboBox[id="newRestriction"] onInputMethodTextChanged
    // Handler for ComboBox[id="newRestriction"] onKeyPressed
    // Handler for ComboBox[id="newRestriction"] onKeyReleased
    // Handler for ComboBox[id="newRestriction"] onKeyTyped
    @FXML
    void restrictionComboListener(InputEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="saveChangesButton"] onAction
    @FXML
    void saveAccountChanges(ActionEvent event) {
        // handle the event here
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'EditProfileWindow.fxml'.";
        assert removeAllergy != null : "fx:id=\"removeAllergy\" was not injected: check your FXML file 'EditProfileWindow.fxml'.";
        assert removeRestrction != null : "fx:id=\"removeRestrction\" was not injected: check your FXML file 'EditProfileWindow.fxml'.";
        assert restrictionsList != null : "fx:id=\"restrictionsList\" was not injected: check your FXML file 'EditProfileWindow.fxml'.";
        assert saveChangesButton != null : "fx:id=\"saveChangesButton\" was not injected: check your FXML file 'EditProfileWindow.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

    }

}
