package plugintest.tests;

import static org.junit.Assert.*;
import husacct.ServiceProvider;
import husacct.analyse.IAnalyseService;
import husacct.control.ControlServiceImpl;
import husacct.control.task.MainController;
import husacct.define.IDefineService;
import husacct.graphics.IGraphicsService;
import husacct.validate.IValidateService;

import org.junit.Before;
import org.junit.Test;

import plugin.controller.PluginController;



public class PluginControllerTest {
	ServiceProvider serviceProvider;
	PluginController pluginController;
	MainController mainController;
	ControlServiceImpl controlService;
	@Before
	public void prepareServices(){
		serviceProvider = ServiceProvider.getInstance();
		pluginController = PluginController.getInstance();
		controlService = (ControlServiceImpl) serviceProvider.getControlService();
 		mainController = controlService.getMainController();
	}
	
	@Test 
	public void testGetStateController(){
		//assertEquals(pluginController.getStateController(), mainController.getStateController());
	}
	
	@Test
	public void testGetDefineFrame(){
		assertEquals(pluginController.getDefineFrame(), serviceProvider.getDefineService().getDefinedGUI());
	}
	
	@Test
	public void testGetValidateFrame(){
		assertEquals(pluginController.getValidateFrame(), serviceProvider.getValidateService().getBrowseViolationsGUI());
	}
	
	@Test
	public void testGetGraphicsAnalysedArchitecture(){
		assertEquals(pluginController.getGraphicsAnalysedArchitecture(), serviceProvider.getGraphicsService().getAnalysedArchitectureGUI());
	}
	
	@Test
	public void testGetGraphicsDefinedArchitecture(){
		assertEquals(pluginController.getGraphicsDefinedArchitecture(), serviceProvider.getGraphicsService().getDefinedArchitectureGUI());
	}
	
	@Test
	public void testGetAnalyseFrame(){
		assertEquals(pluginController.getAnalyseFrame(), serviceProvider.getAnalyseService().getJInternalFrame());
	}
	
	

}
