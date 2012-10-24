package roadmap;

public class Connection {

	private Road connectedRoad;
	private Orientation orientation;
	private TrafficLight trafficLight;

	public Connection(){

	}

	public Road getConnectedRoad() {
		return connectedRoad;
	}

	public void setConnectedRoad(Road connectedRoad) {
		this.connectedRoad = connectedRoad;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

}