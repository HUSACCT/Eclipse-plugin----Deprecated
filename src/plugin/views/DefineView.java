package plugin.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import plugin.controller.PluginController;

public class DefineView extends ViewPart {
	private PluginController pluginController;

	public DefineView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		pluginController = PluginController.getInstance();
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		Frame frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(pluginController.getDefineFrame().getRootPane(), BorderLayout.CENTER);
		createButtons(frame);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}
	
	private void createButtons(Frame frame){
		Panel panel = new Panel();	
		
		Button buttonImport = new Button("Import Architecture");
		buttonImport.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	importArchitecture();
	        }
	    }); 	
		panel.add(buttonImport);
		
		Button buttonExport = new Button("Export Architecture");
		buttonExport.addActionListener(new ActionListener() {		 
	        public void actionPerformed(ActionEvent e)
	        {
	        	exportArchitecture();
	        }
	    }); 	
		panel.add(buttonExport);
		
		frame.add(panel, BorderLayout.PAGE_START);
	}
	
	private void importArchitecture(){
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Import");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showOpenDialog(pluginController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			pluginController.importLogicalArchitecture(jFileChooser.getSelectedFile());
		}
	}
	
	private void exportArchitecture(){
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Export");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showSaveDialog(pluginController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			pluginController.exportLogicalArchitecture(jFileChooser.getSelectedFile());
		}
	}
	
	class TypeOfFile extends FileFilter  
	{   
		public boolean accept(File f)  
		{  
			return f.isDirectory()||f.getName().toLowerCase().endsWith(".xml");  
		}   
		public String getDescription()  
		{  
			return ".xml files";  
		}  
	}

	@Override
	public void setFocus() {
	}

}
