package plugin.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.custom.BusyIndicator;
import plugin.views.internalframes.JInternalHusacctViolationsFrame;
import husacct.ServiceProvider;
import husacct.common.dto.ModuleDTO;
import husacct.common.dto.ViolationDTO;
import husacct.control.ControlServiceImpl;
import husacct.control.task.IStateChangeListener;
import husacct.control.task.MainController;
import husacct.control.task.StateController;
import husacct.control.task.WorkspaceController;

public class PluginController {
	private static PluginController pluginController = null;
	
	private ServiceProvider serviceProvider;
	private StateController stateController;
 	private ControlServiceImpl controlService;	
 	private MainController mainController;
 	private WorkspaceController workspaceController;
 	private ViewResetController viewResetController;
	private JInternalFrame JInternalFrameValidate, JInternalFrameDefine, JInternalFrameAnalysedGraphics, JInternalFrameDefinedGraphics, JInternalFrameAnalyse;
 	private JInternalHusacctViolationsFrame JInternalViolationsFrame;
	private Logger logger = Logger.getLogger(PluginController.class);;
 	private IProject project;
 	private File file = new File("");
 	
 	private PluginController(){ 
 		URL propertiesFile = getClass().getResource("/husacct/common/resources/husacct.properties");
		PropertyConfigurator.configure(propertiesFile);
		initializeControllers();	
		initializeFrames();
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
 		workspaceController = mainController.getWorkspaceController();
 		viewResetController = new ViewResetController();
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
 	
	public void checkState() {
		stateController.checkState();
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
 	
 	public IProject getProject(){
 		return project;
 	}
 	
 	public String getProjectName(){
 		return project.toString().substring(2);
 	}
 	
	public void setViolationFrame(JInternalHusacctViolationsFrame violationFrame){
		JInternalViolationsFrame = violationFrame;
	}
	
	public void addToStateController(IStateChangeListener stateChangeListener){
		stateController.addStateChangeListener(stateChangeListener);
	}
	
	public void addToResetController(IResetListener resetListener){
		viewResetController.addListener(resetListener);
	}
 	
 	public void validate(){
 		if(serviceProvider.getDefineService().isMapped()){	
 			Thread validateThread = new Thread(){
 				 public void run() {
 					ServiceProvider.getInstance().getValidateService().checkConformance();
 					PluginController.getInstance().checkState();
 				 }
 			};
 			BusyIndicator.showWhile(null, validateThread);
 			validateThread.run();
 		}
 	}
 	
	public void analyse(){
		Thread analyseThread = new Thread(){
			 public void run() {
				 ServiceProvider.getInstance().getAnalyseService().analyseApplication();
				 PluginController.getInstance().checkState();
			 }
		};
		BusyIndicator.showWhile(null, analyseThread);
		analyseThread.run();
	}
	
	
	public void resetPlugin(){
		serviceProvider.resetServices();
		workspaceController.closeWorkspace();
		initializeFrames();
		viewResetController.notifyResetListeners();
		stateController.checkState();
	}
 	
	//CODE IN DEVELOPMENT
 	public void projectSelected(IProject project){
 		String projectPath = project.getLocation().toString();
		String projectName = project.toString().substring(2);
		this.project = project;
		//if there is no workspace open create a workspace or open a workspace
 		if(!workspaceController.isOpenWorkspace()){ 
 			file = new File(project.getLocation().toString() + "\\" + "hussact.hu");
 			//if file exists open the workspace
 			if(file.exists()){
 				logger.debug("Loading a hussact project for the first time this startup");
				loadProject();
				workspaceController.createWorkspace(projectName);
				serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
			}
 			//if no save file exist create a new project
 			else{
 				logger.debug("Creating a new hussact project");
	 			workspaceController.createWorkspace(projectName);
				serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
 			}
			analyse();
 		}
 		//if there is no a workspace open but it is the same only analyse
 		else if(file.equals(new File(project.getLocation().toString() + "\\" + "hussact.hu"))){
 			logger.debug("The opened workspace is re-analysed");
 			analyse();
 		}
 		//if there is no a workspace open and its different save the last project and reset the plugin
 		else{
 			saveProject();
			resetPlugin();
			file = new File(project.getLocation().toString() + "\\" + "hussact.hu");
			//if the save file exists then open the workspace
			if(file.exists()){
				logger.debug("Loading a other hussact project");
				loadProject();
				workspaceController.createWorkspace(projectName);
				serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
			}
			//if no save file exists then create a new workspace
			else{
				logger.debug("Creating a new hussact project");
				workspaceController.createWorkspace(projectName);
				serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
			}
			analyse();
 		}	
	}
 	
 	private void saveProject(){
 		logger.debug("saving project");
 		if(file != null){
	 		HashMap<String, Object> dataValues = new HashMap<String, Object>();
			dataValues.put("file", file);
			workspaceController.saveWorkspace("xml", dataValues);
 		}
 	}
 	
 	private void loadProject(){
 		logger.debug("loading project");
 		HashMap<String, Object> dataValues = new HashMap<String, Object>();
		dataValues.put("file", file);
		workspaceController.loadWorkspace("xml", dataValues);
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
	
	public Object[][] setDataModel(){
		ArrayList<ViolationDTO> violationArrayList = pluginController.getViolations();
		Object[][] data = new Object[][]{ { "", "", "", "", ""} };
		String serverity = "";
		
		if(violationArrayList.size() > 1){
			data = new Object[violationArrayList.size()][5];
		
			int counter = 0;
			for(ViolationDTO violationDTO : violationArrayList){
				data[counter][0] = violationDTO.fromClasspath;
				data[counter][1] = violationDTO.toClasspath;
				data[counter][2] = "" + violationDTO.linenumber;
				data[counter][3] = violationDTO.violationType.getKey();
				if(violationDTO.severityValue == 0){
					serverity = "Low";
				}else if(violationDTO.severityValue == 1){
					serverity = "Medium";
				}else if(violationDTO.severityValue == 2){
					serverity = "High";
				}
				data[counter][4] = serverity;
				counter++;
			}
		}
		return data;
	}
	
	public void refreshViolationFrame(){
		validate();
		JInternalViolationsFrame.initiateViolationTable();
	}
}
