package roadmap;

public class Connection {

	private Road connectedRoad;
	private TrafficLight trafficLight;

	public Connection(){

	}

	public Road getConnectedRoad() {
		return connectedRoad;
	}

	public void setConnectedRoad(Road connectedRoad) {
		this.connectedRoad = connectedRoad;
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

}