
public class Connection {

	private Road connectedRoad;
	private Orientation orientation;
	private TrafficLight trafficLight;

	public Connection(){

	}

	/**
	 * @return the connectedRoad
	 */
	public Road getConnectedRoad() {
		return connectedRoad;
	}

	/**
	 * @param connectedRoad the connectedRoad to set
	 */
	public void setConnectedRoad(Road connectedRoad) {
		this.connectedRoad = connectedRoad;
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the trafficLight
	 */
	public TrafficLight getTrafficLight() {
		return trafficLight;
	}

	/**
	 * @param trafficLight the trafficLight to set
	 */
	public void setTrafficLight(TrafficLight trafficLight) {
		this.trafficLight = trafficLight;
	}

}