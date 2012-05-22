package plugin.builder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import plugin.controller.PluginController;

public class ToggleNatureAnalyseProject implements IObjectActionDelegate    {

	private ISelection selection;
	private PluginController plugincontroller= PluginController.getInstance();
	private IProject selectedProject;
	private JDialog dialogFrame;
	
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
			try {
				PlatformUI.getWorkbench().showPerspective("HUSACCT_Plugin.HusacctPerspective",PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			} catch (WorkbenchException e) {
				e.printStackTrace();
			}
			
			if(plugincontroller.getProject() == null){
				plugincontroller.projectSelected(project);
			}else if (!plugincontroller.getProject().equals(project)){
				selectedProject = project;
				dialogFrame = new JDialog();
				JPanel contentPanel = new JPanel();
				dialogFrame.setTitle("Warning");
				dialogFrame.setBounds(100, 100, 300, 162);
				dialogFrame.getContentPane().setLayout(new BorderLayout());
				contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
				dialogFrame.getContentPane().add(contentPanel, BorderLayout.CENTER);
				contentPanel.setLayout(new BorderLayout(0, 0));
				
					JLabel lblNewLabel = new JLabel("<html>You requested a new analyse on a different project. <br>\r\nDo you wish to continue?</html>");
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					contentPanel.add(lblNewLabel, BorderLayout.WEST);
				
				
					JPanel buttonPane = new JPanel();
					dialogFrame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
					buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
					
						JButton okButton = new JButton("Yes");
						buttonPane.add(okButton);
						dialogFrame.getRootPane().setDefaultButton(okButton);
					
						JButton cancelButton = new JButton("No");
						buttonPane.add(cancelButton);
					
				
				
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						plugincontroller.resetPlugin();
						plugincontroller.projectSelected(selectedProject);
						dialogFrame.setVisible(false);
					}
				});
				cancelButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						dialogFrame.setVisible(false);
					}
				});
				
				dialogFrame.setVisible(true);
				
			}else{
				plugincontroller.projectSelected(project);
			}
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
