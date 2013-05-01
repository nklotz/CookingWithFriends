package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import API.Wrapper;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Recipe;

public class SearchScene implements GUIScene {

	private Account _account;
	private GUIFrame _frame;
	private Map<KitchenName, Kitchen> _kitchens;
	private Wrapper _wrapper;
	
	private GridPane _searchResults;
	private List<IngredientCheckBox> _checkBoxList;
	
	public SearchScene(Account account, GUIFrame frame, Map<KitchenName, Kitchen> kitchens){
		_account = account;
		_frame = frame;
		_kitchens = kitchens;
		_checkBoxList = new ArrayList<>();
	}	
	
	@Override
	public Scene makeScene() {
		final GridPane grid = new GridPane();
		grid.setStyle(Style.BACKGROUND);
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 1000, 500);
        
        Text scenetitle = new Text("Cooking with Friends: Recipe Search");
        scenetitle.setStyle(Style.PAGE_HEADER);
        grid.add(scenetitle, 0, 0, 2, 1);
        
        //Ingredients section
        Text ingredientsHeader = new Text("Ingredients");
        ingredientsHeader.setStyle(Style.SECTION_HEADER);
        grid.add(ingredientsHeader, 0, 1);
        GridPane ingredientsGrid = new GridPane();
        ingredientsGrid.setPrefSize(250, 200);
        ingredientsGrid.setStyle(Style.SECTIONS);
        ingredientsGrid.setHgap(5);
        grid.add(ingredientsGrid, 0, 2);
        
        List<TitledPane> kitchenPanes = new ArrayList<>();
        TitledPane personalPane =  new TitledPane();
        personalPane.setText("My Fridge");
        personalPane.setContent(makeIngredientList(_account.getIngredients()));
        kitchenPanes.add(personalPane);
        for (Kitchen kitchen : _kitchens.values()) {
        	TitledPane kitchenPane =  new TitledPane();
        	kitchenPane.setText(kitchen.getName());
        	kitchenPane.setContent(makeIngredientList(kitchen.getIngredients()));
        	kitchenPanes.add(kitchenPane);
        }        
        Accordion ingredientsAccordion = new Accordion();
        ingredientsAccordion.getPanes().addAll(kitchenPanes);
        ingredientsGrid.add(ingredientsAccordion, 0, 0);
        
        //Results section
        Text resultsHeader = new Text("Results");
        resultsHeader.setStyle(Style.SECTION_HEADER);
        grid.add(resultsHeader, 1, 1);
        _searchResults = new GridPane();
        ScrollPane resultsScroll = new ScrollPane(); //Scrolling Panel
        resultsScroll.setPrefSize(300, 200);
        resultsScroll.setStyle(Style.SECTIONS);
        resultsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        resultsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        resultsScroll.setFitToWidth(true);
        resultsScroll.setContent(_searchResults);
        grid.add(resultsScroll, 1, 2);
        
        //Search section
        Text searchHeader = new Text("Search");
        searchHeader.setStyle(Style.SECTION_HEADER);
        grid.add(searchHeader, 0, 3);
        GridPane searchPane = new GridPane();
        searchPane.setPrefSize(400, 100);
        searchPane.setStyle(Style.SECTIONS);
        final TextField searchField = new TextField();
        Button searchButton = new Button();
        searchButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				List<Ingredient> selectedIngredients = new ArrayList<>();
				for (IngredientCheckBox checkBox : _checkBoxList) {
					if (checkBox.getCheckBox().isSelected()) {
						selectedIngredients.add(checkBox.getIngredient());
					}
				}
				List<Recipe> recipes = _wrapper.searchRecipes(searchField.getText(), selectedIngredients, dislikes, dietRestrictions, allergies);
			}
		});
        searchPane.add(searchField, 0, 0);
        searchPane.add(searchButton, 0, 1);
        grid.add(searchPane, 0, 4);
        
        
        //Left to buy
        Text leftToBuyHeader = new Text("Left to buy");
        leftToBuyHeader.setStyle(Style.SECTION_HEADER);
        grid.add(leftToBuyHeader, 1, 3);
        GridPane leftToBuyGrid = new GridPane();
        leftToBuyGrid.setPrefSize(300, 200);
        leftToBuyGrid.setStyle(Style.SECTIONS);
        grid.add(leftToBuyGrid, 1, 4);
        
        //Go back to the home page.
        Button homeButton = new Button("Back to home");
        homeButton.setStyle(Style.BUTTON);
		grid.add(homeButton, 1, 1);
		homeButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				_frame.loadHomeScene();
			}
		});
        
		return scene;
	}
	
	private Pane makeIngredientList(Set<Ingredient> ingredients) {
		Pane listPane = new Pane();
		listPane.setPrefSize(350, 176);
		listPane.setStyle(Style.TEXT);
		
		ScrollPane scroll = new ScrollPane(); //Scrolling Panel
		scroll.setStyle(Style.TEXT);
		scroll.setPrefSize(listPane.getWidth()-30, listPane.getHeight());
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToWidth(true);
		
		VBox itemList = new VBox();
		itemList.setPrefSize(300, 200);
		for (Ingredient ing : ingredients){
			IngredientCheckBox checkBox = new IngredientCheckBox(ing);
			_checkBoxList.add(checkBox);
			itemList.getChildren().add(checkBox);
		}
		
		scroll.setContent(itemList);
		listPane.getChildren().add(scroll);
		return listPane;
	}
	
	private class IngredientCheckBox extends HBox {
		private CheckBox _cb;
		private Ingredient _ingredient;
		public IngredientCheckBox(Ingredient ingredient) {
			super();
			_ingredient = ingredient;
			_cb = new CheckBox();
			Text ingredientName = new Text(ingredient.getName());
			this.getChildren().addAll(ingredientName, _cb);	
		}
		
		public CheckBox getCheckBox() {
			return _cb;
		}
		
		public Ingredient getIngredient() {
			return _ingredient;
		}
	}
	
	private class RecipeBox extends Button {
		public RecipeBox(final Recipe recipe) {
			super();
			Image recipeThumbnail = new Image(recipe.getImageUrl(), 20, 20, true, true, true); 
			this.setGraphic(new ImageView(recipeThumbnail));
			this.setText(recipe.getName());
			this.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent e){
					_frame.loadRecipeScene(recipe);
				}
			});
		}
	}
}
