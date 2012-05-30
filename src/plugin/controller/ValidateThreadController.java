package plugin.controller;

import husacct.ServiceProvider;
import husacct.control.task.MainController;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import plugin.views.internalframes.LoadingDialog;

public class ValidateThreadController {
	private static Logger logger = Logger.getLogger(ValidateThreadController.class);
	private static boolean validating = false;
	
	public static void validate(final JInternalFrame jInternalFrameValidate, MainController mainController){
		logger.debug("validate gestart");
		if(validating == false){
			validating = true;
			if(ServiceProvider.getInstance().getDefineService().isMapped()){
			final LoadingDialog loadingdialog = new LoadingDialog(mainController, "Validating");
			final Thread validateThread = new Thread(){
				 public void run() {
					 ServiceProvider.getInstance().getValidateService().checkConformance();
					 PluginController.getInstance().checkState();
					 PluginController.getInstance().getViolationFrame().initiateViolationTable();
				 }
			};
			
			Thread loadingThread = new Thread(loadingdialog);
	
			Thread monitorThread = new Thread(new Runnable() {
				public void run() {
					try {
						validateThread.join();
						loadingdialog.dispose();
						logger.debug("Monitor: validate finished");
						if(!loadingdialog.isVisible()){
							JOptionPane.showMessageDialog(jInternalFrameValidate, 
							"Validation succeeded!\n" +
							"\n* Open the “Validate” view to see the found violations."+
							"\n* Within this view you can click on a violation to go to the specific fault " +
							"in the source code.", 
							"Succes", JOptionPane.PLAIN_MESSAGE);
						}
					} catch (InterruptedException exception){
						logger.debug("Monitor: analyse interrupted");
						JOptionPane.showMessageDialog(jInternalFrameValidate, "Validation failed", "Error", 
						JOptionPane.ERROR_MESSAGE);
					}
	
				}
			});
			loadingThread.start();
			validateThread.start();
			monitorThread.start();
			}
			else{
				logger.debug("Monitor: Validate not available yet");
				JOptionPane.showMessageDialog(jInternalFrameValidate, "Validate functionality not available yet", 
				"Error", JOptionPane.ERROR_MESSAGE);
			}
			validating = false;
		}
	}
}
