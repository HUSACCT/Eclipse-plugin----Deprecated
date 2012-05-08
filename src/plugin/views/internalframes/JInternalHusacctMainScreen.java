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

public class JInternalHusacctMainScreen extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	public JInternalHusacctMainScreen() {
		BufferedImage bufferedImage;
		
		try {
			final String husacctpng = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("icons/husacct.png")).getPath();
			bufferedImage = ImageIO.read(new File(husacctpng));
			JLabel pictureLabel = new JLabel(new ImageIcon(bufferedImage));
			getContentPane().add(pictureLabel);
			
		} catch (IOException e) {
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