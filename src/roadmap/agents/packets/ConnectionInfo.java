package roadmap.agents.packets;

import jade.core.AID;

import java.io.Serializable;
import roadmap.Orientation;

public class ConnectionInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int carCount;
	private int roadCapacity;
	private int roadId;
	private int nextRoadId;
	private int intersectionId;
	private int maxCarSpeed;
	private boolean currentState;
	private AID agentAID;
	private Orientation roadOrientation;
	
	public Orientation getRoadOrientation() {
		return roadOrientation;
	}

	public int getMaxCarSpeed() {
		return maxCarSpeed;
	}

	public void setMaxCarSpeed(int maxCarSpeed) {
		this.maxCarSpeed = maxCarSpeed;
	}

	public int getIntersectionId() {
		return intersectionId;
	}

	public void setIntersectionId(int intersectionId) {
		this.intersectionId = intersectionId;
	}

	public void setRoadOrientation(Orientation roadOrientation) {
		this.roadOrientation = roadOrientation;
	}

	public AID getAgentAID() {
		return agentAID;
	}

	public void setAgentAID(AID agentAID) {
		this.agentAID = agentAID;
	}

	public boolean isCurrentState() {
		return currentState;
	}

	public void setCurrentState(boolean currentState) {
		this.currentState = currentState;
	}

	public int getCarCount() {
		return carCount;
	}
	
	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}
	
	public int getRoadCapacity() {
		return roadCapacity;
	}
	
	public void setRoadCapacity(int roadCapacity) {
		this.roadCapacity = roadCapacity;
	}
	
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

}
