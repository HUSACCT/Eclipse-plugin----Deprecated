package plugin.controller;

import java.util.ArrayList;

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
