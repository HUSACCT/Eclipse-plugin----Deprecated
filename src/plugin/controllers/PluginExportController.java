package plugin.controllers;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import husacct.control.task.ExportController;

public class PluginExportController {
	private ExportController exportController;
	private static Logger logger = Logger.getLogger(AnalyseThreadController.class);
	
	public PluginExportController(ExportController exportController){
		this.exportController = exportController;
	}

	public void exportArchitecture() {	
        Display.getDefault().asyncExec(new Runnable() {
        	public void run() {
        		Display display = Display.getDefault();
        	    Shell  shell = new Shell(display);
        	    shell.setSize(400, 400);
        	    FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
                fileDialog.setText("Save");
                fileDialog.setFilterPath("C:/");
                String[] filterExt = { "*.xml"};
                fileDialog.setFilterExtensions(filterExt);
        		String selected = fileDialog.open();
        		if(selected != null){
        			exportController.exportArchitecture(new File(selected));
        		}
        	}
        });
	}
	
	public void exportViolations(){
		 Display.getDefault().asyncExec(new Runnable() {
	        	public void run() {
	        		Display display = Display.getDefault();
	        	    Shell  shell = new Shell(display);
	        	    shell.setSize(400, 400);
	        	    FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
	                fileDialog.setText("Save");
	                fileDialog.setFilterPath("C:/");
	                //HTML werkt niet in de plugin String[] filterExt = { "*.xml", "*.html", "*.pdf"};
	                String[] filterExt = { "*.xml", "*.pdf"};
	                fileDialog.setFilterExtensions(filterExt);
	        		String selected = fileDialog.open();
	        		File selectedFile = new File(selected);
	        		if(selected != null){
	        			if(selectedFile.exists()){
	        				logger.debug("File with selected name and path already exists");
	        			}else{
	        				exportController.exportViolationsReport(new File(selected));
	        			}
	        		}
	        	}
	        });
		}
	
	class TypeOfFile extends FileFilter  
	{   
		public boolean accept(File f)  
		{  
			return f.isDirectory()||f.getName().toLowerCase().endsWith(".xml");  
		}   
		public String getDescription()  
		{  
			return ".xml files";  
		}  
	}
}
