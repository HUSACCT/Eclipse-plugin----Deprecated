package plugin.views;


import husacct.ServiceProvider;
import husacct.control.task.StateController;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.ActionDelegate;
import plugin.views.internalframes.JInternalHusacctMainScreen;

public class HusacctMainView extends ViewPart {	
	
	private ActionDelegate actionDelegate;
	private ServiceProvider serviceProvider;
	private StateController stateController;
	private JInternalFrame JInternalFrameValidate;
 	private JInternalFrame JInternalFrameDefine;
 	private JInternalFrame currentFrame = null;
 	private String currentScreen = "";
 
 	private Frame frame;
 	private Composite composite;
	
	public HusacctMainView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		createInternalFrames();
		createFrame(parent);
		creatButtons();
		parent.setParent(composite);	
	}
	
	private void createFrame(Composite parent){
		composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		//changeScreen(new JInternalHusacctMainScreen());
	}
	
	private void creatButtons(){
	Panel panel = new Panel();	
	
	//SelectSource
	Button buttonSelectSource = new Button("Select Source");
	buttonSelectSource.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	selectSource();
        }
    }); 	
	panel.add(buttonSelectSource);
	
	//Define
	Button buttonDefine = new Button("Define");
	buttonDefine.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	define();
        }
    });  
	panel.add(buttonDefine);
	
	//Define
	Button buttonImportArchitecture = new Button("Import Architecture");
	buttonImportArchitecture.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	importArchitecture();
        }
    });  
	panel.add(buttonImportArchitecture);
	
	//Validate
	Button buttonValidate = new Button("Validate");
	buttonValidate.addActionListener(new ActionListener() {		 
        public void actionPerformed(ActionEvent e)
        {
        	validate();
        }
    });  
	panel.add(buttonValidate);
	frame.add(panel, BorderLayout.PAGE_START);	
	}
	
	private void createInternalFrames(){
		stateController = new StateController();
		serviceProvider = ServiceProvider.getInstance();
		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameValidate.setVisible(true);
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
	}

	private void changeScreen(JInternalFrame jInternalFrame){
		if(currentFrame != null){
			frame.remove(currentFrame.getRootPane());
		}
		currentFrame = jInternalFrame;
		frame.add(jInternalFrame.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
	
	private void selectSource(){
		//actionDelegate.run();
		//actionDelegate.test();
		//serviceProvider.getDefineService().createApplication( "Test application", new String[]{"C:\\Users\\Tim\\workspace\\ViewTests\\src"}, "java", "1.0");
		//stateController.setState(1);
	}
	
	private void importArchitecture(){
		
	}
	
	private void define(){
		//if(stateController.getState() >= 0){
			//if(!currentScreen.equals("define")){
				changeScreen(JInternalFrameDefine);
				//currentScreen = "define";
			//}
		//}
	}
	
	private void validate(){
		//stateController.checkState();
		//if(stateController.getState() >= 0){
			//serviceProvider.getAnalyseService().analyseApplication();
			//serviceProvider.getValidateService().checkConformance();
			//if(!currentScreen.equals("validate")){
				changeScreen(JInternalFrameValidate);
			//	currentScreen = "validate";
			//}	
		//}
	}
	
	@Override
	public void setFocus() {}

}
