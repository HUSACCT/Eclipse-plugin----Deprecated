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
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
	private JTextPane violationInformation;

	public ValidateView() {

	}

	@Override
	public void createPartControl(Composite parent) {
		pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		initiateViolationTable();
		frame.add(new JScrollPane(violationTable), BorderLayout.CENTER);
		createButton(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void initiateViolationTable(){
		String[] columnNames = {"From", "To", "Line number", "Dependency type"};
		violationArrayList = pluginController.getViolations();

		Object[][] data = new Object[][]{ { "", "", "", ""} };
		
		if(violationArrayList.size() > 1){
			data = new Object[violationArrayList.size()][4];
		
			int counter = 0;
			for(ViolationDTO violationDTO : violationArrayList){
				data[counter][0] = violationDTO.fromClasspath;
				data[counter][1] = violationDTO.toClasspath;
				data[counter][2] = "" + violationDTO.linenumber;
				data[counter][3] = violationDTO.violationType.getKey();
				counter++;
			}
		}
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
				int lineNumber = Integer.parseInt(violationTable.getValueAt(violationTable.getSelectedRow(), 2).toString());  
				String readedPackageAndClassName = violationTable.getValueAt(violationTable.getSelectedRow(), 0).toString();  
				String formattedPackageAndClassName = readedPackageAndClassName.replace('.' , '/'); 
				String projectName = pluginController.getProjectName();
				String entireClassPath = (projectName + "/src/" + formattedPackageAndClassName)+ ".java";

				Path path = new Path(entireClassPath); 
				IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);

				openViolationWithEditor(iFile,lineNumber);	
			}
		});
		
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

	@Override
	public void setFocus() {

	}
	
	class MyTableModel extends AbstractTableModel {
	    private String[] columnNames = {"From", "To", "Line number", "Dependency type"};
	    private Object[][] data = new Object[][]{ { "", "", "", ""} };

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
