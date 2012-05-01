package plugin.wizards;

import org.eclipse.jface.wizard.Wizard;

public class SelectSourceWizard extends Wizard {
	SelectSourcePage selectSourcePage;
	
	 public void addPages() {
         selectSourcePage = new SelectSourcePage("Personal Information Page");
         addPage(selectSourcePage);

}
	 
	@Override
	public boolean performFinish() {
		return false;
	}

}
