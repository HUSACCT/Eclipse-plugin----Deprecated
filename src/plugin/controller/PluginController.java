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
import plugin.views.internalframes.JInternalHusacctExportArchitecture;
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
	private JInternalFrame JInternalFrameValidate, JInternalFrameDefine, JInternalSelectSource, JInternalImportArchitecture, JInternalExportArchitecture;
 	private String currentFrame = "";
 	private Logger logger;
 	
 	public PluginController(HusacctMainView hussactMainView){
 		this.hussactMainView = hussactMainView;
 		initializeLogger();
 		logger.info("Starting ServiceProvider");
 		serviceProvider = ServiceProvider.getInstance();
 		logger.info("Starting StateController");
 		stateController = new StateController();
 		logger.info("Initialize Frames");
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
		JInternalExportArchitecture = new JInternalHusacctExportArchitecture(this);
 	}
 	
	public void showSelectSourceFrame(){
		if(!currentFrame.equals("selectSource")){
			logger.info("changing screeen to SelectSource");
			hussactMainView.changeScreen(JInternalSelectSource);
			currentFrame = "selectSource";
		}
	}
 	
	public void showDefineFrame(){
		if(!currentFrame.equals("define")){
			logger.info("changing screeen to Define");
			hussactMainView.changeScreen(JInternalFrameDefine);
			currentFrame = "define";
		}
	}
	
	public void showImportArchitectureFrame(){
		if(!currentFrame.equals("importArchitecture")){
			logger.info("changing screen to ImportArchitecture");
			hussactMainView.changeScreen(JInternalImportArchitecture);
			currentFrame = "importArchitecture";
		}
	}
	
	public void showExportArchitectureFrame() {
		if(!currentFrame.equals("exportArchitecture")){
			logger.info("changing screen to ExportArchitecture");
			hussactMainView.changeScreen(JInternalExportArchitecture);
			currentFrame = "exportArchitecture";
		}
		
	}
	
	public void showValidateFrame(){
		stateController.checkState();
		logger.info("checking the state. State = " + stateController.getState());
		if(stateController.getState() >= 4){
			logger.info("starting the conformance check");
			serviceProvider.getValidateService().checkConformance();
			if(!currentFrame.equals("validate")){
				logger.info("chainging screen to Validate");
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

	
}
