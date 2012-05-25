package plugin.controller;

import husacct.ServiceProvider;

import javax.swing.JInternalFrame;

public class FrameInstanceController {
 	public static JInternalFrame getDefineFrame(){
 		JInternalFrame JInternalFrameDefine = ServiceProvider.getInstance().getDefineService().getDefinedGUI();
		JInternalFrameDefine.setVisible(true);
 		return JInternalFrameDefine;
 	}
 	
 	public static JInternalFrame getValidateFrame(){
 		JInternalFrame jInternalFrameValidate = ServiceProvider.getInstance().getValidateService().getBrowseViolationsGUI();
		jInternalFrameValidate.setVisible(true);
 		return jInternalFrameValidate;
 	}
 	
 	public static JInternalFrame getGraphicsAnalysedArchitecture(){
 		JInternalFrame jInternalFrameAnalysedGraphics = ServiceProvider.getInstance().getGraphicsService().getAnalysedArchitectureGUI();
		jInternalFrameAnalysedGraphics.setVisible(true);
		jInternalFrameAnalysedGraphics.setClosable(false);
		jInternalFrameAnalysedGraphics.setResizable(false);
 		return jInternalFrameAnalysedGraphics;
 	}
 	
 	public static JInternalFrame getGraphicsDefinedArchitecture(){
 		JInternalFrame jInternalFrameDefinedGraphics = ServiceProvider.getInstance().getGraphicsService().getDefinedArchitectureGUI();
		jInternalFrameDefinedGraphics.setVisible(true);
		jInternalFrameDefinedGraphics.setClosable(false);
		jInternalFrameDefinedGraphics.setResizable(false);
 		return jInternalFrameDefinedGraphics;
 	}
 	
 	public static JInternalFrame getAnalyseFrame(){
 		JInternalFrame jInternalFrameAnalyse = ServiceProvider.getInstance().getAnalyseService().getJInternalFrame();
		jInternalFrameAnalyse.setVisible(true);		
 		return jInternalFrameAnalyse;
 	}
 	
}
