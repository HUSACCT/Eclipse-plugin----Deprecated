package plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;


public class OpenHusacctPerspectiveActionDelegate implements IWorkbenchWindowActionDelegate {
	
		public void init(IWorkbenchWindow window) {
			
        }
		
        public void dispose() {
        	
        }

        public void run(IAction action) {
        	try {
				PlatformUI.getWorkbench().showPerspective("HUSACCT_Plugin.HusacctPerspective",PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			} catch (WorkbenchException e) {
				e.printStackTrace();
			} 
        }
        
        public void selectionChanged(IAction action, ISelection selection) {
        	
        }
        
}