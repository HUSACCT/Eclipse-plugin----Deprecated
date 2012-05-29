package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.beans.PropertyVetoException;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;

public class GraphicsAnalysedArchitectureView extends ViewPart implements IStateChangeListener, IResetListener  {
	private Frame frame;
	private JInternalHusacctNotAvailableFrame notAvailableScreen;
	private JInternalFrame graphicsAnalysedArchitecture;
	private boolean isAnalysedArchitectureFrameVisible;

	public GraphicsAnalysedArchitectureView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		notAvailableScreen = new JInternalHusacctNotAvailableFrame(
			"You have to analyse a project before this screen is available.");
		graphicsAnalysedArchitecture = FrameInstanceController.getGraphicsAnalysedArchitecture();
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
		if(states.contains(States.ANALYSED) && !isAnalysedArchitectureFrameVisible){
			changeScreen(graphicsAnalysedArchitecture);
			isAnalysedArchitectureFrameVisible = true;
		}
		else if(!states.contains(States.ANALYSED) && isAnalysedArchitectureFrameVisible){
			changeScreen(notAvailableScreen);
			isAnalysedArchitectureFrameVisible = false;
		}
	}

	public void changeScreen(JInternalFrame jInternalFrame){
		frame.removeAll();
		JSplitPane jeSplitPane = new JSplitPane();
		jeSplitPane.removeAll();
		jeSplitPane.add(jInternalFrame, JSplitPane.TOP);
		try {
			jInternalFrame.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		frame.add(jeSplitPane, BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}

	@Override
	public void reset() {
		graphicsAnalysedArchitecture = FrameInstanceController.getGraphicsAnalysedArchitecture();
		changeScreen(notAvailableScreen);
		isAnalysedArchitectureFrameVisible = false;
	}
}
