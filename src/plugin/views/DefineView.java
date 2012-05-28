package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;

public class DefineView extends ViewPart implements IStateChangeListener, IResetListener{
	private PluginController pluginController;
	private Frame frame;
	private Logger logger = Logger.getLogger(DefineView.class);
	private JInternalHusacctNotAvailableFrame notAvailableScreen;
	private boolean isDefineFrameVisible = false;

	public DefineView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		logger.info("defineView gestart");
		notAvailableScreen = new JInternalHusacctNotAvailableFrame(
				"You have to analyse a project before this screen is available.");
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		changeScreenWithoutButtons(notAvailableScreen);
		parent.setParent(composite);
		pluginController = PluginController.getInstance();
		pluginController.addToStateController(this);
		pluginController.addToResetController(this);
	}
	
	private void createButtons(Frame frame){
		Panel panel = new Panel();	
		
		Button buttonImport = new Button("Import Architecture");
		buttonImport.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	PluginController.getInstance().importArchitecture();
	        }
	    }); 	
		panel.add(buttonImport);
		
		Button buttonExport = new Button("Export Architecture");
		buttonExport.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	PluginController.getInstance().exportArchitecture();
	        }
	    }); 	
		panel.add(buttonExport);
		panel.setBackground(Color.LIGHT_GRAY);
		
		frame.add(panel, BorderLayout.PAGE_START);
	}

	@Override
	public void setFocus() {
	}
	
	public void changeScreen(JInternalFrame jInternalFrame){
		frame.removeAll();
		frame.add(jInternalFrame.getRootPane(), BorderLayout.CENTER);
		createButtons(frame);
		frame.validate();
		frame.repaint();
	}
	
	public void changeScreenWithoutButtons(JInternalFrame jInternalFrame){
		frame.removeAll();
		frame.add(jInternalFrame.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
	
	@Override
	public void changeState(List<States> states) {		
		if(states.contains(States.ANALYSED) && !isDefineFrameVisible){
			changeScreen(FrameInstanceController.getDefineFrame());
			isDefineFrameVisible = true;
		}
		else if(!states.contains(States.ANALYSED) && isDefineFrameVisible){
			changeScreenWithoutButtons(notAvailableScreen);
			isDefineFrameVisible = false;
		}
	}

	@Override
	public void reset() {
		changeScreen(notAvailableScreen);
		isDefineFrameVisible = false;
	}
}
