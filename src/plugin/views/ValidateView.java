package plugin.views;

import husacct.common.dto.ViolationDTO;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
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
			      	System.out.println("(" + violationTable.getValueAt(violationTable.getSelectedRow(), 0) + ".java:" +violationTable.getValueAt(violationTable.getSelectedRow(), 2)+")");
						//PlatformUI.getWorkbench().getBrowserSupport().createBrowser("1").openURL(new URL("(" + violationTable.getValueAt(violationTable.getSelectedRow(), 0) + ".java:" +violationTable.getValueAt(violationTable.getSelectedRow(), 2)+")"));
			  }
			});

		frame.add(new JScrollPane(violationTable), BorderLayout.CENTER);
		createButton(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void createButton(Frame frame){
		Panel panel = new Panel();	
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
