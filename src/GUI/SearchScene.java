package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;

public class SearchScene implements GUIScene {

	private Account _account;
	private GUIFrame _frame;
	private Map<String, Kitchen> _kitchens;
	
	public SearchScene(Account account, GUIFrame frame, Map<String, Kitchen> kitchens){
		_account = account;
		_frame = frame;
		_kitchens = kitchens;
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
        
        //Search section
        Text searchHeader = new Text("Search");
        searchHeader.setStyle(Style.SECTION_HEADER);
        grid.add(searchHeader, 0, 3);
        //pane
        GridPane searchGrid = new GridPane();
        searchGrid.setPrefSize(300, 200);
        searchGrid.setStyle(Style.SECTIONS);
        grid.add(searchGrid, 0, 4);
        
        //Results section
        Text resultsHeader = new Text("Results");
        resultsHeader.setStyle(Style.SECTION_HEADER);
        grid.add(resultsHeader, 1, 1);
        //pane
        GridPane resultsGrid = new GridPane();
        resultsGrid.setPrefSize(300, 200);
        resultsGrid.setStyle(Style.SECTIONS);
        grid.add(resultsGrid, 1, 2);
        
        //Left to buy
        Text leftToBuyHeader = new Text("Left to buy");
        leftToBuyHeader.setStyle(Style.SECTION_HEADER);
        grid.add(leftToBuyHeader, 1, 3);
        //pane
        GridPane leftToBuyGrid = new GridPane();
        leftToBuyGrid.setPrefSize(300, 200);
        leftToBuyGrid.setStyle(Style.SECTIONS);
        grid.add(leftToBuyGrid, 1, 4);
        
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
			itemList.getChildren().add(new IngredientCheckBox(ing));
		}
		
		scroll.setContent(itemList);
		listPane.getChildren().add(scroll);
		return listPane;
	}
	
	private class IngredientCheckBox extends HBox {
		public IngredientCheckBox(Ingredient ingredient) {
			super();
			Text ingredientName = new Text(ingredient.getName());
			CheckBox cb = new CheckBox();
			this.getChildren().addAll(ingredientName, cb);	
		}
	}
}
