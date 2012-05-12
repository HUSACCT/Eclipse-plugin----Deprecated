package plugin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.jdom2.Document;
import org.jdom2.Element;
import plugin.Activator;
import husacct.Main;
import husacct.ServiceProvider;
import husacct.common.dto.ModuleDTO;
import husacct.common.dto.ViolationDTO;
import husacct.control.task.resources.IResource;
import husacct.control.task.resources.ResourceFactory;

public class PluginController {
	private ServiceProvider serviceProvider;
	private JInternalFrame JInternalFrameValidate, JInternalFrameDefine, JInternalFrameAnalysedGraphics, JInternalFrameDefinedGraphics;
 	private Logger logger;
 	private static PluginController pluginController = null;
 	private IProject project;
 	private String projectName;
 	
 	private PluginController(){
 		initializeLogger();
 		logger.info("Starting ServiceProvider");
 		serviceProvider = ServiceProvider.getInstance();
 		logger.info("Initialize Frames");
 		initializeFrames();
 	}
 	
 	public static PluginController getInstance(){
 		if(pluginController == null){
 			pluginController = new PluginController(); 
 		}
		return pluginController;
 	}
 	
 	private void initializeLogger(){
 		try {
			String loggerFile = FileLocator.toFileURL(Activator.getDefault().getBundle().getEntry("husacct.properties")).getPath();
			PropertyConfigurator.configure(loggerFile);
			logger = Logger.getLogger(Main.class);
			logger.info("Starting HUSACCT");
		} catch (Exception e) {
			e.printStackTrace();
		} 		
 	} 	
 	
 	private void initializeFrames(){
 		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameValidate.setVisible(true);
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
		JInternalFrameAnalysedGraphics = serviceProvider.getGraphicsService().getAnalysedArchitectureGUI();
		JInternalFrameAnalysedGraphics.setVisible(true);
		JInternalFrameDefinedGraphics = serviceProvider.getGraphicsService().getDefinedArchitectureGUI();
		JInternalFrameDefinedGraphics.setVisible(true);
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
 	
 	public void validate(){
 		if(serviceProvider.getDefineService().isMapped()){
 			serviceProvider.getValidateService().checkConformance();
 		}
 	}
 	
 	public void projectSelected(IProject project){
 		this.project = project;
 		IPath projectPath = project.getLocation();
 		sourceSelected("eclipse plugin project" ,projectPath.toString(), "1.0");
 		logger.info(projectPath.makeRelative().toString());
 	}
 	
 	public void setProjectName(IProject project){
		IProject iProjectProjectName = project.getProject();
		String projectNameString = iProjectProjectName.toString(); 	
		String subString = projectNameString.substring(2);
		this.projectName = subString;
 	}
 	
 	public String getProjectName(){
 		return projectName;
 	}
	
	public void sourceSelected(String projectName, String sources, String version){
		logger.info("Selecting source");
		serviceProvider.getDefineService().createApplication( "Eclipseplugin", new String[]{sources}, "Java", version);		
		logger.info("Analyzing source");
		serviceProvider.getAnalyseService().analyseApplication();
	}
	
	public void importLogicalArchitecture(File file){		
		logger.info("importing architecture");
		HashMap<String, Object> resourceData = new HashMap<String, Object>();
		resourceData.put("file", file);
		IResource xmlResource = ResourceFactory.get("xml");
		try {
			Document doc = xmlResource.load(resourceData);	
			Element logicalData = doc.getRootElement();
			System.out.println(logicalData);
			serviceProvider.getDefineService().loadLogicalArchitectureData(logicalData);
		} catch (Exception e) {
			logger.debug("Unable to import logical architecture: " + e.getMessage());
		}
	}
	
	public void exportLogicalArchitecture(File file){
		logger.info("exporting architecture");
		HashMap<String, Object> resourceData = new HashMap<String, Object>();
		resourceData.put("file", file);
		IResource xmlResource = ResourceFactory.get("xml");
		try {
			Element logicalData = serviceProvider.getDefineService().getLogicalArchitectureData();
			Document doc = new Document(logicalData);
			xmlResource.save(doc, resourceData);
		} catch (Exception e) {
			logger.debug("Unable to export logical architecture: " + e.getMessage());
		}
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
}
