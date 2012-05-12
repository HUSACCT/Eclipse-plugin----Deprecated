package plugin.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class GraphicsView extends ViewPart {
	private JInternalFrame currentFrame;
	private Frame frame;
	
	public GraphicsView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		PluginController pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		createButtons();	
		frame.add(pluginController.getGraphicsAnalysedArchitecture().getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void changeScreen(JInternalFrame jInternalFrame){
		if(currentFrame != null && currentFrame == jInternalFrame){
			frame.remove(currentFrame.getRootPane());
		}
		currentFrame = jInternalFrame;	
		frame.add(currentFrame.getRootPane(), BorderLayout.CENTER);		
		frame.validate();
		frame.repaint();
		}
	
	private void createButtons(){
		Panel panel = new Panel();			
		Button buttonAnalysedArchitecture = new Button("Analysed Architecture");
		buttonAnalysedArchitecture.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	changeScreen(PluginController.getInstance().getGraphicsAnalysedArchitecture());
	        }
	    }); 	
		panel.add(buttonAnalysedArchitecture);
		
		Button buttonDefinedArchitecture = new Button("Defined Architecture");
		buttonDefinedArchitecture.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	changeScreen(PluginController.getInstance().getGraphicsDefinedArchitecture());
	        }
	    }); 	
		panel.add(buttonDefinedArchitecture);
		frame.add(panel, BorderLayout.PAGE_START);
	}

	@Override
	public void setFocus() {
		
	}

}
