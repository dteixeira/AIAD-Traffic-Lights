package roadmap;

public class Car {
	
	private static int carLastId = 0;
	private int carId;
	private int currentRoadId;
	private int nextRoadId;
	
	public Car() {
		carId = carLastId++;
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
