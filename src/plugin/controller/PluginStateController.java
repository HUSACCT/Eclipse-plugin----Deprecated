package plugin.controller;

import husacct.ServiceProvider;
import husacct.analyse.IAnalyseService;
import husacct.control.task.IStateChangeListener;
import husacct.control.task.States;
import husacct.define.IDefineService;
import husacct.validate.IValidateService;

import java.util.ArrayList;
import java.util.List;

public class PluginStateController {
	private boolean isOpened = false;
	private ArrayList<IStateChangeListener> stateListeners = new ArrayList<IStateChangeListener>();
	
	public List<States> checkState(){
		
		IDefineService defineService = ServiceProvider.getInstance().getDefineService();
		IAnalyseService analyseService = ServiceProvider.getInstance().getAnalyseService();
		IValidateService validateService = ServiceProvider.getInstance().getValidateService();
		List<States> newStates = new ArrayList<States>();

		if(validateService.isValidated()){
			newStates.add(States.VALIDATED);
		}
		
		if(defineService.isMapped()){
			newStates.add(States.MAPPED);
		}
		
		if(analyseService.isAnalysed()){
			newStates.add(States.ANALYSED);
		}
		
		if(defineService.isDefined()){
			newStates.add(States.DEFINED);
		}
		
		if(isOpened){
			newStates.add(States.OPENED);
		}
		
		if(newStates.isEmpty()){
			newStates.add(States.NONE);
		}
		notifyStateListeners(newStates);
		return newStates;
	}
	
	public void setIsOpened(boolean isOpened){
		this.isOpened = isOpened;
		checkState();
	}
	
	public void addStateChangeListener(IStateChangeListener listener) {
		this.stateListeners.add(listener);
	}
	
	public void notifyStateListeners(List<States> states){
		for(IStateChangeListener listener : this.stateListeners){
			listener.changeState(states);
		}
	}
}
