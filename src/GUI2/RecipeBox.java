package GUI2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import UserInfo.Account;
import UserInfo.Kitchen;
import UserInfo.KitchenEvent;
import UserInfo.Recipe;
import client.Client;

public class RecipeBox extends VBox {
   
	private Recipe _recipe;
    private Controller2 _controller;
    private RecipeBox _this;
	private ContextMenu _contextMenu;
	private Client _client;
	
	private Account _user;
	private Kitchen _kitchen;
	private KitchenEvent _event;
    
    //Constructor for a user recipe
    public RecipeBox(Recipe recipe, Controller2 controller, Account user, Client client) {
    	super(8);
    	this.initialize(recipe, controller, client);
    	
    	_user = user;
    	
    	MenuItem remove = new MenuItem("Remove");
		remove.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	_user.removeRecipe(_recipe);
		    	_client.storeAccount(_user);
		    	_controller.populateUserRecipes();
		    }
		});
		_contextMenu.getItems().addAll(remove);
    }
    
    //Constructor for a kitchen recipe
    public RecipeBox(Recipe recipe, Controller2 controller, Kitchen kitchen, Client client) {
    	super(8);
    	this.initialize(recipe, controller, client);
    	
    	_kitchen = kitchen;
    	
    	MenuItem remove = new MenuItem("Remove");
		remove.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	_client.removeRecipe(_kitchen.getID(), _recipe);
		    }
		});
		_contextMenu.getItems().addAll(remove);
    }
    
    //Constructor for an event recipe
    public RecipeBox(Recipe recipe, Controller2 controller, Kitchen kitchen, KitchenEvent event, Client client) {
    	super(8);
    	this.initialize(recipe, controller, client);
    	
    	_event = event;
    	_kitchen = kitchen;
    	
    	MenuItem remove = new MenuItem("Remove");
		remove.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	_event.removeRecipe(_recipe);
		    	_client.addEvent(_kitchen.getID(), _event);
		    }
		});
		_contextMenu.getItems().addAll(remove);
    	
    }
    	
    //Constructor for recipe search
    public RecipeBox(Recipe recipe, Controller2 controller) {
    	this.initialize(recipe, controller, null);
    	
    	this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					_controller.createPopup(_recipe);
				}
			}
		});
	}

	private void initialize(Recipe recipe, Controller2 controller, Client client) {
    	_recipe = recipe;
		_controller = controller;
		_client = client;
		_this = this;
		_contextMenu = new ContextMenu();
		
		this.getStyleClass().add("recipeBox");
		this.setAlignment(Pos.CENTER);
		
		this.setMaxHeight(130);
		this.setPrefHeight(130);
		this.setMinHeight(130);
		
		this.setMaxWidth(150);
		this.setPrefWidth(150);
		this.setMinWidth(150);
		
		if (recipe.hasThumbnail()) {
			Image recipeThumbnail = new Image(recipe.getThumbnailUrl(), 100, 0, true, true, true); 
			if (recipeThumbnail.getHeight() < 50) {
				ImageView imageView = new ImageView(recipeThumbnail);
				imageView.getStyleClass().add("recipeThumbnail");
				this.getChildren().add(imageView);
			}
		}
		
		final Label recipeLabel = new Label(recipe.getName());
		recipeLabel.setFont(new Font(11));
		recipeLabel.setMaxWidth(120);
		recipeLabel.setWrapText(true);
		this.getChildren().add(recipeLabel);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					_controller.createPopup(_recipe);
				}
				else if (event.getButton() == MouseButton.SECONDARY) {
					_contextMenu.show(_this, Side.TOP, event.getX() + 5, event.getY() + 40);
				}
			}
		});
    }
}