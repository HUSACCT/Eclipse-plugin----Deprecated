package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class StateView extends ViewPart implements IStateChangeListener {
	private JLabel sourceSelectLabel, definedLabel, mappedLabel, validatedLabel;
	private Frame frame;

	public StateView() {
	}

	@Override
	public void createPartControl(Composite parent)  {
		PluginController.getInstance().getPluginStateController().addStateChangeListener(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		createLabels(frame);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void createLabels(Frame frame){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
	    sourceSelectLabel = new JLabel("Source Selected");
	    sourceSelectLabel.setOpaque(true);
	    sourceSelectLabel.setToolTipText("This shows if you have set the Source");
		panel.add(sourceSelectLabel);
		definedLabel = new JLabel("Defined");;
		definedLabel.setOpaque(true);
		definedLabel.setToolTipText("This shows if you have defined a Architecture");
		panel.add(definedLabel);
		mappedLabel = new JLabel("Mapped");
		mappedLabel.setOpaque(true);
		panel.add(mappedLabel);
		validatedLabel = new JLabel("Validated");
		validatedLabel.setOpaque(true);
		panel.add(validatedLabel);
		panel.setToolTipText("Test panel");
		frame.add(panel);
		this.setFocus();
	}
	
	@Override
	public void setFocus() {
		PluginController.getInstance().getPluginStateController().checkState();
	}

	@Override
	public void changeState(List<States> states) {
		if(states.contains(States.VALIDATED)){
			sourceSelectLabel.setBackground(Color.GREEN);
			definedLabel.setBackground(Color.GREEN);
			mappedLabel.setBackground(Color.GREEN);
			validatedLabel.setBackground(Color.GREEN);
		}
		else if(states.contains(States.MAPPED)){
			sourceSelectLabel.setBackground(Color.GREEN);
			definedLabel.setBackground(Color.GREEN);
			mappedLabel.setBackground(Color.GREEN);
			validatedLabel.setBackground(Color.YELLOW);
		}
		else if(states.contains(States.DEFINED) && states.contains(States.OPENED)){
			sourceSelectLabel.setBackground(Color.GREEN);
			definedLabel.setBackground(Color.GREEN);
			mappedLabel.setBackground(Color.YELLOW);
			validatedLabel.setBackground(Color.RED);
		}
		else if(states.contains(States.DEFINED) && !states.contains(States.OPENED)){
			sourceSelectLabel.setBackground(Color.YELLOW);
			definedLabel.setBackground(Color.GREEN);
			mappedLabel.setBackground(Color.YELLOW);
			validatedLabel.setBackground(Color.RED);
		}	
		else if(states.contains(States.OPENED)){
			sourceSelectLabel.setBackground(Color.GREEN);
			definedLabel.setBackground(Color.YELLOW);
			mappedLabel.setBackground(Color.RED);
			validatedLabel.setBackground(Color.RED);
		}	
		else{
			sourceSelectLabel.setBackground(Color.YELLOW);
			definedLabel.setBackground(Color.YELLOW);
			mappedLabel.setBackground(Color.RED);
			validatedLabel.setBackground(Color.RED);
		}		
		frame.validate();
		frame.repaint();
	}

}
