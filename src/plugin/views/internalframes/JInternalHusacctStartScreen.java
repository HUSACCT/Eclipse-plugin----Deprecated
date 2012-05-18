package plugin.views.internalframes;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import org.eclipse.core.runtime.FileLocator;
import plugin.Activator;

public class JInternalHusacctStartScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelStart, panelHelp;
	private JLabel labelStartInfo;
	private JTextArea textAreaHelpInfo;
	private GridLayout gridLayoutStartPanel, gridLayoutHelpPanel;
	private BufferedImage bufferedImage;

	
	public JInternalHusacctStartScreen() {
		
		initializeStartPane();
		initializeHelpPane();
		
		ImageIcon icon = new ImageIcon("pathicon.jpg");
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Home", icon, panelStart, "This is the start screen of the plugin");
        tabbedPane.setSelectedIndex(0);
        tabbedPane.addTab("Help", icon, panelHelp, "In this view you see an explanation of the workflow");
		
        getContentPane().add(tabbedPane);
		setBounds(50, 50, 200, 200);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Husacct");
		setVisible(true);
	}	
	
	public void initializeStartPane(){
		gridLayoutStartPanel = new GridLayout(2,1);
		panelStart = new JPanel();
		panelStart.setLayout(gridLayoutStartPanel);
		panelStart.setBackground(Color.WHITE);
        
		try {
			final String husacctpng = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("icons/husacct.png")).getPath();
			bufferedImage = ImageIO.read(new File(husacctpng));
			JLabel pictureLabel = new JLabel(new ImageIcon(bufferedImage));
			panelStart.add(pictureLabel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		labelStartInfo = new JLabel("Click within this view at the 'Help' tab for information about the workflow");
		panelStart.add(labelStartInfo);
	}
	
	public void initializeHelpPane(){
		gridLayoutHelpPanel = new GridLayout(10,2);

		textAreaHelpInfo = new JTextArea();
		textAreaHelpInfo.setText("--> Hier komt nog hele leuke informatie " + "\n" + "iets wat nuttiger is dan wat er nu staat");
		textAreaHelpInfo.setEditable(false);
		
		panelHelp = new JPanel();
		panelHelp.setBackground(Color.WHITE);
		panelHelp.setLayout(gridLayoutHelpPanel);
		panelHelp.add(textAreaHelpInfo);
	}
}