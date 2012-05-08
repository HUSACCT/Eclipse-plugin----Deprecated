package plugin.controller;

import java.io.File;
import java.util.HashMap;

import javax.swing.JInternalFrame;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.jdom2.Document;
import org.jdom2.Element;

import plugin.Activator;
import plugin.views.HusacctMainView;
import plugin.views.internalframes.JInternalHusacctImportArchitecture;
import plugin.views.internalframes.JInternalHusacctSelectSource;
import husacct.Main;
import husacct.ServiceProvider;
import husacct.control.task.StateController;
import husacct.control.task.resources.IResource;
import husacct.control.task.resources.ResourceFactory;

public class PluginController {
	private HusacctMainView hussactMainView;
	private ServiceProvider serviceProvider;
	private StateController stateController;
	private JInternalFrame JInternalFrameValidate, JInternalFrameDefine, JInternalSelectSource, JInternalImportArchitecture;
 	private String currentFrame = "";
 	private Logger logger;
 	
 	public PluginController(HusacctMainView hussactMainView){
 		this.hussactMainView = hussactMainView;
 		initializeLogger();
 		logger.info("Starting ServiceProvider");
 		serviceProvider = ServiceProvider.getInstance();
 		logger.info("Starting StateController");
 		stateController = new StateController();
 		logger.info("InitializeFrames");
 		initializeFrames();
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
			serviceProvider.getValidateService().checkConformance();
			if(!currentFrame.equals("validate")){
				hussactMainView.changeScreen(JInternalFrameValidate);
				currentFrame = "validate";
			}
		}
	}
	
	public void sourceSelected(String[] sources, String version){
		logger.info("Selecting source");
		serviceProvider.getDefineService().createApplication( "Eclipseplugin", sources, "Java", version);		
		logger.info("Analyzing source");
		serviceProvider.getAnalyseService().analyseApplication();
	}
	
	public void importLogicalArchitecture(File file){
		HashMap<String, Object> resourceData = new HashMap<String, Object>();
		resourceData.put("file", file);
		IResource xmlResource = ResourceFactory.get("xml");
		try {
			Document doc = xmlResource.load(resourceData);	
			Element logicalData = doc.getRootElement();
			System.out.println(logicalData);
			ServiceProvider.getInstance().getDefineService().loadLogicalArchitectureData(logicalData);
		} catch (Exception e) {
			logger.debug("Unable to import logical architecture: " + e.getMessage());
		}
	}
}
