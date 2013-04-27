/**
 * This is the writing interface class. It serves as the UI for the autocorrect project. A document listener
 * is attached to the text field to note whenever the input changes.
 */

package autocorrect;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WritingInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField textBox_;
	private JPanel textPanel_;
	private JLabel textLabel_;
	
	private JPanel suggestionPanel_;
	private JLabel[] suggestionLabels_;
	
	
	/**
	 * Constructor for the UI for autocorrect.
	 */
	public WritingInterface(Engine engine){
		super("Autocorrect");
		textPanel_ = new JPanel();
		textLabel_ = new JLabel("Please enter words: ");
		textBox_ = new JTextField(20);
		textPanel_.add(textLabel_);
		textPanel_.add(textBox_);
		
		suggestionPanel_ = new JPanel();
		suggestionLabels_ = new JLabel[5];
		for(int i = 0; i < suggestionLabels_.length; i++){
			suggestionLabels_[i] = new JLabel(" ");
			suggestionPanel_.add(suggestionLabels_[i]);
			suggestionPanel_.setLayout(new GridLayout(5,0));
		}
		this.add(suggestionPanel_, BorderLayout.SOUTH);
		this.setSize(200, 200);
		
		this.add(textPanel_);
		//needed for JFrame
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.pack();
	    this.setVisible(true);
	    
	    run(engine);
	}
	
	/**
	 * Runs the gui by constantly trying to update the text box.
	 * @param engine The engine that is used to suggest.
	 */
	public void run(Engine engine){
		while(true){
			String text = textBox_.getText();
			text = text.replaceAll("\\p{Punct}|\\d", " ").toLowerCase();
			text = text.replaceAll(("\\s+"), " ");
			String oldText = text;	//Need old text to check if there is a space at the end.
			text = text.replace("\n", "");
			text = text.trim();
			//Not an empty string, continue to display normally.
			if(!text.equals("")){
				//Only continue if there is text to complete and no space at the end of word.
				if(text.length()!=0 && oldText.charAt(oldText.length()-1) != ' '){
					engine.setText(text);
					ArrayList<String> suggestions = engine.suggest();
					for(int i = 0; i < suggestionLabels_.length; i++){
						if(i<suggestions.size()){
							suggestionLabels_[i].setText(suggestions.get(i));
						}
						else
							suggestionLabels_[i].setText(" ");
					}
				}
				else{
					for(int i = 0; i < suggestionLabels_.length; i++){
						suggestionLabels_[i].setText(" ");
					}
				}
			}
			//If it's an empty string, fill all back into nothing.
			else{
				for(int i = 0; i < suggestionLabels_.length; i++){
					suggestionLabels_[i].setText(" ");
				}
			}
		}
	}
}

