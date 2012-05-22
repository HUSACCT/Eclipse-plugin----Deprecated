package plugin.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.custom.BusyIndicator;

import husacct.Main;
import husacct.ServiceProvider;
import husacct.common.dto.ModuleDTO;
import husacct.common.dto.ViolationDTO;
import husacct.control.ControlServiceImpl;
import husacct.control.task.MainController;
import husacct.control.task.StateController;
import husacct.control.task.WorkspaceController;

public class PluginController {
	private static PluginController pluginController = null;
	
	private ServiceProvider serviceProvider;
	private StateController stateController;
 	private ControlServiceImpl controlService;	
 	private MainController mainController;
	private JInternalFrame JInternalFrameValidate, JInternalFrameDefine, JInternalFrameAnalysedGraphics, JInternalFrameDefinedGraphics, JInternalFrameAnalyse;
 	private Logger logger = Logger.getLogger(PluginController.class);;
 	private IProject project;
 	private String projectName = "";
 	private IPath projectPath;
 	
 	private PluginController(){ 
 		URL propertiesFile = getClass().getResource("/husacct/common/resources/husacct.properties");
		PropertyConfigurator.configure(propertiesFile);
		initializeControllers();			initializeFrames();
 	}
 	
 	public static PluginController getInstance(){
 		if(pluginController == null){
 			pluginController = new PluginController(); 
 		}
		return pluginController;
 	}
 	private void initializeControllers(){
 		logger.info("Initializing Controllers");
 		serviceProvider = ServiceProvider.getInstance(); 
 		controlService = (ControlServiceImpl) serviceProvider.getControlService();
 		mainController = controlService.getMainController();
 		stateController = mainController.getStateController();
 	} 	
 	
 	private void initializeFrames(){
 		logger.info("Initializing Frames");
 		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameValidate.setVisible(true);
		
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
		
		JInternalFrameAnalysedGraphics = serviceProvider.getGraphicsService().getAnalysedArchitectureGUI();
		JInternalFrameAnalysedGraphics.setVisible(true);
		
		JInternalFrameDefinedGraphics = serviceProvider.getGraphicsService().getDefinedArchitectureGUI();
		JInternalFrameDefinedGraphics.setVisible(true);
		
		JInternalFrameAnalyse = serviceProvider.getAnalyseService().getJInternalFrame();
		JInternalFrameAnalyse.setVisible(true);		
 	}
 	
 	public StateController getStateController(){
 		return stateController;
 	}
 	
 	public JInternalFrame getDefineFrame(){
 		return JInternalFrameDefine;
 	}
 	
 	public JInternalFrame getValidateFrame(){
 		return JInternalFrameValidate;
 	}
 	
 	public JInternalFrame getGraphicsAnalysedArchitecture(){
 		return JInternalFrameAnalysedGraphics;
 	}
 	
 	public JInternalFrame getGraphicsDefinedArchitecture(){
 		return JInternalFrameDefinedGraphics;
 	}
 	
 	public JInternalFrame getAnalyseFrame(){
 		return JInternalFrameAnalyse;
 	}
 	
 	public void validate(){
 		if(serviceProvider.getDefineService().isMapped()){	
 			Thread validateThread = new Thread(){
 				 public void run() {
 					ServiceProvider.getInstance().getValidateService().checkConformance();			 
 				 }
 			};
 			BusyIndicator.showWhile(null, validateThread);
 			validateThread.run();
 		}
 	}
 	
 	public void projectSelected(IProject project){
 		this.project = project;
 		projectPath = project.getLocation();
		projectName =  project.toString().substring(2);
		WorkspaceController workspaceController = mainController.getWorkspaceController();
		workspaceController.createWorkspace(projectName);		
		stateController.checkState();
		File file = new File(projectPath.makeRelative().toString() + "\\" + projectName + ".hussact");
		logger.debug(file.toString());
 		sourceSelected(projectName ,projectPath.toString(), "1.0");
 	}
 	
 	public IProject getProject(){
 		return project;
 	}
 	
 	public String getProjectName(){
 		return projectName;
 	}
 	
 	public String getProjectPath(){
 		return projectPath.toString();
 	}
	
	public void sourceSelected(String projectName, String sources, String version){
		logger.info("Selecting source");
		serviceProvider.getDefineService().createApplication( projectName, new String[]{sources}, "Java", version);		
		logger.info("Analyzing source");
		Thread analyzeThread = new Thread(){
			 public void run() {
				 ServiceProvider.getInstance().getAnalyseService().analyseApplication();				 
			 }
		};
		BusyIndicator.showWhile(null, analyzeThread);
		analyzeThread.run();
	}
	
	public ArrayList<ViolationDTO> getViolations(){
		ArrayList<ViolationDTO> violationArrayList = new ArrayList<ViolationDTO>();
		ModuleDTO[] moduleList;
		moduleList = serviceProvider.getDefineService().getRootModules();
		
		//Setting all violation per module in a ArrayList
		for(ModuleDTO mdtoFrom : moduleList){
			for(ModuleDTO mdtoTo: moduleList){
				if(mdtoFrom != mdtoTo){
					violationArrayList.addAll(Arrays.asList(serviceProvider.getValidateService().getViolationsByLogicalPath(mdtoFrom.logicalPath, mdtoTo.logicalPath)));
				}
			}
		}
		return violationArrayList;
	}
	
	public void resetPlugin(){
		serviceProvider.resetServices();
		//reset views?
	}	
}
