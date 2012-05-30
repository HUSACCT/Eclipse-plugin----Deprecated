package plugin.builders;

import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import plugin.controllers.PluginController;


public class ToggleNatureValidateProject implements IObjectActionDelegate    {
	private ISelection selection;
	private PluginController pluginController = PluginController.getInstance();
	
	
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
		pluginController.validate();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		if(pluginController.isMapped()){
			action.setEnabled(true);
		}else{
			action.setEnabled(false);
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
