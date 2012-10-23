
public class TrafficLight {

	private Road destinationRoad;
	private boolean trafficAllowed;

	public TrafficLight(){

	}
	
	/**
	 * @return the destinationRoad
	 */
	public Road getDestinationRoad() {
		return destinationRoad;
	}

	/**
	 * @param destinationRoad the destinationRoad to set
	 */
	public void setDestinationRoad(Road destinationRoad) {
		this.destinationRoad = destinationRoad;
	}

	/**
	 * @return the trafficAllowed
	 */
	public boolean isTrafficAllowed() {
		return trafficAllowed;
	}

	/**
	 * @param trafficAllowed the trafficAllowed to set
	 */
	public void setTrafficAllowed(boolean trafficAllowed) {
		this.trafficAllowed = trafficAllowed;
	}

}