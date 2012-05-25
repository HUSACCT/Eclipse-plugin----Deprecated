package plugin.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.custom.BusyIndicator;
import plugin.views.internalframes.JInternalHusacctViolationsFrame;
import plugin.views.internalframes.LoadingDialog;
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
	private LoadingDialog loadingdialog;
 	private Logger logger = Logger.getLogger(PluginController.class);
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
 		serviceProvider = ServiceProvider.getInstance();
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
		loadingdialog = new LoadingDialog(mainController, "analysing");
		final Thread analyseThread = new Thread(){
			 public void run() {
				 ServiceProvider.getInstance().getAnalyseService().analyseApplication();
				 PluginController.getInstance().checkState();
			 }
		};
		Thread loadingThread = new Thread(loadingdialog);

		Thread monitorThread = new Thread(new Runnable() {
			public void run() {
				try {
					analyseThread.join();
					loadingdialog.dispose();
					logger.debug("Monitor: analyse finished");
					JOptionPane.showMessageDialog(JInternalFrameAnalyse, "Analysation succeeded, open the define-view for mapping your architecture", "Succes", JOptionPane.PLAIN_MESSAGE);
				} catch (InterruptedException exception){
					logger.debug("Monitor: analyse interrupted");
					JOptionPane.showMessageDialog(JInternalFrameAnalyse, "Validation failed", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		loadingThread.start();
		analyseThread.start();
		monitorThread.start();
	}
	
	public void resetPlugin(){	
		initializeFrames();
		viewResetController.notifyResetListeners();
		stateController.checkState();
	}
	
	public void projectSelected(IProject project){
		String projectPath = project.getLocation().toString();
		String projectName = project.toString().substring(2);
		this.project = project;
		if(file.equals(new File(project.getLocation().toString() + "\\" + "hussact.hu"))){
			logger.debug("reanalysing project");
			analyse();
		}
		else{
			if(workspaceController.isOpenWorkspace()){
				logger.debug("Saving project and resetting plugin");
				saveProject();
			}		
			file = new File(project.getLocation().toString() + "\\" + "hussact.hu");
			if(file.exists()){
 				logger.debug("Loading a hussact project for the first time this startup");
 				//loading is not working in hussact corectly yet. A new project is created even if the project is saved 
				//loadProject();
				//ServiceProvider.getInstance().resetServices();
				workspaceController.createWorkspace(projectName);
				serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
				resetPlugin();				
			}
 			else{
 				logger.debug("Creating a new hussact project");
	 			workspaceController.createWorkspace(projectName);
	 			serviceProvider.getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
	 			resetPlugin();				
 			}
			analyse();
		}		
	}
	
 	public void saveProject(){
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
