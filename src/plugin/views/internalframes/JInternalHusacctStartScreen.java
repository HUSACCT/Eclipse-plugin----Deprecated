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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import org.eclipse.core.runtime.FileLocator;
import plugin.Activator;

public class JInternalHusacctStartScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panelStart, panelHelp;
	private JLabel labelStartInfo;
	private GridLayout gridLayoutStartPanel, gridLayoutHelpPanel;
	private BufferedImage bufferedImage;
	private JScrollPane scrollPane;
	private JTextPane textPaneHelpInformation;
	
	public JInternalHusacctStartScreen() {
		initializeStartPane();
		initializeHelpPane();
		ImageIcon icon = new ImageIcon("pathicon.jpg");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Home", icon, panelStart, "This is the start screen of the plugin");
        tabbedPane.setSelectedIndex(0);
        tabbedPane.addTab("Help", icon, panelHelp, "In this screen you can find an explanation of the workflow");
        getContentPane().add(tabbedPane);
		setBounds(50, 50, 200, 400);
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
		
		labelStartInfo = new JLabel("Click within this view at the “Help” tab for information about the workflow");
		panelStart.add(labelStartInfo);
	}
	
	public void initializeHelpPane(){
		gridLayoutHelpPanel = new GridLayout(1,1);
        textPaneHelpInformation = new JTextPane();
        textPaneHelpInformation.setForeground(Color.BLACK);
        textPaneHelpInformation.setContentType("text/html");
        textPaneHelpInformation.setText(       // ONDERSTAANDE TEKST MOET NOG VERBERERD WORDEN!!!!!!!!!!!!! IS EEN EERSTE OPZET
        		"<font face='verdana' color='black'>" +
        		"<span style='font-size: 10pt'>" +
        		"<span style='font-size: 14pt'>" +
        		"<B><U>HERE YOU CAN FIND INFORMATION ABOUT THE WORKFLOW OF THE HUSACCT ECLIPSE PLUGIN </B></U><BR></span>" + 
        		"<BR>" +
        		"<BR>" +
        		"<span style='font-size: 12pt'>" +
        		"<B>State of the process: </B><BR></span>" + 
        		"Within the “StateView” you can see how far you are in the process and what steps you can take.<BR>" +
        		"At each step you see the state. The states can be: “is performed”, “ready to do” or “not yet available”.<BR>" +
        		"<BR>" +
        		"<BR>" +
        		"<span style='font-size: 12pt'>" +
        		"<B><U>Steps:</B></U><BR>" +
        		"<BR></span>" +
        		"<B><I>Analyse a Java project within Eclipse:</B></I><BR>" +
        		"You can analyse a Java Project within Eclipse by right-click within the “Package Explorer” view <BR>" +
        		"at a Java Project and then you click at “HUSACCT – Analyse Project”.<BR>" +
        		"When de Java project analyse is completed, you can see the analysed application within the “Analyse” view.<BR>" +
        		"<BR>"+
        		"<B><I>Define logical  architecture:</B></I><BR>" +
        		"You can define the logical architecture in the “Define” view.<BR>" +
        		"Within this view you can also import and export the logical architecture.<BR>" +
        		"<BR>"+
        		"<B><I>Validate: </B></I><BR>" +
        		"When the architectural rules are defined and the Java project is analysed, <BR>" +
        		"the “StateView” will show you that you are able to validate your project. <BR>" +
        		"You can do this by opening the “Validate” view and click within this view at the button “Validate”.<BR>" +
        		"When you double-click at a violation, an editor will be opened with the source at the linenumber where the violation occurs.<BR>" +
        		"<BR>"+
        		"<B><I>Graphical representations:</B></I><BR>" +
        		"The HUSACCT Eclipse plugin gives you the ability to view a graphical representation of the analysed and defined architecture. <BR>" +
        		"You can see them by open the views: “Graphics – Analysed” and “Graphics – Defined”.<BR>" +
        		"</span>"
        );
        scrollPane = new JScrollPane(textPaneHelpInformation);
		panelHelp = new JPanel();
		panelHelp.setBackground(Color.WHITE);
		panelHelp.setLayout(gridLayoutHelpPanel);
		panelHelp.add(scrollPane);
	}
}