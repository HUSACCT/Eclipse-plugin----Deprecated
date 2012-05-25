package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;
import javax.swing.JInternalFrame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;

public class GraphicsDefinedArchitectureView extends ViewPart implements IStateChangeListener, IResetListener {
	private Frame frame;
	private JInternalHusacctNotAvailableFrame notAvailableScreen;
	private JInternalFrame definedArchitectureFrame;
	private boolean isDefinedArchitectureFrameVisible;
	
	public GraphicsDefinedArchitectureView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		PluginController pluginController = PluginController.getInstance();
		pluginController.addToStateController(this);
		pluginController.addToResetController(this);
		notAvailableScreen = new JInternalHusacctNotAvailableFrame();
		definedArchitectureFrame = FrameInstanceController.getGraphicsDefinedArchitecture();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.add(notAvailableScreen.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
		pluginController.checkState();
	}

	@Override
	public void setFocus() {

	}

	public void changeState(List<States> states) {
		if(states.contains(States.DEFINED) && !isDefinedArchitectureFrameVisible){
			changeScreen(definedArchitectureFrame);
			isDefinedArchitectureFrameVisible = true;
		}
		else if(!states.contains(States.DEFINED) && isDefinedArchitectureFrameVisible){
			changeScreen(notAvailableScreen);
			isDefinedArchitectureFrameVisible = false;
		}
	}

	public void changeScreen(JInternalFrame jInternalFrame){
		frame.removeAll();
		frame.add(jInternalFrame.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}

	@Override
	public void reset() {
		definedArchitectureFrame = FrameInstanceController.getGraphicsDefinedArchitecture();
		changeScreen(notAvailableScreen);
		isDefinedArchitectureFrameVisible = false;		
	}
}
