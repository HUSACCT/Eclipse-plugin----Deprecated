package plugin.controller;

import husacct.ServiceProvider;
import husacct.control.task.WorkspaceController;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;

public class PluginWorkspaceController {
	private Logger logger = Logger.getLogger(PluginController.class);
	private WorkspaceController workspaceController;
	private File file = new File("");
	
	public PluginWorkspaceController(WorkspaceController workspaceController){
		this.workspaceController = workspaceController;
	}
	
	public void createWorkspace(IProject project, File file){
		logger.debug("creating a new workspace");
		String projectPath = project.getLocation().toString();
		String projectName = project.toString().substring(2);
		this.file = file;
		workspaceController.createWorkspace(projectName);
		ServiceProvider.getInstance().getDefineService().createApplication(projectName, new String[]{projectPath}, "Java", "1.0");
	}
	
 	public void saveProject(){
 		logger.debug("saving workspace");
 		HashMap<String, Object> dataValues = new HashMap<String, Object>();
		dataValues.put("file", file);
		workspaceController.saveWorkspace("xml", dataValues);
 	}
 	
 	public void loadProject(File file){
 		this.file = file;
 		logger.debug("loading workspace");
 		HashMap<String, Object> dataValues = new HashMap<String, Object>();
		dataValues.put("file", file);
		workspaceController.loadWorkspace("xml", dataValues);
 	}
 	
 	public File getFile(){
 		return file;
 	}
}
