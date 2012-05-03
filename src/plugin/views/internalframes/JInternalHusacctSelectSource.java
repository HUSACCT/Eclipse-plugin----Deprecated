package plugin.views.internalframes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JInternalHusacctSelectSource extends JInternalFrame implements ActionListener {

		   JButton buttonSelectSource;
		   JTextField textFieldSourceFolder, textFieldProgramLanguage, textFieldProgramNumer; 
		   JLabel labelSourceFolder, labelProgramLanguage, labelProgramNumber, labelSource;
		   
		   JFileChooser jFileChooser;
		   String choosertitle;
	
		   GridLayout gridLayout = new GridLayout(0,2);
		   JPanel jPanel = new JPanel();
		   
		   
		  public JInternalHusacctSelectSource() {
			jPanel.setLayout(gridLayout);
			  
			textFieldSourceFolder = new JTextField();
			labelSourceFolder = new JLabel("Source Folder: ");
			
			textFieldProgramLanguage = new JTextField();
			textFieldProgramLanguage.setText("Java");
			labelProgramLanguage = new JLabel("Program Language: ");
			
			textFieldProgramNumer = new JTextField();
			textFieldProgramNumer.setText("1.0");
			labelProgramNumber = new JLabel("Program number: ");
			
			buttonSelectSource = new JButton("Browse source folder");
			labelSource = new JLabel();
		    buttonSelectSource.addActionListener(this);
		    

		    jPanel.add(labelProgramLanguage);
		    jPanel.add(textFieldProgramLanguage);
		    jPanel.add(labelProgramNumber);
		    jPanel.add(textFieldProgramNumer);
		    jPanel.add(labelSourceFolder);
		    jPanel.add(textFieldSourceFolder);
		    jPanel.add(labelSource); 
		    jPanel.add(buttonSelectSource);
		    
		    
		    
		    getContentPane().add(jPanel);
    
		    
		    setBounds(50, 50, 200, 200);
		    setResizable(true);
		    setClosable(true);
		    setMaximizable(true);
		    setIconifiable(true);
		    setTitle("Select source");
		    setVisible(true);
		   
		   }
		 
		  public void actionPerformed(ActionEvent e) {
		    int result;
		         
		    jFileChooser = new JFileChooser(); 
		    jFileChooser.setCurrentDirectory(new java.io.File("."));
		    jFileChooser.setDialogTitle(choosertitle);
		    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //
		    // disable the "All files" option.
		    //
		    jFileChooser.setAcceptAllFileFilterUsed(false);
		    //    
		    if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): " +  jFileChooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " +  jFileChooser.getSelectedFile());
		      textFieldSourceFolder.setText(jFileChooser.getCurrentDirectory() + "");
		      }
		    else {
		      System.out.println("No Selection ");
		      }
		     }
		    
//		  public Dimension getPreferredSize(){
//		    return new Dimension(200, 200);
//		    }
}
