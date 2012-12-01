package roadmap.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import roadmap.Car;
import roadmap.Connection;
import roadmap.Road;
import roadmap.parser.RoadMapInfo;

public class SimulationEngine {
	
	private RoadMapInfo world;
	private Random randomGenerator;
	
	public SimulationEngine(RoadMapInfo world) {
		this.world = world;
		randomGenerator = new Random();
	}

	public RoadMapInfo getWorld() {
		return world;
	}

	public void setWorld(RoadMapInfo world) {
		this.world = world;
	}

	public void evolveWorld() {
		moveCars();
		retractCars();
		cleanup();
	}

	private void cleanup() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			for(Car car : road.getCarQueue()) {
				if(car.getCurrentRoadId() != road.getRoadId()) {
					car.setCurrentRoadId(road.getRoadId());
					car.setNextRoadId(-1);
				}
			}
		}
	}

	private void retractCars() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			
			// Moves waiting cars into road
			for(int i = road.getCurrentCarCount(); i < road.getMaxCarCapacity() && road.getCarWaitingQueue().size() > 0; ++i) {
				road.getCarQueue().addLast(road.getCarWaitingQueue().pop());
			}
			
			// Return remaining cars home
			LinkedList<Car> remaining = road.getCarWaitingQueue();
			while(remaining.size() != 0) {
				returnHome(remaining.pop());
			}
		}
	}
	
	private void returnHome(Car car) {
		Road road = world.getRoads().get(car.getCurrentRoadId());
		road.getCarQueue().addFirst(car);
		while(road.getCurrentCarCount() > road.getMaxCarCapacity()){
			returnHome(road.getCarQueue().removeLast());
		}
	}

	private void moveCars() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			
			// Builds traffic movement permission map
			HashMap<Integer, Boolean> trafficLights = new HashMap<Integer, Boolean>();
			HashMap<Integer, Integer> movedCount = new HashMap<Integer, Integer>();
			ArrayList<Integer> possibleNext = new ArrayList<Integer>();
			for(Connection connect : road.getConnections()) {
				trafficLights.put(connect.getTrafficLight().getDestinationRoad().getRoadId(), connect.getTrafficLight().isTrafficAllowed());
				movedCount.put(connect.getTrafficLight().getDestinationRoad().getRoadId(), 0);
				possibleNext.add(connect.getTrafficLight().getDestinationRoad().getRoadId());
			}
			
			for(int i = 0; i < road.getCarQueue().size(); ++i) {
				// Gives car a direction if it doesn't have one
				Car car = road.getCarQueue().get(i);
				if(car.getNextRoadId() < 0)
					car.setNextRoadId(possibleNext.get(randomGenerator.nextInt(possibleNext.size())));				
				
				if(trafficLights.get(car.getNextRoadId()) && movedCount.get(car.getNextRoadId()) < road.getCarSpeed()) {
					world.getRoads().get(car.getNextRoadId()).getCarWaitingQueue().add(car);
					movedCount.put(car.getNextRoadId(), movedCount.get(car.getNextRoadId()) + 1);
					road.getCarQueue().remove(i);
					--i;
				}
			}
		}
	}
}
