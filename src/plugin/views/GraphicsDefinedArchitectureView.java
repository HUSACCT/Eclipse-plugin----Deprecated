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
import plugin.views.internalframes.JInternalHusacctNotAvailableFrame;

public class GraphicsDefinedArchitectureView extends ViewPart implements IStateChangeListener {
	private Frame frame;
	private JInternalHusacctNotAvailableFrame notAvailableScreen;

	public GraphicsDefinedArchitectureView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		notAvailableScreen = new JInternalHusacctNotAvailableFrame();
		PluginController.getInstance().getStateController().addStateChangeListener(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.add(notAvailableScreen.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}

	@Override
	public void setFocus() {

	}

	public void changeState(List<States> states) {
		if(states.contains(States.DEFINED)){
			changeScreen(notAvailableScreen,
					PluginController.getInstance().getGraphicsDefinedArchitecture());
		}
		else{
			changeScreen(PluginController.getInstance().getGraphicsDefinedArchitecture(),
					notAvailableScreen);
		}
	}

	public void changeScreen(JInternalFrame jInternalFrameOld, JInternalFrame jInternalFrameNew){
		
		if(frame == null){
			frame.add(jInternalFrameNew.getRootPane(), BorderLayout.CENTER);
			frame.validate();
			frame.repaint();
		}
		else if(frame != null){
			frame.removeAll();
			frame.add(jInternalFrameNew.getRootPane(), BorderLayout.CENTER);
			frame.validate();
			frame.repaint();
		}
	}
}
