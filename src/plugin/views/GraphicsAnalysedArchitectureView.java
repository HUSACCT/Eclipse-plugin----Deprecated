package plugin.views;

import java.awt.BorderLayout;
import java.awt.Frame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class GraphicsAnalysedArchitectureView extends ViewPart {
	private Frame frame;
	
	public GraphicsAnalysedArchitectureView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		PluginController pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(pluginController.getGraphicsAnalysedArchitecture().getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	@Override
	public void setFocus() {
		
	}
}
