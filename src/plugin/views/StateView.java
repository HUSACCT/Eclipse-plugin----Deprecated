package plugin.views;

import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import java.awt.BorderLayout;
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
	private JLabel sourceSelectLabel, definedLabel, mappedLabel, validatedLabel;
	private JLabel sourceSelectImage, definedImage, mappedImage, ValidatedImage;
	private Frame frame;
	private final String availableIcon = "icons/availableIcon.png";
	private final String correctIcon = "icons/correctIcon.png";
	private final String wrongIcon = "icons/wrongIcon.png";
	private String sourceSelectTextNotChosen = "No project has been selected. You can selected a project by right clicking on the project in the package explorer and choosing 'HUSSACT - analyze project'";

	public StateView() {
	}

	@Override
	public void createPartControl(Composite parent)  {
		PluginController.getInstance().getPluginStateController().addStateChangeListener(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		createLabels(frame);
		fillPanel();
		parent.setParent(composite);
		this.setFocus();
	}
	
	private void createLabels(Frame frame){		
	    sourceSelectLabel = new JLabel("Source Selected");
		sourceSelectImage = new JLabel("");	
		
		definedLabel = new JLabel("Defined");
		definedLabel.setToolTipText("This shows if you have defined a architecture");	
		definedImage = new JLabel("");	
		
		mappedLabel = new JLabel("Mapped");
		mappedLabel.setToolTipText("This shows if you have mapped the defined architecture to the source");	
		mappedImage = new JLabel("");
		
		validatedLabel = new JLabel("Validated");
		validatedLabel.setToolTipText("This shows if the code is checked on the defined rules");	
		ValidatedImage = new JLabel("");	
	}
	
	private void fillPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(sourceSelectLabel);
		panel.add(sourceSelectImage);
		panel.add(definedLabel);
		panel.add(definedImage);
		panel.add(mappedLabel);
		panel.add(mappedImage);
		panel.add(validatedLabel);
		panel.add(ValidatedImage);
		frame.add(panel, BorderLayout.NORTH);		
	}
	
	private void setAvailable(JLabel label){
		label.setIcon(new ImageIcon(setImage(availableIcon)));
		label.setToolTipText("This step is available");
	}
	
	private void setDone(JLabel label){
		label.setIcon(new ImageIcon(setImage(correctIcon)));
		label.setToolTipText("This step is done");
	}
	
	private void setNotAvailable(JLabel label){
		label.setIcon(new ImageIcon(setImage(wrongIcon)));
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
		PluginController.getInstance().getPluginStateController().checkState();
	}
	
	private String getSourceSelectTextChosen(){
		return "The selected project is: " + PluginController.getInstance().getProjectName();
	}

	@Override
	public void changeState(List<States> states) {
		if(states.contains(States.VALIDATED)){
			sourceSelectLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(sourceSelectImage);
			setDone(definedImage);
			setDone(mappedImage);
			setDone(ValidatedImage);
		}
		else if(states.contains(States.MAPPED)){
			sourceSelectLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(sourceSelectImage);
			setDone(definedImage);
			setDone(mappedImage);
			setAvailable(ValidatedImage);
		}
		else if(states.contains(States.DEFINED) && states.contains(States.OPENED)){
			sourceSelectLabel.setToolTipText(getSourceSelectTextChosen());	
			setDone(sourceSelectImage);
			setDone(definedImage);
			setAvailable(mappedImage);
			setNotAvailable(ValidatedImage);
		}
		else if(states.contains(States.DEFINED) && !states.contains(States.OPENED)){
			sourceSelectLabel.setToolTipText(getSourceSelectTextChosen());
			setAvailable(sourceSelectImage);
			setDone(definedImage);
			setAvailable(mappedImage);
			setNotAvailable(ValidatedImage);
		}	
		else if(states.contains(States.OPENED)){
			sourceSelectLabel.setToolTipText(getSourceSelectTextChosen());
			setDone(sourceSelectImage);
			setAvailable(definedImage);
			setNotAvailable(mappedImage);
			setNotAvailable(ValidatedImage);
		}	
		else{
			sourceSelectLabel.setToolTipText(sourceSelectTextNotChosen);
			setAvailable(sourceSelectImage);
			setAvailable(definedImage);
			setNotAvailable(mappedImage);
			setNotAvailable(ValidatedImage);
		}		
		frame.validate();
		frame.repaint();
	}

}
