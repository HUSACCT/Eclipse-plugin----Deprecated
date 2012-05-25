package plugin.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import plugin.controller.FrameInstanceController;
import plugin.controller.PluginController;
import plugin.controller.resources.IResetListener;

public class DefineView extends ViewPart implements IResetListener{
	private PluginController pluginController;
	private Frame frame;
	private Logger logger = Logger.getLogger(DefineView.class);

	public DefineView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		logger.info("defineView gestart");
		pluginController = PluginController.getInstance();
		pluginController.addToResetController(this);
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(FrameInstanceController.getDefineFrame().getRootPane(), BorderLayout.CENTER);
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
		panel.setBackground(Color.LIGHT_GRAY);
		
		frame.add(panel, BorderLayout.PAGE_START);
	}
	
	private void importArchitecture(){
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Import");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showOpenDialog(FrameInstanceController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			//pluginController.importLogicalArchitecture(jFileChooser.getSelectedFile());
		}
	}
	
	private void exportArchitecture(){
		JFileChooser jFileChooser = new JFileChooser(); 
		jFileChooser.setCurrentDirectory(new java.io.File("."));
		jFileChooser.setDialogTitle("Export");
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setFileFilter(new TypeOfFile());  
		jFileChooser.setAcceptAllFileFilterUsed(false); 
		if (jFileChooser.showSaveDialog(FrameInstanceController.getDefineFrame().getRootPane()) == JFileChooser.APPROVE_OPTION) { 
			//pluginController.exportLogicalArchitecture(jFileChooser.getSelectedFile());
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

	@Override
	public void reset() {
		logger.info("defineView gereset");
		frame.removeAll();
		frame.add(FrameInstanceController.getDefineFrame().getRootPane(), BorderLayout.CENTER);
		createButtons(frame);
		frame.validate();
		frame.repaint();
	}
}
