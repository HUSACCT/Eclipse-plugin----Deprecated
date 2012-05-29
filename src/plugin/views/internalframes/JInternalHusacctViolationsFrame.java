package plugin.views.internalframes;

import husacct.common.dto.ViolationDTO;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import plugin.controller.PluginController;
import plugin.controller.ViolationsViewController;


public class JInternalHusacctViolationsFrame extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private PluginController pluginController = PluginController.getInstance();
	private ArrayList<ViolationDTO> violationArrayList = new ArrayList<ViolationDTO>();
	private JTable violationTable;
	private JTextPane violationInformation;

	public JInternalHusacctViolationsFrame() {
		setBounds(50, 50, 200, 400);
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Violations");
		setVisible(true);
		initiateViolationTable();
		getContentPane().add(new JScrollPane(violationTable), BorderLayout.CENTER);
		createButton(this);
		getContentPane().validate();
		getContentPane().repaint();
		pluginController.setViolationFrame(this);
	}
	
	public void initiateViolationTable(){
		String[] columnNames = {"Source", "Target", "LineNumber", "Kind of dependency", "Severity"};
		if(pluginController != null){
			violationArrayList = ViolationsViewController.getViolations();
		}
		Object[][] data = ViolationsViewController.setDataModel();
		
		if(violationTable == null){
		violationTable = new JTable(data, columnNames);
		}else{
			TableModel dataModel = new MyTableModel();
			((MyTableModel) dataModel).setNewArray(data);
			violationTable.setModel(dataModel);
			
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
			violationTable.setRowSorter(sorter);
			
			violationInformation.setText("Er zijn " + violationArrayList.size() + " violations gevonden.");
			violationTable.repaint();
		}
		violationTable.repaint();
		
		violationTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("uitgevoerd" + e.toString());
				if(violationTable.getValueAt(violationTable.getSelectedRow(), 2).toString() != ""){
					int lineNumber = Integer.parseInt(violationTable.getValueAt(violationTable.getSelectedRow(), 2).toString());  
					String readedPackageAndClassName = violationTable.getValueAt(violationTable.getSelectedRow(), 0).toString();  
					String formattedPackageAndClassName = readedPackageAndClassName.replace('.' , '/'); 
					String projectName = pluginController.getProjectName();
					String entireClassPath = (projectName + "/src/" + formattedPackageAndClassName)+ ".java";
					
					Path path = new Path(entireClassPath); 
					IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
					if(iFile.exists()){
						ViolationsViewController.openViolationWithEditor(iFile,lineNumber);
					}
//					else{
//						Display.getDefault().asyncExec(new Runnable() {
//				        	public void run() {
//								Display display = Display.getDefault();
//				        	    Shell  shell = new Shell(display);
//								final Shell dialog = new Shell(shell, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
//								dialog.setText("File does not exist");
//								dialog.setSize(250, 150);
//								dialog.open();
//				        	}
//						});
//					}
				}
			}
		});
		
	}

	private void createButton(JInternalHusacctViolationsFrame frame){
		Panel panel = new Panel();	
		panel.setBackground(Color.LIGHT_GRAY);
		Button buttonValidate = new Button("Validate");
		violationInformation = new JTextPane();
		violationInformation.setText("Er zijn 0 violations gevonden");
		violationInformation.setBackground(Color.LIGHT_GRAY);
		
		buttonValidate.addActionListener(new ActionListener() {		 
			public void actionPerformed(ActionEvent e)
			{
				pluginController.validate();
				initiateViolationTable();
			}
		}); 
		panel.add(buttonValidate);
		panel.add(violationInformation);
		frame.add(panel, BorderLayout.PAGE_START);
	}

	class MyTableModel extends AbstractTableModel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columnNames = {"Source", "Target", "LineNumber", "Kind of dependency", "Severity"};
	    private Object[][] data = new Object[][]{ { "", "", "", "", ""} };

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

		public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	    
	    public void setNewArray(Object[][] data){
	    	this.data = data;
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 2) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	}
}