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
import javax.swing.JTextPane;
import org.eclipse.core.runtime.FileLocator;
import plugin.Activator;

public class JInternalHusacctStartScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelStart, panelHelp;
	private JLabel labelStartInfo, labelHelpInfo;
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
		labelHelpInfo = new JLabel(" HERE CAN YOU FIND INFORMATION ABOUT THE WORKFLOW OF THE HUSACCT ECLIPSE PLUGIN");
		textAreaHelpInfo = new JTextArea();
		textAreaHelpInfo.setText	("test"
				
						);
		//textAreaHelpInfo.setEditable(false);
        JTextPane textPane = new JTextPane();
//        Font font = new Font("Cambria", Font.PLAIN, 13);
//        textPane.setFont(font);
        
        textPane.setForeground(Color.BLACK);
       // textPane.setContentType("text/html");
        
        textPane.setText( 
        		"State of the process:  \n" +
        		"Within the “StateView” you can see how far you are in the process and what steps you can take. \n" +
        		"In this view you can see if a step is performed, ready to do or not yet available \n" +
        		"\n" +

        		"Steps: \n" +
        		"\n"+
        		
        		"Define logical  architecture: 	\n" +
        		"You can define the logical architecture in the “Define” view. \n" +
        		"Within this view you can also import and export the logical architecture. \n" +
        		"\n"+
        		
        		"Analyse a Java project within Eclipse: \n" +
        		"You can analyse a Java Project within Eclipse by right-click at a Java Project "+
        		"in the “Package Explorer” view and click at “HUSACCT – Analyse Project”."+
        		"When de Java project analyse is completed, you can see the analysed application within the “Analyse” View .\n" +
        		"\n"+
        		
        		"Validate: \n" +
        		"Wanneer de architectural rules gedefinieerd zijn en het Java project geanalyseerd is, \n" +
        		"zal de “StateView” laten zien dat u kunt Valideren. \n" +
        		"U kunt dit doen door de Validate View te openen en binnen deze view op de knop “Validate” te klikken.\n" +
        		"Als u dubbelklikt op een violation zal de source geopend worden op de regel waar de violation zich voordoet.\n" +
        		"\n"+
        		
        		"Graphical representation:	\n" +
        		"Het is mogelijk om een grafische representatie van de analysed architecture te laten zien. \n" +
        		"Dit kunt u doen door de “Graphics – Analysed” view te openen.\n" +
        		"Ook is er een graphical representation van de defined architecture te bekijken. \n" +
        		"Dit doet u door de “Graphics – Defined” view te openen.\n"
        		
        
        
        );
        textAreaHelpInfo.setSize(500, 500);
        
		panelHelp = new JPanel();
		panelHelp.setBackground(Color.WHITE);
		panelHelp.setLayout(gridLayoutHelpPanel);
		panelHelp.add(labelHelpInfo);
		//panelHelp.add(textAreaHelpInfo);
		panelHelp.add(textPane);
	}
}