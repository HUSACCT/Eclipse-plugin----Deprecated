package plugin.views.internalframes;

import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class JInternalHusacctNotAvailableScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextPane textPaneInformation;
	private JPanel jPanel;
	
	public JInternalHusacctNotAvailableScreen() {

		textPaneInformation = new JTextPane();
		jPanel = new JPanel();
		setBounds(50, 50, 200, 400);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Husacct");
		setVisible(true);
		
		textPaneInformation.setText("THIS SCREEN IS NOT AVAILABLE YET. /n" +
		"In the “StateView” you can see when it is available");
	
		jPanel.add(textPaneInformation);
		jPanel.setBackground(Color.WHITE);
		getContentPane().add(jPanel);
	}
}