package roadmap;

public class Car {
	
	private static int carLastId = 0;
	private int carId;
	private int currentRoadId;
	private int nextRoadId;
	private int aliveCycles = 0;
	private int timeWaiting = 0;
	private int timeMoving = 0;
	
	public Car() {
		carId = carLastId++;
	}
	
	public int getAliveCycles() {
		return aliveCycles;
	}

	public void setAliveCycles(int aliveCycles) {
		this.aliveCycles = aliveCycles;
	}

	public int getTimeWaiting() {
		return timeWaiting;
	}

	public void setTimeWaiting(int timeWaiting) {
		this.timeWaiting = timeWaiting;
	}

	public int getTimeMoving() {
		return timeMoving;
	}

	public void setTimeMoving(int timeMoving) {
		this.timeMoving = timeMoving;
	}
	
	public int getNextRoadId() {
		return nextRoadId;
	}

	public void setNextRoadId(int nextRoadId) {
		this.nextRoadId = nextRoadId;
	}

	public int getCarId() {
		return carId;
	}
	
	public void setCarId(int carId) {
		this.carId = carId;
	}
	
	public int getCurrentRoadId() {
		return currentRoadId;
	}
	
	public void setCurrentRoadId(int currentRoadId) {
		this.currentRoadId = currentRoadId;
	}

}
