package roadmap.agents.packets;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;
import roadmap.Connection;
import roadmap.Intersection;
import roadmap.Road;

public class TrafficLightInfoPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<ConnectionInfo> info;
	
	public TrafficLightInfoPacket(Intersection intersection, HashMap<Integer, String> agentMapping) {
		info = new ArrayList<ConnectionInfo>();
		for(Connection connect : intersection.getInboundConnections()) {
			ConnectionInfo cInfo = new ConnectionInfo();
			Road road = connect.getConnectedRoad();
			cInfo.setAgentName(agentMapping.get(connect.getTrafficLight().getDestinationRoad().getFinishIntersection().getIntersectionId()));
			cInfo.setCarCount(road.getCurrentCarCount());
			cInfo.setRoadCapacity(road.getMaxCarCapacity());
			cInfo.setRoadId(road.getRoadId());
			cInfo.setCurrentState(connect.getTrafficLight().isTrafficAllowed());
			cInfo.setNextRoadId(connect.getTrafficLight().getDestinationRoad().getRoadId());
			cInfo.setRoadOrientation(road.getRoadOrientation());
			info.add(cInfo);
		}
	}
	
	public ArrayList<ConnectionInfo> getInfo() {
		return info;
	}
	

}
