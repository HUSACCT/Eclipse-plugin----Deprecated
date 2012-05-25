package plugin.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	private static final String PERSPECTIVE_ID = "HUSACCT_Plugin.HusacctPerspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		return new WorkbenchWindowAdvisor(configurer);
		}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	public boolean preShutdown(){
		System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		String dialogBoxTitle = "Question";
		String question = "Are you sure you want to close this application?";
		return MessageDialog.openQuestion(shell, dialogBoxTitle, question);
		
	}
}

