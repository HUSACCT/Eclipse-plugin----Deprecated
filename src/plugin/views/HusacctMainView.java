package plugin.views;

import husacct.Main;
import husacct.ServiceProvider;
import husacct.control.presentation.MainGui;
import husacct.control.task.MainController;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class HusacctMainView extends ViewPart {	
		ServiceProvider sp = ServiceProvider.getInstance();
	public HusacctMainView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {		
		JInternalFrame jif = sp.getValidateService().getBrowseViolationsGUI();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		BorderLayout bl = new BorderLayout();
		frame.setLayout(bl);
		frame.add(jif.getRootPane(), BorderLayout.CENTER);		
		String[] columnNames = {"Action"}; 
        Object[][] data = {
        {"Open Project"},
        {"Define"},
        {"Analyse"},
        {"Check Conformance"},
        {"Test"}
        }; 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table.setFillsViewportHeight(true);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	int i = table.getSelectedRow();
            	if (i == 1){
            		
            	}            	
            }
        });
        frame.add(table, BorderLayout.LINE_START);
       	parent.setParent(composite);	
	}

	@Override
	public void setFocus() {

	}

}
