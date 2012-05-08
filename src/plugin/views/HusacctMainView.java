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
import plugin.views.internalframes.JInternalHusacctMainScreen;

public class HusacctMainView extends ViewPart {	
	
 	private JInternalFrame currentFrame = null;
 	private PluginController pluginController;
 	private Frame frame;
 	private Composite composite;
	
	public HusacctMainView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		pluginController = new PluginController(this);
		createFrame(parent);
		createButtons();
		parent.setParent(composite);	
	}
	
	private void createFrame(Composite parent){
		composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		changeScreen(new JInternalHusacctMainScreen());
	}
	
	private void createButtons(){
	Panel panel = new Panel();	
	
	//SelectSource
	Button buttonSelectSource = new Button("Select Source");
	buttonSelectSource.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	pluginController.showSelectSourceFrame();
        }
    }); 	
	panel.add(buttonSelectSource);
	
	//Define
	Button buttonDefine = new Button("Define");
	buttonDefine.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	pluginController.showDefineFrame();
        }
    });  
	panel.add(buttonDefine);
	
	//Import architecture
	Button buttonImportArchitecture = new Button("Import Architecture");
	buttonImportArchitecture.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	pluginController.showImportArchitectureFrame();
        }
    });  
	panel.add(buttonImportArchitecture);
	
	//Validate
	Button buttonValidate = new Button("Validate");
	buttonValidate.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	pluginController.showValidateFrame();
        }
    });  
	panel.add(buttonValidate);
	frame.add(panel, BorderLayout.PAGE_START);	
	}
	
	public void changeScreen(JInternalFrame jInternalFrame){
		if(currentFrame != null){
			frame.remove(currentFrame.getRootPane());
		}
		currentFrame = jInternalFrame;
		frame.add(currentFrame.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
	
	@Override
	public void setFocus() {}

}
