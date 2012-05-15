package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class StateView extends ViewPart implements IStateChangeListener {
	private JLabel source, defined, mapped, validated;
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
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(0,1));
	    source = new JLabel("Source Selected");
	    source.setOpaque(true);
	    source.setToolTipText("Click this button to disable the middle button.");
		panel.add(source);
		defined = new JLabel("Defined");;
		defined.setOpaque(true);
		panel.add(defined);
		mapped = new JLabel("Mapped");
		mapped.setOpaque(true);
		panel.add(mapped);
		validated = new JLabel("Validated");
		validated.setOpaque(true);
		panel.add(validated);
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
			source.setBackground(Color.GREEN);
			defined.setBackground(Color.GREEN);
			mapped.setBackground(Color.GREEN);
			validated.setBackground(Color.GREEN);
		}
		else if(states.contains(States.MAPPED)){
			source.setBackground(Color.GREEN);
			defined.setBackground(Color.GREEN);
			mapped.setBackground(Color.GREEN);
			validated.setBackground(Color.YELLOW);
		}
		else if(states.contains(States.DEFINED) && states.contains(States.OPENED)){
			source.setBackground(Color.GREEN);
			defined.setBackground(Color.GREEN);
			mapped.setBackground(Color.YELLOW);
			validated.setBackground(Color.RED);
		}
		else if(states.contains(States.DEFINED) && !states.contains(States.OPENED)){
			source.setBackground(Color.YELLOW);
			defined.setBackground(Color.GREEN);
			mapped.setBackground(Color.YELLOW);
			validated.setBackground(Color.RED);
		}	
		else if(states.contains(States.OPENED)){
			source.setBackground(Color.GREEN);
			defined.setBackground(Color.YELLOW);
			mapped.setBackground(Color.RED);
			validated.setBackground(Color.RED);
		}	
		else{
			source.setBackground(Color.YELLOW);
			defined.setBackground(Color.YELLOW);
			mapped.setBackground(Color.RED);
			validated.setBackground(Color.RED);
		}		
		frame.validate();
		frame.repaint();
	}

}
