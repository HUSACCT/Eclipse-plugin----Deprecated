package plugin.controllers;

import java.util.ArrayList;

import plugin.controllers.resources.IResetListener;

public class ViewResetController {
	
	ArrayList<IResetListener> resetListeners = new ArrayList<IResetListener>();
	
	public void addListener(IResetListener listener) {
		this.resetListeners.add(listener);
	}
	
	public void notifyResetListeners(){
		for(IResetListener listener : this.resetListeners){
			listener.reset();
		}
	}
}
