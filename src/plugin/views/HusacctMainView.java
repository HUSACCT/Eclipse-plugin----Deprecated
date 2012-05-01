package plugin.views;


import husacct.ServiceProvider;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.ActionDelegate;


public class HusacctMainView extends ViewPart {	
	
	 ActionDelegate actionDelegate;
	 JInternalFrame JInternalFrameValidate;
	 JInternalFrame JInternalFrameDefine;
	 JInternalFrame JInternalHusacctMainScreen = new JInternalFrame();
	 Frame frame;
	 ServiceProvider serviceProvider = ServiceProvider.getInstance();

	
	public HusacctMainView() {
	}

	@Override
	public void createPartControl(Composite parent) {	

		Composite composite = new Composite(parent, SWT.EMBEDDED);		
		JInternalFrameValidate = serviceProvider.getValidateService().getBrowseViolationsGUI();
		JInternalFrameDefine = serviceProvider.getDefineService().getDefinedGUI();
		
		frame = SWT_AWT.new_Frame(composite);
		BorderLayout borderLayout = new BorderLayout();
		frame.setLayout(borderLayout);
		frame.add(JInternalFrameValidate.getRootPane(), BorderLayout.CENTER);		
		
		//frame.add(JInternalHusacctMainScreen.getRootPane(), BorderLayout.CENTER);	
		
		String[] columnNames = {"Actions"}; 
		Object[][] data = {
				{"Select Source"},
				{"Define Architecture"},
				{"Import Architecture"},
				{"Validate"},
		}; 

		final JTable jTable = new JTable(data, columnNames);
		jTable.setSize(500,500);
		jTable.setFillsViewportHeight(true);
		jTable.addMouseListener(new MouseAdapter() {

			
			public void mouseClicked(MouseEvent e) {
				int column = jTable.getSelectedRow();

				if (column == 0){ //Select source
					//actionDelegate.run();
//					actionDelegate.test();
					Repaint();

				}
				else if(column == 1){//Define Architecture
					frame.add(JInternalFrameDefine.getRootPane(), BorderLayout.CENTER);
					Repaint();

				}
				else if(column == 2){//Import Architecture   
					//frame.add(jif1.getRootPane(), BorderLayout.CENTER);
					Repaint();
				}
				else if(column == 3){//Validate  
					frame.add(JInternalFrameValidate.getRootPane(), BorderLayout.CENTER);
					Repaint();
				}
			}
		});
		frame.add(jTable, BorderLayout.LINE_START);
		parent.setParent(composite);	
	}

	@Override
	public void setFocus() {}
	
	public void Repaint(){
		frame.validate();
		frame.repaint();
	}

}
