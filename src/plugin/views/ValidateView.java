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

import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;
import plugin.views.internalframes.JInternalHusacctViolationsFrame;

public class ValidateView extends ViewPart implements IStateChangeListener, IResetListener {
	private Frame frame;
	private JInternalHusacctNotAvailableFrame notAvailableScreen;
	private boolean isViolationFrameVisible = false;
	
	public ValidateView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		notAvailableScreen = new JInternalHusacctNotAvailableFrame();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.add(notAvailableScreen.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
		PluginController pluginController = PluginController.getInstance();
		pluginController.addToStateController(this);
		pluginController.addToResetController(this);
	}

	@Override
	public void setFocus() {

	}
	
	public void changeState(List<States> states) {
		if(states.contains(States.MAPPED) && !isViolationFrameVisible){
			changeScreen(new JInternalHusacctViolationsFrame());
			isViolationFrameVisible = true;
		}
		else if(!states.contains(States.MAPPED) && isViolationFrameVisible){
			changeScreen(notAvailableScreen);
			isViolationFrameVisible = false;
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
		changeScreen(notAvailableScreen);
		isViolationFrameVisible = false;
	}
}
