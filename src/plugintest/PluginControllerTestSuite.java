package plugintest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import plugintest.tests.PluginControllerTest;
import plugintest.tests.StateControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	PluginControllerTest.class,
	StateControllerTest.class
})
public class PluginControllerTestSuite {

}
