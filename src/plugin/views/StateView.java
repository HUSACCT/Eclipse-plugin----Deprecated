package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.eclipse.core.runtime.FileLocator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.Activator;
import plugin.controller.PluginController;

public class StateView extends ViewPart implements IStateChangeListener {
	private JLabel analyseLabel, defineLabel, mappedLabel, validateLabel;
	private JLabel analyseImage, defineImage, mappedImage, validateImage;
	private Frame frame;
	private final String availableIcon = "icons/availableIcon.png";
	private final String succeededIcon = "icons/succeededIcon.png";
	private final String blockedIcon = "icons/blockedIcon.png";
	private String sourceSelectTextNotChosen = "No project has been selected. " +
			"You can selected a project by right clicking on the project in the " +
			"package explorer and choosing 'HUSSACT - analyze project'";

	public StateView() {
	}

	@Override
	public void createPartControl(Composite parent)  {
		PluginController.getInstance().addToStateController(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		createLabels(frame);
		fillPanel();
		parent.setParent(composite);
		PluginController.getInstance().checkState();
	}
	
	private void createLabels(Frame frame){		
	    analyseLabel = new JLabel("Analyse Project");
	    analyseLabel.setToolTipText("This shows if you have analysed a project");
		analyseImage = new JLabel();	
		
		defineLabel = new JLabel("Define Architecture");
		defineLabel.setToolTipText("This shows if you have defined a architecture");	
		defineImage = new JLabel();	
		
		mappedLabel = new JLabel("Mapped");
		mappedLabel.setToolTipText("This shows if you have mapped the defined architecture to the source");	
		mappedImage = new JLabel();
		
		validateLabel = new JLabel("Validate");
		validateLabel.setToolTipText("This shows if the code is checked on the defined rules");	
		validateImage = new JLabel();	
	}
	
	private void fillPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(analyseLabel);
		panel.add(analyseImage);
		
		panel.add(defineLabel);
		panel.add(defineImage);
		
		panel.add(mappedLabel);
		panel.add(mappedImage);
		
		panel.add(validateLabel);
		panel.add(validateImage);
		
		frame.add(panel, BorderLayout.NORTH);	
		panel.setBackground(Color.WHITE);
	}
	
	private void setAvailable(JLabel label){
		label.setIcon(new ImageIcon(setImage(availableIcon)));
		label.setToolTipText("This step is available");
	}
	
	private void setDone(JLabel label){
		label.setIcon(new ImageIcon(setImage(succeededIcon)));
		label.setToolTipText("This step is done");
	}
	
	private void setNotAvailable(JLabel label){
		label.setIcon(new ImageIcon(setImage(blockedIcon)));
		label.setToolTipText("This step is not yet available");
	}
	
	private BufferedImage setImage(String imageLocation){
		String husacctpng;
		try {
			husacctpng = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry(imageLocation)).getPath();
			return ImageIO.read(new File(husacctpng));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setFocus() {
		PluginController.getInstance().checkState();
	}
	
	private String getSourceSelectTextChosen(){
		return "The selected project is: " + PluginController.getInstance().getProjectName();
	}

	@Override
	public void changeState(List<States> states) {
		if(states.contains(States.VALIDATED)){
			analyseLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(analyseImage);
			setDone(defineImage);
			setDone(mappedImage);
			setDone(validateImage);
		}
		else if(states.contains(States.MAPPED)){
			analyseLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(analyseImage);
			setDone(defineImage);
			setDone(mappedImage);
			setAvailable(validateImage);
		}
		else if(states.contains(States.DEFINED) && states.contains(States.OPENED)){
			analyseLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(analyseImage);
			setDone(defineImage);
			setAvailable(mappedImage);
			setNotAvailable(validateImage);
		}
		else if(states.contains(States.DEFINED) && !states.contains(States.OPENED)){
			analyseLabel.setToolTipText(getSourceSelectTextChosen());
			setAvailable(analyseImage);
			setDone(defineImage);
			setAvailable(mappedImage);
			setNotAvailable(validateImage);
		}	
		else if(states.contains(States.OPENED)){
			analyseLabel.setToolTipText(getSourceSelectTextChosen());
			setDone(analyseImage);
			setAvailable(defineImage);
			setNotAvailable(mappedImage);
			setNotAvailable(validateImage);
		}	
		else{
			analyseLabel.setToolTipText(sourceSelectTextNotChosen);
			setAvailable(analyseImage);
			setAvailable(defineImage);
			setNotAvailable(mappedImage);
			setNotAvailable(validateImage);
		}		
		frame.validate();
		frame.repaint();
	}
}
