package plugin.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.PluginController;

public class ValidateView extends ViewPart {
	private PluginController pluginController;

	public ValidateView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(pluginController.getValidateFrame().getRootPane(), BorderLayout.CENTER);
		createButton(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void createButton(Frame frame){
		Panel panel = new Panel();	
		Button buttonValidate = new Button("Validate");
		buttonValidate.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	pluginController.validate();
	        }
	    }); 	
		panel.add(buttonValidate);
		frame.add(panel, BorderLayout.PAGE_START);
	}

	@Override
	public void setFocus() {
	}

}
