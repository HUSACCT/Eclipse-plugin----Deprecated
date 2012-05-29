package plugin.controller;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import husacct.control.task.ExportController;

public class PluginExportController {
	private ExportController exportController;
	
	public PluginExportController(ExportController exportController){
		this.exportController = exportController;
	}

	public void exportArchitecture() {
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Export");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showSaveDialog(FrameInstanceController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			exportController.exportArchitecture(jFileChooser.getSelectedFile());
		}		
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
