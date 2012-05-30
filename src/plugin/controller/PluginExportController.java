package plugin.controller;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import husacct.control.task.ExportController;

public class PluginExportController {
	private ExportController exportController;
	
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
