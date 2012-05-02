package plugin.views;


import husacct.ServiceProvider;
import husacct.control.task.StateController;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
//import plugin.ActionDelegate;


public class HusacctMainView extends ViewPart {	
	
	//ActionDelegate actionDelegate;
	ServiceProvider serviceProvider = ServiceProvider.getInstance();
	StateController stateController = new StateController();
	JInternalFrame JInternalFrameValidate;
 	JInternalFrame JInternalFrameDefine;
 	String currentScreen = "";
 
 	Frame frame;
 	Composite composite;
	
	public HusacctMainView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		createInternalFrames();
		createFrame(parent);
		createTable();
		parent.setParent(composite);	
	}
	
	private void createFrame(Composite parent){
		composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		BorderLayout borderLayout = new BorderLayout();
		frame.setLayout(borderLayout);
	}
	
	private void createTable(){
		String[] columnNames = {"Actions"}; 
		Object[][] data = {
				{"Select Source"},
				{"Define Architecture"},
				{"Import Architecture"},
				{"Validate"},
		}; 
		final JTable jTable = new JTable(data, columnNames);
		jTable.setSize(500,500);
		jTable.setFillsViewportHeight(true);
		jTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int column = jTable.getSelectedRow();
				if (column == 0){ //Select source
					selectSource();
				}
				else if(column == 1){//Define Architecture
					define();			
				}
				else if(column == 2){//Import Architecture   
					importArchitecture();
				}
				else if(column == 3){//Validate 
					validate();					
				}
			}
		});
		frame.add(jTable, BorderLayout.LINE_START);
	}
	
	private void createInternalFrames(){
		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
	}

	private void changeScreen(JInternalFrame jif){
		frame.add(jif.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
	
	private void selectSource(){
		//actionDelegate.run();
		//actionDelegate.test();
		serviceProvider.getDefineService().createApplication( "Test application", new String[]{"C:\\Users\\Tim\\workspace\\ViewTests\\src"}, "java", "1.0");
		stateController.setState(1);
	}
	
	private void importArchitecture(){
		
	}
	
	private void define(){
		if(stateController.getState() >= 1){
			if(!currentScreen.equals("define")){
				changeScreen(JInternalFrameDefine);
				currentScreen = "define";
			}
		}
	}
	
	private void validate(){
		stateController.checkState();
		if(stateController.getState() >= 4){
			serviceProvider.getAnalyseService().analyseApplication();
			serviceProvider.getValidateService().checkConformance();
			if(!currentScreen.equals("validate")){
				changeScreen(JInternalFrameValidate);
				currentScreen = "validate";
			}	
		}
	}
	
	@Override
	public void setFocus() {}

}
