package plugin.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IProject;
import plugin.controller.resources.IResetListener;
import plugin.views.internalframes.JInternalHusacctViolationsFrame;
import husacct.ServiceProvider;
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
 	private JInternalHusacctViolationsFrame JInternalViolationsFrame;
 	private Logger logger = Logger.getLogger(PluginController.class);
 	private IProject project;
 	private File file = new File("");
 	
 	private PluginController(){ 
 		URL propertiesFile = getClass().getResource("/husacct/common/resources/husacct.properties");
		PropertyConfigurator.configure(propertiesFile);
		initializeControllers();	
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
 	
 	public IProject getProject(){
 		return project;
 	}
 	
 	public String getProjectName(){
 		return project.toString().substring(2);
 	}
 	
	public void setViolationFrame(JInternalHusacctViolationsFrame violationFrame){
		JInternalViolationsFrame = violationFrame;
	}
	
	public JInternalHusacctViolationsFrame getViolationFrame(){
		if(JInternalViolationsFrame == null){
			return new JInternalHusacctViolationsFrame();
		}
		return JInternalViolationsFrame;
	}
	
	public void checkState() {
		stateController.checkState();
	}
	
	public void addToStateController(IStateChangeListener stateChangeListener){
		stateController.addStateChangeListener(stateChangeListener);
	}
	
	public void addToResetController(IResetListener resetListener){
		viewResetController.addListener(resetListener);
	}
	
	public void resetPlugin(){	
		viewResetController.notifyResetListeners();
		stateController.checkState();
	}
	
	public void validate(){
		ValidateThreadController.validate();
	}
	
	public void analyse(){
		AnalyseThreadController.analyse(FrameInstanceController.getAnalyseFrame(), mainController);
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
}
