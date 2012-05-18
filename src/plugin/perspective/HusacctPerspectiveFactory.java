package plugin.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class HusacctPerspectiveFactory implements IPerspectiveFactory {
	@Override
	public void createInitialLayout(IPageLayout myLayout) {
		myLayout.setEditorAreaVisible(false);
				
		IFolderLayout topLeftFolder = myLayout.createFolder("topLeft", IPageLayout.LEFT, 0.18f, myLayout.getEditorArea());
		topLeftFolder.addView("org.eclipse.jdt.ui.PackageExplorer");
		IFolderLayout bottomLeftFolder = myLayout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.70f, "topLeft");
		bottomLeftFolder.addView("plugin.views.StateView");
		IFolderLayout rightFolder = myLayout.createFolder("RIGHT", IPageLayout.RIGHT, 0.25f, myLayout.getEditorArea());
		rightFolder.addView("plugin.views.StartView");
		rightFolder.addView("plugin.views.DefineView");
		rightFolder.addView("plugin.views.AnalyseView");
		rightFolder.addView("plugin.views.ValidateView");
		rightFolder.addView("plugin.views.GraphicsAnalysedArchitectureView");
		rightFolder.addView("plugin.views.GraphicsDefinedArchitectureView");
		
	}
}
