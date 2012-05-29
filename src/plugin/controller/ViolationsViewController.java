package plugin.controller;

import husacct.ServiceProvider;
import husacct.common.dto.ModuleDTO;
import husacct.common.dto.ViolationDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class ViolationsViewController {

	public static ArrayList<ViolationDTO> getViolations(){
		ArrayList<ViolationDTO> violationArrayList = new ArrayList<ViolationDTO>();
		ModuleDTO[] moduleList;
		moduleList = ServiceProvider.getInstance().getDefineService().getRootModules();
		for(ModuleDTO mdtoFrom : moduleList){
			for(ModuleDTO mdtoTo: moduleList){
				if(mdtoFrom != mdtoTo){
					violationArrayList.addAll(Arrays.asList(ServiceProvider.getInstance().getValidateService().getViolationsByLogicalPath(mdtoFrom.logicalPath, mdtoTo.logicalPath)));
				}
			}
		}
		return violationArrayList;
	}

	public static Object[][] setDataModel(){
		ArrayList<ViolationDTO> violationArrayList = getViolations();
		Object[][] data = new Object[][]{ { "", "", "", "", ""} };
		String serverity = "";

		if(violationArrayList.size() > 1){
			data = new Object[violationArrayList.size()][5];

			int counter = 0;
			for(ViolationDTO violationDTO : violationArrayList){
				data[counter][0] = violationDTO.fromClasspath;
				data[counter][1] = violationDTO.toClasspath;
				data[counter][2] = "" + violationDTO.linenumber;
				data[counter][3] = violationDTO.violationType.getKey();
				if(violationDTO.severityValue == 0){
					serverity = "Low";
				}else if(violationDTO.severityValue == 1){
					serverity = "Medium";
				}else if(violationDTO.severityValue == 2){
					serverity = "High";
				}
				data[counter][4] = serverity;
				counter++;
			}
		}
		return data;
	}

	public static void openViolationWithEditor(IFile file, int lineNumber) {
		final IFile iFile = file;
		final int finalLineNumber = lineNumber;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchwindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = workbenchwindow.getActivePage();
				if (page != null && iFile != null) {
					try {
						IDE.openEditor(page, iFile, true);
						HashMap<String, Object> hashMap = new HashMap<String, Object> ();
						hashMap.put(IMarker.LINE_NUMBER, new Integer(finalLineNumber));
						hashMap.put(IDE.EDITOR_ID_ATTR, "org.eclipse.jdt.internal.ui.javaeditor.JavaEditor");
						try {
							IMarker marker = iFile.createMarker(IMarker.TEXT);
							marker.setAttributes(hashMap);
							IDE.openEditor(page, marker, true);
							marker.delete();
						} catch (PartInitException e) {
							e.printStackTrace();
						} catch (CoreException e) {
							e.printStackTrace();
						} 
					}catch (PartInitException pie) {
						pie.printStackTrace();
					}
				}
			}
		});
	}
}
