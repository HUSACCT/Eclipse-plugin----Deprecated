package plugin.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class HusacctPerspectiveFactory implements IPerspectiveFactory {
	@Override
	public void createInitialLayout(IPageLayout myLayout) {
		myLayout.setEditorAreaVisible(false);
		IFolderLayout leftFolder = myLayout.createFolder("LEFT", IPageLayout.LEFT, 0.20f, myLayout.getEditorArea());
		leftFolder.addView("org.eclipse.jdt.ui.PackageExplorer");
		leftFolder.addView("plugin.views.StateView");
		IFolderLayout rightFolder = myLayout.createFolder("RIGHT", IPageLayout.RIGHT, 0.25f, myLayout.getEditorArea());
		rightFolder.addView("plugin.views.DefineView");
		rightFolder.addView("plugin.views.AnalyseView");
		rightFolder.addView("plugin.views.ValidateView");
		rightFolder.addView("plugin.views.GraphicsAnalysedArchitectureView");
		rightFolder.addView("plugin.views.GraphicsDefinedArchitectureView");
	}
}
