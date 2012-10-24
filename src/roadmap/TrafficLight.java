package roadmap;

public class TrafficLight {

	private Road destinationRoad;
	private boolean trafficAllowed;

	public TrafficLight(){

	}
	
	public Road getDestinationRoad() {
		return destinationRoad;
	}

	public void setDestinationRoad(Road destinationRoad) {
		this.destinationRoad = destinationRoad;
	}

	public boolean isTrafficAllowed() {
		return trafficAllowed;
	}

	public void setTrafficAllowed(boolean trafficAllowed) {
		this.trafficAllowed = trafficAllowed;
	}

}