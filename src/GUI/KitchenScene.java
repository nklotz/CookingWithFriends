package GUI;

import java.util.HashSet;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import UserInfo.Account;
import UserInfo.Ingredient;
import UserInfo.Kitchen;
import UserInfo.KitchenName;
import UserInfo.Nameable;
import UserInfo.Recipe;
import client.Client;

public class KitchenScene implements GUIScene {

	private Kitchen _kitchen;
	private GUIFrame _frame;
	private Client _client;
	private int _width = 300;
	private int _height = 500;
	
	public KitchenScene(Kitchen kitchen, GUIFrame frame, Client client){
		_kitchen = kitchen;
		_frame = frame;
		_client = client;
		
	}

	@Override
	public Scene makeScene() {
		final GridPane grid = new GridPane();
		grid.setStyle(Style.BACKGROUND);
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene scene = new Scene(grid, 1200, 500);
        
        Text scenetitle = new Text("Cooking with Friends: Kitchen");
        Text kitchenName = new Text("Welcome to " + _kitchen.getName());
        scenetitle.setStyle(Style.PAGE_HEADER);
        kitchenName.setStyle(Style.PAGE_HEADER);
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(kitchenName, 0, 1, 2, 1);
        
        Button goHome = new Button("Return Home");
        goHome.setStyle(Style.GO_HOME);
        goHome.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		_frame.loadHomeScene();
        	}
        });
        HBox homeBox = new HBox(10);
        homeBox.setAlignment(Pos.TOP_RIGHT);
        homeBox.getChildren().add(goHome);
        grid.add(homeBox, 3, 0);
        
        
        //HEADERS
        //menu
        Text menu = new Text("Menu");
        menu.setStyle(Style.SECTION_HEADER);
        grid.add(menu, 0, 2);
        //diet
        Text diet = new Text("Dietary Restrictions");
        diet.setStyle(Style.SECTION_HEADER);
        grid.add(diet, 1, 2);
        //ingredients
        Text ingredients = new Text("Ingredients");
        ingredients.setStyle(Style.SECTION_HEADER);
        grid.add(ingredients, 1, 4);
        //events
        Text events = new Text("Events");
        events.setStyle(Style.SECTION_HEADER);
        grid.add(events, 2, 2);
        //chefs
        Text chefs = new Text("Chefs");
        chefs.setStyle(Style.SECTION_HEADER);
        grid.add(chefs, 3, 2);
        
        
        //PANEs
        //menu
        GridPane menuGrid = new GridPane();
        menuGrid.setPrefSize(_width, _height);
        menuGrid.setStyle(Style.SECTIONS);
        menuGrid.setHgap(5);
        grid.add(menuGrid, 0, 3,1,3);
        menuGrid.add(displayList(_width, _height, false, false, _kitchen.getRecipes(), new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		System.out.println("This will go to recipe you selected");
        	}
        }, menuGrid), 0, 0);
        
        //diet
        GridPane dietGrid = new GridPane();
        dietGrid.setPrefSize(_width, _height/2);
        dietGrid.setStyle(Style.SECTIONS);
        dietGrid.setHgap(5);
        grid.add(dietGrid, 1, 3);
        /*dietGrid.add(displayList(_width, _height/2,false,true,_kitchen.getDietaryRestrictions(), new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		System.out.println("This will go to recipe you selected");
        	}
        }, dietGrid), 0, 0);*/
        
        //ingredients
        GridPane inGrid = new GridPane();
        inGrid.setPrefSize(_width, _height/2);
        inGrid.setStyle(Style.SECTIONS);
        inGrid.setHgap(5);
        grid.add(inGrid, 1, 5);
        inGrid.add(displayList(_width,_height/2,true,false,_kitchen.getIngredients(), new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		System.out.println("This will go to recipe you selected");
        	}
        }, inGrid), 0, 0);
        //events
        GridPane eventGrid = new GridPane();
        eventGrid.setPrefSize(_width, _height);
        eventGrid.setStyle(Style.SECTIONS);
        eventGrid.setHgap(5);
        grid.add(eventGrid, 2, 3, 1, 3);
        //TODO: add content
        //chefs
        GridPane chefGrid = new GridPane();
        chefGrid.setPrefSize(_width, _height);
        chefGrid.setStyle(Style.SECTIONS);
        chefGrid.setHgap(5);
        grid.add(chefGrid, 3, 3, 1, 3);
        
		return scene;
	}
	
	private GridPane displayList(final int width, final int height, final boolean isIngredients, final boolean dietary, final Set<? extends Nameable> items,
			final EventHandler<ActionEvent> itemDest, final GridPane parentGrid){
		final GridPane listPane = new GridPane();
		listPane.setHgap(3);
		listPane.setVgap(3);
		listPane.setPrefSize(width, height);
		listPane.setStyle(Style.TEXT);
		final ScrollPane scroll = new ScrollPane();//Scrolling Panel
		scroll.setStyle(Style.TEXT);
		scroll.setPrefSize(width, height);
		scroll.setMinWidth(width);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToWidth(true);
		//grid panel for buttons
		final GridPane itemList = buttonList(width, height, items, itemDest);
		scroll.setContent(itemList);
		Button edit = null;
		if(isIngredients){
			edit = new Button("+");
		} else {
			edit = new Button("+/-");
		}
		edit.setMinWidth(20);
		edit.setStyle(Style.BUTTON);
		final HBox editBox = new HBox(10);
        editBox.setPrefHeight(80);
        editBox.setAlignment(Pos.TOP_RIGHT);
        editBox.getChildren().add(edit);
		edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	editBox.getChildren().remove(0);
            	int j = 0;
            	for (Nameable item : items){
            		System.out.println("calling");
            		itemList.add(removeButton(width, height, item, isIngredients, dietary, itemDest), 2,j);
            		j++;
            	}
            	if (!isIngredients){
            		Button b = new Button("Search Recipes");
            		b.setStyle(Style.BUTTON);
            		b.setOnAction(new EventHandler<ActionEvent>(){
            			@Override
            			public void handle(ActionEvent e){
            				_frame.loadSearchScene();
            			}
            		});
            		HBox saveBox = new HBox();
        			saveBox.setAlignment(Pos.BOTTOM_LEFT);
        			saveBox.getChildren().add(b);
            		if (isIngredients){	
            			listPane.add(saveBox, 0, 2);
            		} else {
            			listPane.add(saveBox, 1, 2);
            		}
            	}
            	if (!isIngredients){
	            	Button save = new Button("Save");
	        		save.setMinWidth(40);
	        		save.setStyle(Style.BUTTON);
	        		HBox hbBtn = new HBox(10);
	                hbBtn.setPrefHeight(80);
	                hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	                hbBtn.getChildren().add(save);
	                listPane.add(hbBtn, 2, 2, 2,1);
	        		save.setOnAction(new EventHandler<ActionEvent>() {
	        			@Override
	                    public void handle(ActionEvent e) {
	        				parentGrid.add(displayList(width, height, isIngredients, dietary, items, itemDest, parentGrid), 0, 0);
	        			}
	        		});
            	} else { //thing to add your own ingredients
            		//Button
            	}
        		
        	}

			private Button removeButton(final int width, final int height, final Nameable item,
					final boolean isIngredient, final boolean isFridge, final EventHandler<ActionEvent> itemDest) {
				Button b = new Button("X");
				System.out.println(b.getText());
				b.setStyle(Style.RECIPE);
				//b.setMinWidth(40);
				b.setOnAction(new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent e){
						if (isIngredient){
							if(isFridge){
								items.remove(item);
								//_kitchen.setIngredients((Set<Ingredient>) items);
							} else {
								items.remove(item);
								//_kitchen.setShoppingList((Set<Ingredient>) items);
							}
						} else {
							items.remove(item);
							_kitchen.setRecipes((HashSet<Recipe>) items);
							_client.removeRecipe(_kitchen.getID(), (Recipe) item);
						}
						final GridPane itemList = new GridPane();
						itemList.setPrefSize(width,height);
						itemList.setVgap(2);
						int i = 0;
						for (Nameable item : items){
							itemList.add(itemButton(item, itemDest),1,i);
							if (isIngredients){
								Ingredient thing = (Ingredient) item;
								itemList.add(quantButton(thing.getQuantity() + thing.getUnit()), 0, i);
							}
							itemList.add(removeButton(width, height, item, isIngredients, isFridge, itemDest), 2,i);
							i++;
						}
						scroll.setContent(itemList);
					}
				});
				return b;
			}
        });
		if (isIngredients){
			Text qty = new Text("   Qty.   |");
			Text Ingredient = new Text("	Ingredient");
			//Display Quantities
			int j = 0;
			for (Nameable item : items){
				Ingredient thing = (Ingredient) item;
				itemList.add(quantButton(thing.getQuantity() + thing.getUnit()), 0, j);
				j++;
			}
			listPane.add(qty, 1, 0);
			listPane.add(Ingredient, 2, 0);
			listPane.add(scroll, 1, 1, 2, 1);
			listPane.add(editBox, 0, 0);
		} else {
			listPane.add(scroll, 1, 0);
			listPane.add(editBox, 0, 0);
		}
		return listPane;
	}


	private GridPane buttonList(int width, int height, Set<? extends Nameable> items, EventHandler<ActionEvent> itemDest){
		//grid panel for buttons
		final GridPane itemListGrid = new GridPane();
		itemListGrid.setPrefSize(width,height);
		itemListGrid.setVgap(2);
		int i = 0;
		for (Nameable item : items){
			itemListGrid.add(itemButton(item, itemDest),1,i);
			i++;
		}
		return itemListGrid;
	}

	private Button quantButton(String called){
		System.out.println(called);
		Button b = new Button(called);
		b.setStyle(Style.RECIPE);
		//b.setMinWidth(45);
		b.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				System.out.println("nothing");
			}
		});
		return b;
	}

	private Button itemButton(Nameable item, EventHandler<ActionEvent> destination){ 
		Button b = new Button(item.getName());
		b.setStyle(Style.RECIPE);
		b.setPrefWidth(350);
		b.setOnAction(destination);
		return b;
	}
}
