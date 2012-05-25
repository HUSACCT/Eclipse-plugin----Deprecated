package plugin.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;

public class DefineView extends ViewPart implements IResetListener{
	private PluginController pluginController;
	private Frame frame;
	private Logger logger = Logger.getLogger(DefineView.class);

	public DefineView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		logger.info("defineView gestart");
		pluginController = PluginController.getInstance();
		pluginController.addToResetController(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(FrameInstanceController.getDefineFrame().getRootPane(), BorderLayout.CENTER);
		createButtons(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
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

	@Override
	public void reset() {
		frame.removeAll();
		frame.add(FrameInstanceController.getDefineFrame().getRootPane(), BorderLayout.CENTER);
		createButtons(frame);
		frame.validate();
		frame.repaint();
	}
}
