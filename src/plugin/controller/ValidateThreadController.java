package plugin.controller;

import husacct.ServiceProvider;

import org.eclipse.swt.custom.BusyIndicator;

public class ValidateThreadController {
	public static void validate(){
 		if(ServiceProvider.getInstance().getDefineService().isMapped()){	
 			Thread validateThread = new Thread(){
 				 public void run() {
 					ServiceProvider.getInstance().getValidateService().checkConformance();
 					PluginController.getInstance().checkState();
 					PluginController.getInstance().getViolationFrame().initiateViolationTable();
 				 }
 			};
 			BusyIndicator.showWhile(null, validateThread);
 			validateThread.run();
 		}
 	}
}
