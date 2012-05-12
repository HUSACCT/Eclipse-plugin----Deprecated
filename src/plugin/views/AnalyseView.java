package plugin.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import java.awt.BorderLayout;
import java.awt.Frame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import plugin.controller.PluginController;

public class AnalyseView extends ViewPart {
	private PluginController pluginController;
	
	public AnalyseView() {
		 
	}

	@Override
	public void createPartControl(Composite parent) {
		pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(pluginController.getAnalyseFrame().getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}

	@Override
	public void setFocus() {
		 
	}
}
