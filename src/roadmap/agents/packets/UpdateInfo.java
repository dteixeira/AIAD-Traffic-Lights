package roadmap.agents.packets;

import java.io.Serializable;

public class UpdateInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int roadId;
	private int nextRoadId;
	private boolean currentState;
	
	public int getRoadId() {
		return roadId;
	}
	
	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}
	
	public int getNextRoadId() {
		return nextRoadId;
	}
	
	public void setNextRoadId(int nextRoadId) {
		this.nextRoadId = nextRoadId;
	}
	
	public boolean isCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(boolean currentState) {
		this.currentState = currentState;
	}

}
