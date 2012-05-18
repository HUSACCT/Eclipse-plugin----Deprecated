package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class GraphicsAnalysedArchitectureView extends ViewPart implements IStateChangeListener {
	private Frame frame;
	
	public GraphicsAnalysedArchitectureView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		PluginController.getInstance().getPluginStateController().addStateChangeListener(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		frame = SWT_AWT.new_Frame(composite);
		frame.add( PluginController.getInstance().getGraphicsAnalysedArchitecture().getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		frame.setVisible(false);
		parent.setParent(composite);		
	}
	
	@Override
	public void setFocus() {
	}

	@Override
	public void changeState(List<States> states) {
		if(states.contains(States.OPENED)){
			frame.setVisible(true);
			frame.validate();
			frame.repaint();
		}
		else{
			frame.setVisible(false);
			frame.validate();
			frame.repaint();
		}
	}
}
