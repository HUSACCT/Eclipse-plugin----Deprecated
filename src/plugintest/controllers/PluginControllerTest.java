package plugintest.controllers;

import static org.junit.Assert.*;
import husacct.ServiceProvider;
import husacct.control.ControlServiceImpl;
import husacct.control.task.MainController;
import org.junit.Before;
import org.junit.Test;

import plugin.controllers.FrameInstanceController;
import plugin.controllers.PluginController;

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
		//assertEquals(pluginController.getStateController(), 
		//mainController.getStateController());
	}
	
	@Test
	public void testGetAnalyseFrame(){
		assertEquals(FrameInstanceController.getAnalyseFrame(), 
				serviceProvider.getAnalyseService().getJInternalFrame());
	}
	
	@Test
	public void testGetDefineFrame(){
		assertEquals(FrameInstanceController.getDefineFrame(), 
				serviceProvider.getDefineService().getDefinedGUI());
	}
	
	@Test
	public void testGetValidateFrame(){
		assertEquals(FrameInstanceController.getValidateFrame(), 
				serviceProvider.getValidateService().getBrowseViolationsGUI());
	}
	
	@Test
	public void testGetGraphicsAnalysedArchitecture(){
		assertEquals(FrameInstanceController.getGraphicsAnalysedArchitecture(), 
				serviceProvider.getGraphicsService().getAnalysedArchitectureGUI());
	}
	
	@Test
	public void testGetGraphicsDefinedArchitecture(){
		assertEquals(FrameInstanceController.getGraphicsDefinedArchitecture(), 
				serviceProvider.getGraphicsService().getDefinedArchitectureGUI());
	}
}
