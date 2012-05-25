package plugin.views;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import plugin.controller.FrameInstanceController;
import husacct.control.task.ImportController;

public class PluginImportController {
	ImportController importController;

	public PluginImportController(ImportController importController) {
		this.importController = importController;
	}

	public void importArchitecture() {
		System.out.println("Import Architecture");
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Import");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showOpenDialog(FrameInstanceController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			importController.importArchitecture(jFileChooser.getSelectedFile());
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
