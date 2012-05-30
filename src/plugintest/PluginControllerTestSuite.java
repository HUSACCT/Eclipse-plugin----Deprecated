package plugintest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import plugintest.controllers.PluginControllerTest;
import plugintest.controllers.StateControllerTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	PluginControllerTest.class,
	StateControllerTest.class
})

public class PluginControllerTestSuite {

}
