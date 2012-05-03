package plugin.views.internalframes;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JInternalHusacctMainScreen extends JInternalFrame {

	private JLabel text = new JLabel("Dit is de HUSACCT Eclipse plugin");
	
	private Image Background; 
	

	public JInternalHusacctMainScreen(){

		// getContentPane().add(new JLabel("test"), BorderLayout.CENTER);
		 getContentPane().add(text);
		 
		    setBounds(50, 50, 200, 200);
		    setResizable(true);
		    setClosable(true);
		    setMaximizable(true);
		    setIconifiable(true);
		    setTitle("Husacct");
		   setVisible(true);
	}	
}