package roadmap.statistics;

import java.util.Iterator;
import java.util.Map.Entry;

import roadmap.Car;
import roadmap.Road;
import roadmap.parser.RoadMapInfo;

public class Stats {
	
	// Global statistics
	private int totalCars = 0;
	private int totalRoads = 0;
	private int totalIntersections = 0;
	private int totalTrafficLights = 0;
	private int totalSimulationCycles = 0;
	
	// Car statistics
	private double avgTimeMoving = 0.0;
	private double avgTimeWaiting = 0.0;
	private double percentageTimeMoving = 0.0;
	
	// Roadmap statistics
	private int movedCars = 0;
	private double percentageMovedCars = 0.0;
	
	public Stats(RoadMapInfo world) {
		totalRoads = world.getRoads().size();
		totalIntersections = world.getIntersections().size();
		totalTrafficLights = world.getTrafficLights().size();
		totalCars = 0;
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			Iterator<Car> it = road.getCarQueue().iterator();
			while(it.hasNext()) {
				it.next();
				++totalCars;
			}
		}
	}
	
	public void updateStats(RoadMapInfo world) {
		++totalSimulationCycles;
		totalRoads = world.getRoads().size();
		totalIntersections = world.getIntersections().size();
		totalTrafficLights = world.getTrafficLights().size();
		totalCars = 0;
		avgTimeMoving = 0.0;
		avgTimeWaiting = 0.0;
		movedCars = 0;
		
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			road.updateStats(totalSimulationCycles);
			Iterator<Car> it = road.getCarQueue().iterator();
			while(it.hasNext()) {
				Car car = it.next();
				totalCars++;
				car.setAliveCycles(car.getAliveCycles() + 1);
				
				// the car moved
				if(car.getNextRoadId() == road.getRoadId()) {
					car.setTimeMoving(car.getTimeMoving() + 1);
					++movedCars;
				}
				// the car didn't move
				else
					car.setTimeWaiting(car.getTimeWaiting() + 1);
				
				// movement statistics
				avgTimeMoving += (double) car.getTimeMoving();
				avgTimeWaiting += (double) car.getTimeWaiting();
			}
		}
		
		avgTimeMoving /= (double) totalCars;
		avgTimeWaiting /= (double) totalCars;
		percentageMovedCars = (double) movedCars / (double) totalCars;
		percentageTimeMoving = avgTimeMoving / (avgTimeMoving + avgTimeWaiting);
	}

	public int getTotalCars() {
		return totalCars;
	}

	public int getTotalRoads() {
		return totalRoads;
	}

	public int getTotalIntersections() {
		return totalIntersections;
	}

	public int getTotalTrafficLights() {
		return totalTrafficLights;
	}

	public int getTotalSimulationCycles() {
		return totalSimulationCycles;
	}

	public double getAvgTimeMoving() {
		return avgTimeMoving;
	}

	public double getAvgTimeWaiting() {
		return avgTimeWaiting;
	}

	public double getPercentageTimeMoving() {
		return percentageTimeMoving;
	}

	public int getMovedCars() {
		return movedCars;
	}

	public double getPercentageMovedCars() {
		return percentageMovedCars;
	}

}
