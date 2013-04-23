package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import UserInfo.Account;
import UserInfo.Recipe;

import client.Client;

public class HomeWindow extends JFrame {

	private Account _account;
	private JTextArea _ingredients;
	private JTextArea _shoppingList;
	private JTextArea _recipes;
	
	public HomeWindow(Account account) throws HeadlessException {
		super("Cooking with Miranda!");
		_account = account;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(1000,550);
		
		JPanel master = new JPanel(new FlowLayout());
		master.setPreferredSize(new Dimension(950,550));
		master.setBackground(new Color(255,102,102));
		this.add(master);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(600, 500));
		panel.setBackground(new Color(255,102,102));
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
		JTextArea myInfo = new JTextArea("Name: " + _account.getUser().getName() + " \n Area: " + _account.getUser().getAddress());
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
        JTextArea diet = new JTextArea("-Vegan \n-Gluten Free");
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
        JTextArea dislikes = new JTextArea("Squash");
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
		_ingredients = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		_ingredients.setBackground(Color.white);
		_ingredients.setPreferredSize(new Dimension(240,170));
		fridge.add(_ingredients);
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
		_recipes = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		_recipes.setBackground(Color.white);
		_recipes.setPreferredSize(new Dimension(240,170));
		myRecipes.add(_recipes);
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
		_shoppingList = new JTextArea("- \n- \n- \n- \n- \n- \n- \n- \n- \n");
		_shoppingList.setBackground(Color.white);
		_shoppingList.setPreferredSize(new Dimension(240,170));
		shopping.add(_shoppingList);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(shoppingLabel,c);
		c.gridx = 1;
		c.gridy = 3;
		panel.add(shopping,c);
		
		//Group Kitchens
		JLabel groupLabel = new JLabel("Group Kitchens");
		JPanel group = new JPanel();
		group.setPreferredSize(new Dimension(250,370));
		group.setVisible(true);
		group.setBackground(Color.gray);
		//kitchen list
		JTextArea kitchenList = new JTextArea("-West House \n-The Hall \n-Breakfast Club \n-cs032 term project team");
		kitchenList.setBackground(Color.white);
		kitchenList.setPreferredSize(new Dimension(240,360));
		group.add(kitchenList);
		//panel.add(groupLabel);
		//panel.add(group);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(300, 400));
		rightPanel.setBackground(new Color(255,102,102));
		rightPanel.add(groupLabel);
		rightPanel.add(group);
		
		//Initially Populate Fields
		this.updateFridge(_account.getIngredients());
		this.updateRecipes(_account.getRecipes());
		this.updateShoppingList(_account.getShoppingList());
		
		master.add(panel);
		master.add(rightPanel);
		this.pack();
		
	}
	
	public void updateFridge(Set<String> ingredients){
		_ingredients.setText("");
		for (String ingredient : ingredients){
			_ingredients.append("--" + ingredient + "\n");
		}
	}
	
	public void updateRecipes(Set<Recipe> recipes){
		_recipes.setText("");
		for (Recipe recipe : recipes){
			_recipes.append("--" + recipe._id+ "\n");
		}
	}
	
	public void updateShoppingList(Set<String> list){
		_shoppingList.setText("");
		for (String item : list){
			_shoppingList.append("--" + item+ "\n");
		}
	}

}
