package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import UserInfo.Account;

import client.Client;

public class HomeWindow extends JFrame {

	private Account _account;
	
	public HomeWindow(Account account) throws HeadlessException {
		super("Cooking with Miranda!");
		_account = account;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(950,550);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(1000, 800));
		panel.setBackground(new Color(255,102,102));
		this.add(panel);
		GridBagConstraints c = new GridBagConstraints();
		
		// USER INFO
		JLabel meLabel = new JLabel("Me");
		JPanel me = new JPanel(new GridBagLayout());
		me.setPreferredSize(new Dimension(250, 180));
		me.setVisible(true);
		me.setBackground(Color.gray);
		//my Info
		JLabel myInfoLabel = new JLabel("My Info ");
        c.gridx = 0;
        c.gridy = 0;
        me.add(myInfoLabel,c);
		JTextArea myInfo = new JTextArea("Name:\n Area:");
		myInfo.setBackground(Color.white);
		myInfo.setPreferredSize(new Dimension(150,80));
		c.gridx = 0;
		c.gridy = 1;
		me.add(myInfo, c);
		//Dietary Restrictions
		JLabel dietLabel = new JLabel("Dietary Restrictions");
        c.gridx = 1;
        c.gridy = 0;
        me.add(dietLabel,c);
        JTextArea diet = new JTextArea("");
        diet.setBackground(Color.white);
        diet.setPreferredSize(new Dimension(90,30));
        c.gridx = 1;
        c.gridy = 1;
        me.add(diet,c);
        //Dislikes
        JLabel dislikesLabel = new JLabel("Dislikes");
        c.gridx = 1;
        c.gridy = 2;
        me.add(dislikesLabel,c);
        JTextArea dislikes = new JTextArea("");
        dislikes.setBackground(Color.white);
        dislikes.setPreferredSize(new Dimension(90,30));
        c.gridx = 1;
        c.gridy = 3;
        me.add(dislikes,c);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(meLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(me,c);
        
        //MyFRIDGE
        JLabel fridgeLabel = new JLabel("In the 'Fridge!");
		JPanel fridge = new JPanel();
		fridge.setPreferredSize(new Dimension(250, 180));
		fridge.setVisible(true);
		fridge.setBackground(Color.gray);
		//In My Fridge
		JTextArea inFridge = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		inFridge.setBackground(Color.white);
		inFridge.setPreferredSize(new Dimension(240,170));
		fridge.add(inFridge);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(fridgeLabel,c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(fridge,c);
        
		//MY_RECIPES
		JLabel myRecipesLabel = new JLabel("My Recipes");
		JPanel myRecipes = new JPanel();
		myRecipes.setPreferredSize(new Dimension(250, 180));
		myRecipes.setVisible(true);
		myRecipes.setBackground(Color.gray);
		//recipes
		JTextArea recipes = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		recipes.setBackground(Color.white);
		recipes.setPreferredSize(new Dimension(240,170));
		myRecipes.add(recipes);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(myRecipesLabel,c);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(myRecipes,c);
		
		//ShoppingList
		JLabel shoppingLabel = new JLabel("My Shopping List");
		JPanel shopping = new JPanel();
		shopping.setPreferredSize(new Dimension(250, 180));
		shopping.setVisible(true);
		shopping.setBackground(Color.gray);
		//shopping list
		JTextArea shoppingList = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		shoppingList.setBackground(Color.white);
		shoppingList.setPreferredSize(new Dimension(240,170));
		shopping.add(shoppingList);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(shoppingLabel,c);
		c.gridx = 1;
		c.gridy = 3;
		panel.add(shopping,c);
		
		
		
	}

}
