package plugin.builder;

import java.util.Iterator;
import javax.swing.JFileChooser;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ToggleNatureImportArchitecture implements IObjectActionDelegate    {
	private ISelection selection;
	private PluginController pluginController= PluginController.getInstance();

	public void run(IAction action) {		
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null) {
					toggleNature(project);
				}
			}
		}
	}

	private void toggleNature(IProject project) {
		System.out.println("Import Architecture");
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Import");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showOpenDialog(FrameInstanceController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			//pluginController.importLogicalArchitecture(jFileChooser.getSelectedFile());
		}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
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
