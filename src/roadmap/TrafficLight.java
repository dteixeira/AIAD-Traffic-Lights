package roadmap;

public class TrafficLight {

	private Road destinationRoad;
	private boolean trafficAllowed;
	private Coordinates coordinates;

	public TrafficLight(){

	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
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