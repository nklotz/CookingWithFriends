package GUI;

import java.io.IOException;

import javax.swing.JFrame;

import client.Client;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
 
public class LoginWindow extends JFrame{
	
	private Client _client;
    
    public LoginWindow(Client client){
    	super("Cooking with Friends -- Login");
    	_client = client;
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	final JFXPanel fxPanel = new JFXPanel();
    	this.add(fxPanel);
    	this.setSize(550,400);
    	this.setVisible(true);
    	fxPanel.setPreferredSize(new java.awt.Dimension(550,400));
    	
    	Platform.runLater(new Runnable() {
    		@Override
    		public void run() {
    		initFX(fxPanel);
    		}
    	});
    }
    
    private void initFX(JFXPanel fxPanel) {
    	fxPanel.setScene(makeScene());
    }
    
    
    
    public Scene makeScene() {
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
         
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        Scene scene = new Scene(grid, 300, 275);
        
        Text scenetitle = new Text("Welcome to Cooking with Friends");
        scenetitle.setFont(Font.font("Comic Sans", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        final PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                
                if(userTextField.getText().length()==0 || pwBox.getText().length()==0){
            		actiontarget.setText("You must input a username and password");
            	}
            	else{
            		try {
            			//If true, start home page.
    					_client.checkPassword(userTextField.getText(), pwBox.getText());
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            }
        });
        return scene;
    }
}
