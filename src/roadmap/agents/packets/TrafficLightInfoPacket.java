package roadmap.agents.packets;

import jade.core.AID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import roadmap.Connection;
import roadmap.Intersection;
import roadmap.Road;

public class TrafficLightInfoPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<ConnectionInfo> info;
	
	public TrafficLightInfoPacket(Intersection intersection, HashMap<Integer, AID> agentMapping) {
		info = new ArrayList<ConnectionInfo>();
		for(Connection connect : intersection.getInboundConnections()) {
			ConnectionInfo cInfo = new ConnectionInfo();
			Road road = connect.getConnectedRoad();
			cInfo.setCarCount(road.getCurrentCarCount());
			cInfo.setRoadCapacity(road.getMaxCarCapacity());
			cInfo.setRoadId(road.getRoadId());
			cInfo.setCurrentState(connect.getTrafficLight().isTrafficAllowed());
			cInfo.setNextRoadId(connect.getTrafficLight().getDestinationRoad().getRoadId());
			cInfo.setRoadOrientation(road.getRoadOrientation());
			cInfo.setIntersectionId(intersection.getIntersectionId());
			cInfo.setMaxCarSpeed(road.getCarSpeed());
			cInfo.setAgentAID(agentMapping.get(connect.getConnectedRoad().getStartIntersection().getIntersectionId()));
			info.add(cInfo);
		}
	}
	
	public ArrayList<ConnectionInfo> getInfo() {
		return info;
	}
	

}
