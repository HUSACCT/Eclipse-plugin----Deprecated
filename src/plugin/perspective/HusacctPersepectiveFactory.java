package plugin.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class HusacctPersepectiveFactory implements IPerspectiveFactory {
	@Override
	public void createInitialLayout(IPageLayout myLayout) {
		myLayout.addView("plugin.views.HusacctMainView", IPageLayout.TOP, 0.76f, myLayout.getEditorArea());
	}
}
