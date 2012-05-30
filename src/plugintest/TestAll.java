package plugintest;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import plugintest.controller.PluginControllerTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	PluginControllerTestSuite.class
})
public class TestAll {
	@Before
	public void prepareLog4J(){
		BasicConfigurator.configure();
	}
}