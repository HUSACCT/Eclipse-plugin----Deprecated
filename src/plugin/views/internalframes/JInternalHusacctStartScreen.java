package plugin.views.internalframes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import org.eclipse.core.runtime.FileLocator;
import plugin.Activator;

public class JInternalHusacctStartScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JLabel label1, label2;
	
	
	public JInternalHusacctStartScreen() {
		BufferedImage bufferedImage;
		
		try {
			final String husacctpng = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("icons/husacct.png")).getPath();
			bufferedImage = ImageIO.read(new File(husacctpng));
			JLabel pictureLabel = new JLabel(new ImageIcon(bufferedImage));
			getContentPane().add(pictureLabel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel label1 = new JLabel("hoi");
		JLabel label2 = new JLabel("doei");		
		
		setBounds(50, 50, 200, 200);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Husacct");
		setVisible(true);
	}	
}