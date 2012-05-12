package plugin.views;

import husacct.common.dto.ViolationDTO;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.PluginController;

public class ValidateView extends ViewPart {
	private PluginController pluginController;
	private ArrayList<ViolationDTO> violationArrayList = new ArrayList<ViolationDTO>();
	private JTable violationTable;
	
	public ValidateView() {
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		
		String[] columnNames = {"From",
                "To",
                "Line number",
                "Dependency type"};
		
		violationArrayList = pluginController.getViolations();
		for(ViolationDTO dto: violationArrayList){
			System.out.println(dto.logicalModuleFrom);
		}
		
		Object[][] data = { {"views.Display", "models.Log", "8", "Import"}, {"views.Display", "models.Storage", "9", "Import"} };
		
		violationTable = new JTable(data, columnNames);
		
		violationTable.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			      //System.out.println("(" + violationTable.getValueAt(violationTable.getSelectedRow(), 0) + ".java:" +violationTable.getValueAt(violationTable.getSelectedRow(), 2)+")");
				  
				  Path path = new Path("RichRail\\src\\views\\Display.java");
				  IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			      openEditor(file);	
			  }
			});

		frame.add(new JScrollPane(violationTable), BorderLayout.CENTER);
		createButton(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void openEditor(IFile file) {
		final IFile aFile = file;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		
			public void run() {
				IWorkbenchWindow dwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = dwindow.getActivePage();
				
				if (page != null) {
					try {
						IDE.openEditor(page, aFile, true);
					}catch (PartInitException pie) {
						System.out.println("Unable to open the Editor");
					}
				}
			}
			});
		}
	
	private void createButton(Frame frame){
		Panel panel = new Panel();	
		panel.setBackground(Color.LIGHT_GRAY);
		Button buttonValidate = new Button("Validate");
		buttonValidate.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	pluginController.validate();
	        }
	    }); 	
		panel.add(buttonValidate);
		frame.add(panel, BorderLayout.PAGE_START);
	}

	@Override
	public void setFocus() {
	}

}
