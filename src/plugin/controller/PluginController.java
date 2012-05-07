package plugin.controller;

import javax.swing.JInternalFrame;

import plugin.views.HusacctMainView;
import plugin.views.internalframes.JInternalHusacctImportArchitecture;
import plugin.views.internalframes.JInternalHusacctSelectSource;

import husacct.ServiceProvider;
import husacct.control.task.StateController;

public class PluginController {
	private HusacctMainView hussactMainView;
	private ServiceProvider serviceProvider;
	private StateController stateController;
	private JInternalFrame JInternalFrameValidate;
 	private JInternalFrame JInternalFrameDefine;
 	private JInternalFrame JInternalSelectSource;
 	private JInternalFrame JInternalImportArchitecture;
 	private String currentFrame = "";
 	
 	public PluginController(HusacctMainView hussactMainView){
 		this.hussactMainView = hussactMainView;
 		stateController = new StateController();
		serviceProvider = ServiceProvider.getInstance();
		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameValidate.setVisible(true);
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
		JInternalSelectSource = new JInternalHusacctSelectSource();
		JInternalImportArchitecture = new JInternalHusacctImportArchitecture();
 	}
 	
	public void selectSource(){
		serviceProvider.getDefineService().createApplication( "Test application", new String[]{"C:\\Users\\Tim\\workspace\\ViewTests\\src"}, "java", "1.0");
		if(!currentFrame.equals("selectSource")){
			hussactMainView.changeScreen(JInternalSelectSource);
			currentFrame = "selectSource";
		}
	}
 	
	public void define(){
		stateController.checkState();
		if(stateController.getState() >= 1){
			if(!currentFrame.equals("define")){
				hussactMainView.changeScreen(JInternalFrameDefine);
				currentFrame = "define";
			}
		}
	}
	
	public void importArchitecture(){
		if(!currentFrame.equals("importArchitecture")){
			hussactMainView.changeScreen(JInternalImportArchitecture);
			currentFrame = "importArchitecture";
		}
	}
	
	public void validate(){
		stateController.checkState();
		if(stateController.getState() >= 4){
			serviceProvider.getAnalyseService().analyseApplication();
			serviceProvider.getValidateService().checkConformance();
			if(!currentFrame.equals("validate")){
				hussactMainView.changeScreen(JInternalFrameValidate);
				currentFrame = "validate";
			}
		}
	}
}
