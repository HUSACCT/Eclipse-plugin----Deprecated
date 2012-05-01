package plugin;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import plugin.wizards.SelectSourceWizard;

public class ActionDelegate implements IViewActionDelegate {

	private IViewPart view;

	@Override
	public void run(IAction action) {
		SelectSourceWizard wizard = new SelectSourceWizard();
		WizardDialog dialog = new WizardDialog(view.getSite().getShell(), wizard);
		dialog.create();
		dialog.open();

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IViewPart view) {
		this.view = view;

	}
}
