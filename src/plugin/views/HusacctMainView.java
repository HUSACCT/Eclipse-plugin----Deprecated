package plugin.views;

import husacct.ServiceProvider;

import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JInternalFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class HusacctMainView extends ViewPart {

	public HusacctMainView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {	
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(composite);
		ServiceProvider sp = ServiceProvider.getInstance();
		JInternalFrame jif = sp.getValidateService().getBrowseViolationsGUI();
		frame.add(jif.getRootPane());		
		parent.setParent(composite);		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
