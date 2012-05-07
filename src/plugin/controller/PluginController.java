package plugin.controller;

import java.io.IOException;

import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;

import plugin.Activator;
import plugin.views.HusacctMainView;
import plugin.views.internalframes.JInternalHusacctImportArchitecture;
import plugin.views.internalframes.JInternalHusacctSelectSource;
import husacct.Main;
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
 		initializeLogger();
 		serviceProvider = ServiceProvider.getInstance();
 		stateController = new StateController();
 		initializeFrames();
 	}
 	
 	private void initializeLogger(){
 		try {
			String loggerFile = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("husacct.properties")).getPath();
			PropertyConfigurator.configure(loggerFile);
			Logger logger = Logger.getLogger(Main.class);
			logger.debug("Starting HUSACCT");
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
 	}
 	
 	private void initializeFrames(){
 		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameValidate.setVisible(true);
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
		JInternalSelectSource = new JInternalHusacctSelectSource(this);
		JInternalImportArchitecture = new JInternalHusacctImportArchitecture(this);
 	}
 	
	public void showSelectSourceFrame(){
		if(!currentFrame.equals("selectSource")){
			hussactMainView.changeScreen(JInternalSelectSource);
			currentFrame = "selectSource";
		}
	}
 	
	public void showDefineFrame(){
		if(!currentFrame.equals("define")){
			hussactMainView.changeScreen(JInternalFrameDefine);
			currentFrame = "define";
		}
	}
	
	public void showImportArchitectureFrame(){
		if(!currentFrame.equals("importArchitecture")){
			hussactMainView.changeScreen(JInternalImportArchitecture);
			currentFrame = "importArchitecture";
		}
	}
	
	public void showValidateFrame(){
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
	
	public void sourceSelected(String[] sources, String version){
		System.out.println("Selecting source");
		serviceProvider.getDefineService().createApplication( "Eclipseplugin", sources, "Java", version);		
		System.out.println("Analyzing source");
		serviceProvider.getAnalyseService().analyseApplication();
	}

	public void importArchitecture() {
		System.out.println(stateController.getState());
	}
}
