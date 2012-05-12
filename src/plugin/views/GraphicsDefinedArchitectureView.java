package plugin.views;

import java.awt.BorderLayout;
import java.awt.Frame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class GraphicsDefinedArchitectureView extends ViewPart {
	private Frame frame;
	
	public GraphicsDefinedArchitectureView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		PluginController pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(pluginController.getGraphicsDefinedArchitecture().getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}

	@Override
	public void setFocus() {

	}

}
