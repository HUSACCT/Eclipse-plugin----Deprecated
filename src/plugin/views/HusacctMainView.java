package plugin.views;

import husacct.Main;
import husacct.ServiceProvider;

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
		JInternalFrame jif;
		Frame frame;
		ServiceProvider sp = ServiceProvider.getInstance();
	public HusacctMainView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {	
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		
		jif = sp.getValidateService().getBrowseViolationsGUI();
		frame = SWT_AWT.new_Frame(composite);
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
               // printDebugData(table);
            	int i = table.getSelectedRow();
            	if (i == 1){
            		System.out.println("JAAA");
            		JInternalFrame jif2 = new JInternalFrame();
            		jif2 = sp.getValidateService().getBrowseViolationsGUI();
            		frame.add(jif2.getRootPane(), BorderLayout.CENTER);
            	}            	
            }
        });
        frame.add(table, BorderLayout.LINE_START);
       	parent.setParent(composite);		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
