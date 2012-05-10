package plugin.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class HusacctPersepectiveFactory implements IPerspectiveFactory {
	@Override
	public void createInitialLayout(IPageLayout myLayout) {
		myLayout.setEditorAreaVisible(false);
		myLayout.addView("org.eclipse.jdt.ui.PackageExplorer", IPageLayout.LEFT, 0.20f, myLayout.getEditorArea());
		IFolderLayout rightFolder = myLayout.createFolder("RIGHT", IPageLayout.RIGHT, 0.25f, myLayout.getEditorArea());
		rightFolder.addView("plugin.views.DefineView");
		rightFolder.addView("plugin.views.ValidateView");		
	}
}
