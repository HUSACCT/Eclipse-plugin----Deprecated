package plugin.views.internalframes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class JInternalHusacctMainScreen extends JInternalFrame {

	public JInternalHusacctMainScreen() {
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("C:\\husacct.png"));
			JLabel picLabel = new JLabel(new ImageIcon( myPicture ));
			getContentPane().add(picLabel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		    setBounds(50, 50, 200, 200);
		    setResizable(true);
		    setClosable(true);
		    setMaximizable(true);
		    setIconifiable(true);
		    setTitle("Husacct");
		   setVisible(true);
	}	
}