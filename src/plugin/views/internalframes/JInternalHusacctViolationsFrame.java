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
import plugin.controllers.PluginController;
import plugin.controllers.ViolationsViewController;

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
			
			violationTable.setToolTipText("Double click a violation to open the source");			
			violationInformation.setText("There are " + violationArrayList.size() + " violations found.");
			violationTable.repaint();
		}
		violationTable.repaint();
		
		violationTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
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
					}
				}
			}
		});
		
	}

	private void createButton(JInternalHusacctViolationsFrame frame){
		Panel panel = new Panel();	
		panel.setBackground(Color.LIGHT_GRAY);
		Button buttonValidate = new Button("Validate");
		Button buttonExport = new Button("Export violations");
		violationInformation = new JTextPane();
		violationInformation.setText("There are no violations found yet");
		violationInformation.setBackground(Color.LIGHT_GRAY);
		
		buttonValidate.addActionListener(new ActionListener() {		 
			public void actionPerformed(ActionEvent e)
			{
				pluginController.validate();
			}
		}); 
		
		buttonExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				pluginController.exportViolations();
			}
		});
		panel.add(buttonValidate);
		panel.add(violationInformation);
		panel.add(buttonExport);
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

	    public boolean isCellEditable(int row, int col) {
	        if (col < 5) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	}
}