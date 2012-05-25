package plugin.controller;

import husacct.ServiceProvider;
import husacct.control.task.MainController;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import plugin.views.internalframes.LoadingDialog;

public class AnalyseThreadController {
	private static Logger logger = Logger.getLogger(AnalyseThreadController.class);
	
	public static void analyse(final JInternalFrame jInternalFrameAnalyse, MainController mainController){
		
		final LoadingDialog loadingdialog = new LoadingDialog(mainController, "analysing");
		final Thread analyseThread = new Thread(){
			 public void run() {
				 ServiceProvider.getInstance().getAnalyseService().analyseApplication();
				 PluginController.getInstance().checkState();
			 }
		};
		
		Thread loadingThread = new Thread(loadingdialog);

		Thread monitorThread = new Thread(new Runnable() {
			public void run() {
				try {
					analyseThread.join();
					loadingdialog.dispose();
					logger.debug("Monitor: analyse finished");
					if(!loadingdialog.isVisible()){
						JOptionPane.showMessageDialog(jInternalFrameAnalyse, "Analysation succeeded, open the define-view for mapping your architecture", "Succes", JOptionPane.PLAIN_MESSAGE);
					}
				} catch (InterruptedException exception){
					logger.debug("Monitor: analyse interrupted");
					JOptionPane.showMessageDialog(jInternalFrameAnalyse, "Analysation failed", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		loadingThread.start();
		analyseThread.start();
		monitorThread.start();
	}
}
