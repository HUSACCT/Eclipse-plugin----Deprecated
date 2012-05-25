package plugin.controller;

import java.io.File;
import java.net.URL;
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
 	private PluginWorkspaceController pluginWorkspaceController;
 	private JInternalHusacctViolationsFrame JInternalViolationsFrame;
 	private Logger logger = Logger.getLogger(PluginController.class);
 	private IProject project;
 	
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
 		pluginWorkspaceController = new PluginWorkspaceController(workspaceController);
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
	
	//This function needs to be taken out as soon as the hussact is far 
	//enough that the individual components notifie the statecontroller. Until then this is used
	public void checkState(){
		stateController.checkState();
	}
	
	public void addToStateController(IStateChangeListener stateChangeListener){
		stateController.addStateChangeListener(stateChangeListener);
		stateController.checkState();
	}
	
	public void addToResetController(IResetListener resetListener){
		viewResetController.addListener(resetListener);
	}
	
	public void validate(){
		ValidateThreadController.validate();
	}
	
	public void analyse(){
		AnalyseThreadController.analyse(FrameInstanceController.getAnalyseFrame(), mainController);
	}
	
	public void saveProject(){
		pluginWorkspaceController.saveProject();
	}
	
	public void projectSelected(IProject project){
		this.project = project;
		File file = new File(project.getLocation().toString() + "\\" + "hussact.hu");
		if(pluginWorkspaceController.getFile().toString().equals(file.toString())){
			logger.debug("reanalysing project");
			analyse();
		}
		else{
			if(workspaceController.isOpenWorkspace()){
				logger.debug("Saving project and resetting plugin");
				pluginWorkspaceController.saveProject();
			}		
			if(file.exists()){
 				//loading is not working in hussact corectly yet. A new project is created even if the project is saved 
				//pluginWorkspaceController.loadProject();
 				pluginWorkspaceController.createWorkspace(project, file);
				resetPlugin();				
			}
 			else{
	 			pluginWorkspaceController.createWorkspace(project, file);
	 			resetPlugin();				
 			}
			analyse();
		}		
	}
	
	private void resetPlugin(){	
		viewResetController.notifyResetListeners();
		stateController.checkState();
	}
}
