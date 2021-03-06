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

import plugin.controllers.FrameInstanceController;
import plugin.controllers.PluginController;
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;

public class AnalyseView extends ViewPart implements IStateChangeListener {
	private Frame frame;
	private JInternalHusacctNotAvailableFrame notAvailableScreen;
	private boolean isAnalyseFrameVisible;


	@Override
	public void createPartControl(Composite parent) {
		notAvailableScreen = new JInternalHusacctNotAvailableFrame(
				"You have to analyse a project before this screen is available.");
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.add(notAvailableScreen.getRootPane(), BorderLayout.CENTER);
		isAnalyseFrameVisible = false;
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
		PluginController pluginController = PluginController.getInstance();
		pluginController.addToStateController(this);
	}

	@Override
	public void setFocus() {
	}

	public void changeState(List<States> states) {
		if(states.contains(States.ANALYSED) && !isAnalyseFrameVisible){
			changeScreen(FrameInstanceController.getAnalyseFrame());
			isAnalyseFrameVisible = true;
		}
		else if(!states.contains(States.ANALYSED) && isAnalyseFrameVisible){
			changeScreen(notAvailableScreen);
			isAnalyseFrameVisible = false;
		}
	}

	public void changeScreen(JInternalFrame jInternalFrame){
		frame.removeAll();
		frame.add(jInternalFrame.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
}
