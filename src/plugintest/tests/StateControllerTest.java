package plugintest.tests;

import static org.junit.Assert.assertSame;
import husacct.ServiceProvider;
import husacct.analyse.IAnalyseService;
import husacct.define.IDefineService;
import husacct.graphics.IGraphicsService;
import husacct.validate.IValidateService;

import org.junit.Before;
import org.junit.Test;



public class StateControllerTest {


	
	@Before
	public void prepareServices(){
		ServiceProvider provider = ServiceProvider.getInstance();
	}
	
	@Test 
	public void testServiceProvider(){
		
	}
	
}
