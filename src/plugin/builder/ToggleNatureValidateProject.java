package plugin.builder;

//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.Iterator;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
//import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.WorkbenchException;
//import plugin.controller.PluginController;

public class ToggleNatureValidateProject implements IObjectActionDelegate    {

	private ISelection selection;
//	private PluginController plugincontroller= PluginController.getInstance();
//	private IProject selectedProject;
//	private JFrame dialogFrame;
	
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
		System.out.println("Validate project");
//			try {
//				PlatformUI.getWorkbench().showPerspective("HUSACCT_Plugin.HusacctPerspective",PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//			} catch (WorkbenchException e) {
//				e.printStackTrace();
//			}
//			
//			if(plugincontroller.getProject() == null){
//				plugincontroller.projectSelected(project);
//			}else if (!plugincontroller.getProject().equals(project)){
//				selectedProject = project;
//				dialogFrame = new JFrame("Project changed");
//				JButton confirmbtn = new JButton("Yes");
//				confirmbtn.addActionListener(new ActionListener(){
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						plugincontroller.resetPlugin();
//						plugincontroller.projectSelected(selectedProject);
//						dialogFrame.setVisible(false);
//					}
//				});
//				JButton declinebtn = new JButton("No");
//				declinebtn.addActionListener(new ActionListener(){
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						dialogFrame.setVisible(false);
//					}
//				});
//				JLabel textlbl = new JLabel("You requested a new analyse on a different project. \n Do you wish to continue?");
//				
//				dialogFrame.getContentPane().add(textlbl, BorderLayout.NORTH);
//				dialogFrame.getContentPane().add(confirmbtn, BorderLayout.SOUTH);
//				dialogFrame.getContentPane().add(declinebtn, BorderLayout.LINE_END);
//				dialogFrame.pack();
//				dialogFrame.setVisible(true);
//				
//			}else{
//				//Nothing
//			}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
