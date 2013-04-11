package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;

public class LoginWindow extends JFrame {

	Client _client;
	JTextField _usernameInput;
	JPasswordField _passwordInput;
	
	public LoginWindow(Client client){
		super("Login With Miranda");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(300,300);
		
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(500, 200));
		this.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagConstraints c = new GridBagConstraints();
		
		_client = client;
		
		_usernameInput = new JTextField(20);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(_usernameInput,c);
        
        _passwordInput = new JPasswordField(20);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(_passwordInput,c);
		
        JLabel userLabel = new JLabel("Username: ");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(userLabel,c);
        
        JLabel passLabel = new JLabel("Password: ");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(passLabel,c);
        
		JButton submit = new JButton("Submit");
		c.gridx = 1;
		c.gridy = 4;
        panel.add(submit,c);
        submit.addActionListener(new LoginButton());
        
		this.pack();
		
	}
	
	class LoginButton implements ActionListener{

        public void actionPerformed(ActionEvent e){
            //JTextField usernameInput = (JTextField)e.getSource();
        	if(_usernameInput.getText().length()==0 || _passwordInput.getText().length()==0){
        		JOptionPane.showMessageDialog(null,"You must input a username and password");
        	}
            
        }
    }
}
