package plugin.views;

import java.awt.BorderLayout;
import java.awt.Frame;
import plugin.views.internalframes.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class StartView extends ViewPart {
	private Frame frame;
	private JInternalHusacctStartFrame husacctMainScreen = new JInternalHusacctStartFrame();
	
	public StartView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.EMBEDDED);	
		frame = SWT_AWT.new_Frame(composite);
		frame.setLayout(new BorderLayout());
		frame.add(husacctMainScreen.getRootPane(), BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
		parent.setParent(composite);
	}

	@Override
	public void setFocus() {

	}

}
