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
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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

		String[] columnNames = {"From", "To", "Line number", "Dependency type"};

		violationArrayList = pluginController.getViolations();
		for(ViolationDTO dto: violationArrayList){
			System.out.println(dto.logicalModuleFrom);
		}

		//Onderstaande array moet gevuld worden met violations. Is momenteel nog met testdata gevuld.
		Object[][] data = { {"views.Display", "models.Log", "8", "Import"}, {"views.Display", "models.Storage", "19", "Import"} };

		violationTable = new JTable(data, columnNames);
		violationTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Onderstaande = line number van geselecteerde violation 
				int lineNumber = Integer.parseInt((String) violationTable.getValueAt(violationTable.getSelectedRow(), 2));  

				//Onderstaande is package+classname gescheiden met een punt van geselecteerde violation
				String readedPackageAndClassName = violationTable.getValueAt(violationTable.getSelectedRow(), 0).toString();  

				/*Uitgelezen package + classname worden uigelezen met een punt ertussen.
				  Voor het juist doorgeven moeten deze punten vervangen worden door slashes.
				  Dit is in onderstaande String gebeurd
				 */
				String formattedPackageAndClassName = readedPackageAndClassName.replace('.' , '/'); 

				//Onderstaande = string van project die geanalyseerd is				
				String projectPath = pluginController.getProjectPath();

				//Onderstaande = volledige pad van de klasse waar de violation in zit.
				String entireClassPath = (projectPath + "/src/" + formattedPackageAndClassName);

				System.out.println(entireClassPath);  // deze is voor de test - mag binnenkort weg! Wanneer er geen project geanalyseerd is zal de output foutief zijn.

				Path path = new Path("Border/src/BorderLayoutDemo.java"); //de huidige test situatie - pad moet een project zijn in je huidige (test) workspace
				//Path path = new Path(entireClassPath); //deze moet gebruikt gaan worden als violations opgehaald kunnen worden van de validate service
				IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);

				openViolationWithEditor(iFile,lineNumber);	

			}
		});

		frame.add(new JScrollPane(violationTable), BorderLayout.CENTER);
		createButton(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}

	private void openViolationWithEditor(IFile file, int lineNumber) {
		final IFile iFile = file;
		final int finalLineNumber = lineNumber;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				IWorkbenchWindow workbenchwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = workbenchwindow.getActivePage();

				if (page != null) {
					try {
						IDE.openEditor(page, iFile, true);
						HashMap hashMap = new HashMap ();
						hashMap.put(IMarker.LINE_NUMBER, new Integer(finalLineNumber));
						hashMap.put(IDE.EDITOR_ID_ATTR, "org.eclipse.jdt.internal.ui.javaeditor.JavaEditor");
						try {
							IMarker marker = iFile.createMarker(IMarker.TEXT);
							marker.setAttributes(hashMap);
							IDE.openEditor(page, marker, true);
							marker.delete();
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (CoreException e) {
							e.printStackTrace();
						} 
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
